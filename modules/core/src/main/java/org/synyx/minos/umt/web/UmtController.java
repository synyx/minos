package org.synyx.minos.umt.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.synyx.minos.umt.UmtPermissions.*;
import static org.synyx.minos.umt.web.UmtUrls.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.security.AuthenticationService;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.PageWrapper;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.ValidationSupport;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.service.UserNotFoundException;
import org.synyx.minos.util.Assert;


/**
 * Web controller for the user management providing access to most of the user management functionality.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Controller
@SessionAttributes( { UmtController.ROLE_KEY, UmtController.USER_KEY })
@RolesAllowed(UMT_ADMIN)
public class UmtController extends ValidationSupport<UserForm> {

    public static final String USER_KEY = "userForm";
    public static final String ROLE_KEY = "role";
    public static final String USERS_KEY = "users";

    private final UserManagement userManagement;
    private final AuthenticationService authenticationService;

    private Comparator<String> permissionComparator;


    protected UmtController() {

        this(null, null);
    }


    @Autowired
    public UmtController(UserManagement userManagement, AuthenticationService authenticationService) {

        super();
        this.userManagement = userManagement;
        this.authenticationService = authenticationService;
    }


    /**
     * Initialize binders for this controller.
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(DataBinder binder) {

        binder.registerCustomEditor(Role.class, new RolePropertyEditor(userManagement));
    }


    /**
     * Redirect to entry page.
     * 
     * @return
     */
    @RequestMapping(MODULE)
    public String index() {

        return UrlUtils.redirect(USERS);
    }


    /**
     * Show all users.
     * 
     * @return
     */
    @RequestMapping(value = USERS, method = GET)
    public String getUsers(Pageable pageable, Model model, @RequestParam(value = "role", required = false) Role role,
            @CurrentUser User user) {

        model.addAttribute("currentUser", user);

        Page<User> page = null;
        if (role == null) {
            page = userManagement.getUsers(pageable);
        } else {
            page = userManagement.getUsersByRole(role, pageable);
        }

        model.addAttribute(USERS_KEY, PageWrapper.wrap(page));
        model.addAttribute("roles", userManagement.getRoles());
        return USERS;
    }


    /**
     * Delete a user.
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = USER, method = DELETE)
    public String deleteUser(@PathVariable("id") User user, Model model, @CurrentUser User currentUser) {

        String targetView = UrlUtils.redirect(USERS);

        if (null == user) {
            model.addAttribute(Core.MESSAGE, Message.error("umt.user.delete.usernamerequired"));
            return targetView;
        }

        try {

            boolean currentUserGiven = null != currentUser;

            if (currentUserGiven && currentUser.equals(user)) {
                model.addAttribute(Core.MESSAGE, Message.error("umt.user.delete.cannotdeleteherself"));
                return targetView;
            }

            userManagement.delete(user);

            model.addAttribute(Core.MESSAGE, Message.success("umt.user.delete.success", user.getUsername()));

        } catch (UserNotFoundException e) {

            model.addAttribute(Core.MESSAGE, Message.error("user.delete.invalidusername", user.getId()));
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
    @RequestMapping(value = USER, method = GET)
    public String setupForm(@PathVariable("id") User user, Model model) {

        if (null == user) {
            model.addAttribute(Core.MESSAGE, Message.error("umt.user.invalid"));
            return UrlUtils.redirect(USERS);
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

        return populateFormModel(BeanUtils.instantiateClass(UserForm.class), model);
    }


    private String populateFormModel(UserForm userForm, Model model) {

        model.addAttribute(USER_KEY, userForm);
        model.addAttribute("roles", userManagement.getRoles());

        return "/umt/user";
    }


    /**
     * Saving a new user.
     * 
     * @param user
     * @param result
     * @param conversation
     * @param model
     * @return
     */
    @RequestMapping(value = USERS, method = POST)
    public String saveNewUser(@ModelAttribute(USER_KEY) UserForm userForm, Errors errors, SessionStatus conversation,
            Model model) {

        return saveUser(userForm, USERS, errors, conversation, model);
    }


    /**
     * Saving a existing user.
     * 
     * @param user
     * @param result
     * @param conversation
     * @param model
     * @return
     */
    @RequestMapping(value = USER, method = PUT)
    public String saveExistingUser(@ModelAttribute(USER_KEY) UserForm userForm, Errors errors,
            SessionStatus conversation, Model model) {

        return saveUser(userForm, USERS, errors, conversation, model);
    }


    String saveUser(UserForm userForm, String redirectUrl, Errors errors, SessionStatus conversation, Model model) {

        if (!isValid(userForm, errors)) {

            model.addAttribute("roles", userManagement.getRoles());
            return "/umt/user";
        }

        userManagement.save(userForm.getDomainObject());
        conversation.setComplete();

        model.addAttribute(Core.MESSAGE, Message.success("umt.user.save.success", userForm.getUsername()));

        return UrlUtils.redirect(redirectUrl);
    }


    /**
     * Lists all {@link Role}s.
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = ROLES, method = GET)
    public String getRoles(Model model) {

        model.addAttribute("roles", userManagement.getRoles());

        return ROLES;
    }


    /**
     * Shows the form to edit an existing {@link Role}.
     * 
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = ROLE, method = GET)
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
        Collection<String> perm = authenticationService.getPermissions();
        List<String> permissions = new ArrayList<String>();
        permissions.addAll(perm);
        Collections.sort(permissions, permissionComparator);
        model.addAttribute("permissions", permissions);

        return "/umt/role";
    }


    /**
     * Saves the new {@link Role}.
     * 
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = ROLES, method = POST)
    public String saveNewRole(@ModelAttribute(ROLE_KEY) Role role, Model model) {

        userManagement.save(role);

        model.addAttribute(Core.MESSAGE, Message.success("umt.role.save.success", role.getName()));

        return UrlUtils.redirect(ROLES);
    }


    /**
     * Saves the existing {@link Role}.
     * 
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = ROLE, method = PUT)
    public String saveExistingRole(@ModelAttribute(ROLE_KEY) Role role, Model model) {

        userManagement.save(role);

        model.addAttribute(Core.MESSAGE, Message.success("umt.role.save.success", role.getName()));

        return UrlUtils.redirect(ROLES);
    }


    /**
     * Deletes the given {@link Role}.
     * 
     * @param role
     * @param model
     * @return
     */
    @RequestMapping(value = ROLE, method = DELETE)
    public String deleteRole(@PathVariable("id") Role role, Model model) {

        userManagement.deleteRole(role);

        model.addAttribute(Core.MESSAGE, Message.success("umt.role.delete.success", role.getName()));

        return UrlUtils.redirect(ROLES);
    }


    /**
     * @param permissionComparator the permissionComparator to set
     */
    public void setPermissionComparator(Comparator<String> permissionComparator) {

        this.permissionComparator = permissionComparator;
    }

}
