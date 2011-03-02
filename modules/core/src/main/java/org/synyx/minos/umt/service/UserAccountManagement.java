package org.synyx.minos.umt.service;

import org.synyx.minos.core.domain.User;


/**
 * Interface for the user account management.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public interface UserAccountManagement {

    /**
     * Saves an existing {@link User}. Has to restrict changes to non security related values.
     *
     * @param user
     * @return
     */
    void saveUserAccount(User user);
}
