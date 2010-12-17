package org.synyx.minos.umt.service;

import org.synyx.minos.core.domain.Password;

/**
 * Basic interface for classes providing password creation functionality.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface PasswordCreator {

    /**
     * Generates a random password, that is used as initial password for a new user after successful registration.
     * 
     * @return a random password
     */
    Password generatePassword();

}