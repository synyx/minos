package org.synyx.minos.umt.web.validation;

import org.apache.commons.lang.StringUtils;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Validator for {@code >Role} objects.
 *
 * @author  Marc Kannegiesser - kannegiesser@synyx.de
 */
public class RoleValidator implements Validator {

    public static final String ROLENAME_EMPTY = "umt.role.name.empty";
    public static final String ROLENAME_EXISTS = "umt.role.name.alreadyexists";

    private UserManagement userManagement;

    /**
     * Setter to inject user management.
     *
     * @param  userManagement  the userManagement to set
     */
    public void setUserManagement(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    public boolean supports(Class<?> clazz) {

        return Role.class.equals(clazz);
    }


    public void validate(Object target, Errors errors) {

        Role role = (Role) target;

        if (StringUtils.isBlank(role.getName())) {
            errors.rejectValue("name", ROLENAME_EMPTY);
        } else {
            Role existing = userManagement.getRole(role.getName());

            if (existing != null && !existing.getId().equals(role.getId()))
                errors.rejectValue("name", ROLENAME_EXISTS);
        }
    }
}
