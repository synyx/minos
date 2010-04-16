package org.synyx.minos.umt.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.synyx.minos.umt.web.UmtUrls.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.ValidationSupport;
import org.synyx.minos.umt.service.UserAccountManagement;


/**
 * Web controller for the user account management.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@Controller
@SessionAttributes( { MyAccountController.ACCOUNT_KEY })
public class MyAccountController extends ValidationSupport<UserForm> {

    public static final String ACCOUNT_KEY = "userForm";

    private final UserAccountManagement userAccountManagement;


    @Autowired
    public MyAccountController(UserAccountManagement userAccountManagement) {

        super();
        this.userAccountManagement = userAccountManagement;
    }


    /**
     * Show the form for an existing {@link User}.
     * 
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = MYACCOUNT, method = GET)
    public String myAccount(@CurrentUser User user, Model model) {

        model.addAttribute(ACCOUNT_KEY, new UserForm(user));

        return "myaccount";
    }


    /**
     * Saves an existing {@link User}.
     * 
     * @param userForm
     * @param errors
     * @param conversation
     * @param model
     * @return
     */
    @RequestMapping(value = MYACCOUNT, method = PUT)
    public String saveMyAccount(@ModelAttribute(ACCOUNT_KEY) UserForm userForm, Errors errors,
            SessionStatus conversation, @CurrentUser User currentUser, Model model) {

        User user = userForm.getDomainObject();
        if (!currentUser.getId().equals(user.getId())) {
            model.addAttribute(Core.MESSAGE, Message.error("umt.user.error.myuser.idmismatch"));
            return "myaccount";
        }

        if (!currentUser.getUsername().equals(user.getUsername())) {
            model.addAttribute(Core.MESSAGE, Message.error("umt.user.error.myuser.usernamemusmatch"));
            return "myaccount";
        }

        if (!isValid(userForm, errors)) {

            return "myaccount";
        }

        userAccountManagement.saveUserAccount(user);
        conversation.setComplete();

        model.addAttribute(Core.MESSAGE, Message.success("umt.user.save.success", userForm.getUsername()));

        return UrlUtils.redirect(MYACCOUNT);
    }
}
