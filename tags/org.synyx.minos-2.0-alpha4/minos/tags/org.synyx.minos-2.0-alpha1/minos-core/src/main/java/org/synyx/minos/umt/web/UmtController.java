package org.synyx.minos.umt.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.synyx.minos.umt.web.UmtUrls.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.Minos;
import org.synyx.minos.core.web.PageWrapper;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.ValidationSupport;
import org.synyx.minos.security.CorePermissions;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.service.UserNotFoundException;
import org.synyx.minos.util.Assert;


/**
 * Web controller for the user management providing access to most of the user
 * management functionality.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Controller
@SessionAttributes( { UmtController.USER_KEY, UmtController.ROLE_KEY })
public class UmtController extends ValidationSupport<UserForm> {

    public static final String USER_KEY = "userForm";
    public static final String ROLE_KEY = "role";
    public static final String USERS_KEY = "users";

    private UserManagement userManagement;


    /**
     * Setter to inject user management.
     * 
     * @param userManagement the userManagement to set
     */
    @Required
    public void setUserManagement(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    /**
     * Initialize binders for this controller.
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(DataBinder binder) {

        binder.registerCustomEditor(Role.class, new RolePropertyEditor(
                userManagement));
    }


    /**
     * Redirect to entry page.
     * 
     * @return
     */
    @RequestMapping(MODULE)
    public String index() {

        return UrlUtils.redirect("umt/users");
    }


    /**
     * Show all users.
     * 
     * @return
     */
    @RequestMapping(LIST_USERS)
    public String getUsers(Pageable pageable, Model model,
            @CurrentUser User user) {

        model.addAttribute("currentUser", user);
        model.addAttribute(USERS_KEY, PageWrapper.wrap(userManagement
                .getUsers(pageable)));

        return "umt/users";
    }


    /**
     * Delete a user.
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = EDIT_USER, method = DELETE)
    public String deleteUser(@PathVariable("id") Long userId, Model model,
            @CurrentUser User currentUser) {

        String targetView = UrlUtils.redirect("../users");

        if (null == userId) {
            model.addAttribute(Minos.MESSAGE, Message
                    .error("umt.user.delete.usernamerequired"));
            return targetView;
        }

        try {

            User user = userManagement.getUser(userId);
            boolean currentUserGiven = null != currentUser;

            if (currentUserGiven && currentUser.equals(user)) {
                model.addAttribute(Minos.MESSAGE, Message
                        .error("umt.user.delete.cannotdeleteherself"));
                return targetView;
            }

            userManagement.deleteUser(userId);

            model.addAttribute(Minos.MESSAGE, Message.success(
                    "umt.user.delete.success", userId));

        } catch (UserNotFoundException e) {

            model.addAttribute(Minos.MESSAGE, Message.error(
                    "user.delete.invalidusername", userId));
        }

        return targetView;
    }


    /**
     * View a user.
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = EDIT_USER, method = GET)
    public String setupForm(@PathVariable("id") Long userId, Model model) {

        User user = userManagement.getUser(userId);

        if (null == user) {
            model.addAttribute(Minos.MESSAGE, Message.error(
                    "umt.user.username.invalid", userId));
            return UrlUtils.redirect("../users");
        }

        return populateFormModel(new UserForm(user), model);
    }


    /**
     * Show the form for a new {@link User}.
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = USER_FORM, method = GET)
    public String showEmptyForm(Model model) {

        return populateFormModel(BeanUtils.instantiateClass(UserForm.class),
                model);
    }


    private String populateFormModel(UserForm userForm, Model model) {

        model.addAttribute(USER_KEY, userForm);
        model.addAttribute("roles", userManagement.getRoles());

        return "umt/user";
    }


    /**
     * Saving a user. Used for editing an existing one or creating a new one.
     * 
     * @param user
     * @param result
     * @param conversation
     * @param model
     * @return
     */
    @RequestMapping(value = EDIT_USER, method = RequestMethod.POST)
    public String doSubmit(@ModelAttribute(USER_KEY) UserForm userForm,
            Errors errors, SessionStatus conversation, Model model) {

        if (!isValid(userForm, errors)) {

            model.addAttribute("roles", userManagement.getRoles());
            return "umt/user";
        }

        userManagement.save(userForm.getDomainObject());
        conversation.setComplete();

        model.addAttribute(Minos.MESSAGE, Message.success(
                "umt.user.save.success", userForm.getUsername()));

        return UrlUtils.redirect("../users");
    }


    /**
     * Lists all {@link Role}s.
     * 
     * @param model
     * @return
     */
    @RequestMapping(LIST_ROLES)
    public String getRoles(Model model) {

        model.addAttribute("roles", userManagement.getRoles());

        return LIST_ROLES;
    }


    /**
     * Shows the form to edit an existing {@link Role}.
     * 
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = EDIT_ROLE, method = GET)
    public String showRole(@PathVariable("id") Role role, Model model) {

        return prepareRoleForm(role, model);
    }


    /**
     * Shows the form for a new {@link Role}.
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = ROLE_FORM, method = GET)
    public String showEmptyRoleForm(Model model) {

        return prepareRoleForm(BeanUtils.instantiateClass(Role.class), model);
    }


    private String prepareRoleForm(Role role, Model model) {

        Assert.notNull(role);

        model.addAttribute(ROLE_KEY, role);
        model.addAttribute("permissions", CorePermissions.ALL);

        return "umt/role";
    }


    /**
     * Saves the given {@link Role}.
     * 
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = EDIT_ROLE, method = POST)
    public String saveRole(@ModelAttribute(ROLE_KEY) Role role, Model model) {

        userManagement.save(role);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "umt.role.save.success", role.getName()));

        return UrlUtils.redirect("../roles");
    }


    /**
     * Deletes the given {@link Role}.
     * 
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = EDIT_ROLE, method = DELETE)
    public String deleteRole(@PathVariable("id") Role role, Model model) {

        userManagement.deleteRole(role);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "umt.role.delete.success", role.getName()));

        return UrlUtils.redirect("../roles");
    }
}
