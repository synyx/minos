package org.synyx.minos.umt.service;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.security.CorePermissions;


/**
 * Interface for the user account management.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public interface UserAccountManagement {

    /**
     * Saves an existing {@link User}. Only changes attributes a user with
     * {@link CorePermissions#NOPERMISSION} is allowed to edit.
     * 
     * @param user
     * @return
     */
    void saveUserAccount(User user);
}
