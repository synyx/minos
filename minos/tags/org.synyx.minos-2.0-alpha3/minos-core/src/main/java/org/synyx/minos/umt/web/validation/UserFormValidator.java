package org.synyx.minos.umt.web.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.validation.EmailValidator;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.web.UserForm;


/**
 * Validator for {@code User} objects. Checks whether the following criterias
 * are given:
 * <ul>
 * <li>Username set</li>
 * <li>Email set and valid</li>
 * <li>Username does not already exist (for new users)</li>
 * <li>Roles not empty</li>
 * <li>Password set (for new users)</li>
 * </ul>
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UserFormValidator implements Validator {

    public static final String USERNAME_EMPTY = "umt.user.username.empty";
    public static final String USERNAME_ALREADY_EXISTS =
            "umt.user.username.alreadyexists";
    public static final String PASSWORD_EMPTY = "umt.user.password.empty";
    public static final String PASSWORDS_DO_NOT_MATCH =
            "umt.user.password.nomatch";
    public static final String EMAIL_ALREADY_EXISTS =
            "umt.user.email.alreadyexists";

    public static final String ROLES_EMPTY = "umt.user.roles.empty";

    private UserManagement userManagement;
    private final EmailValidator emailValidator = new EmailValidator();


    /**
     * Setter to inject user management.
     * 
     * @param userManagement the userManagement to set
     */
    public void setUserManagement(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {

        return UserForm.class.equals(clazz);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

        UserForm user = (UserForm) target;

        // Valid email address
        errors.pushNestedPath("emailAddress");
        emailValidator.validate(user.getEmailAddress(), errors);
        errors.popNestedPath();

        // Reject empty username
        if (StringUtils.isBlank(user.getUsername())) {
            errors.rejectValue("username", USERNAME_EMPTY);
        }

        // Reject not matching passwords
        if (!StringUtils.equals(user.getNewPassword(), user
                .getRepeatedPassword())) {
            errors.rejectValue("repeatedPassword", PASSWORDS_DO_NOT_MATCH);
        }

        if (user.getRoles().isEmpty()) {
            errors.rejectValue("roles", ROLES_EMPTY);
        }

        // Rejects usernames of already existing users
        if (user.isNew()) {

            if (StringUtils.isBlank(user.getNewPassword())) {
                errors.rejectValue("newPassword", PASSWORD_EMPTY);
            }

            boolean usernameExists =
                    null != userManagement.getUser(user.getUsername());

            if (usernameExists) {
                errors.rejectValue("username", USERNAME_ALREADY_EXISTS);
            }

            boolean emailExists =
                    null != userManagement.getUserByEmail(user
                            .getEmailAddress());

            if (emailExists) {
                errors.rejectValue("emailAddress", EMAIL_ALREADY_EXISTS);
            }

            // Prevent "form" as username, as it is being used in the URL
            if (StringUtils.equalsIgnoreCase("form", user.getUsername())) {
                errors.rejectValue("username", USERNAME_ALREADY_EXISTS);
            }

        } else {
            User persitentUserByEmail =
                    userManagement.getUserByEmail(user.getEmailAddress());
            if (persitentUserByEmail != null
                    && !user.getDomainObject().equals(persitentUserByEmail)) {
                errors.rejectValue("emailAddress", EMAIL_ALREADY_EXISTS);
            }

            User persitentUserByName =
                    userManagement.getUser(user.getUsername());
            if (persitentUserByName != null
                    && !user.getDomainObject().equals(persitentUserByName)) {
                errors.rejectValue("username", USERNAME_ALREADY_EXISTS);
            }
        }
    }
}
