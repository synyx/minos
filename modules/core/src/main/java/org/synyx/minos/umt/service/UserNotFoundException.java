package org.synyx.minos.umt.service;

import org.synyx.minos.core.domain.User;


/**
 * Exception to express a {@link User} not found.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 134812981234L;


    public UserNotFoundException(String username) {

        super(String.format("User %s not found!", username));
    }


    public UserNotFoundException(Long invalidId) {

        super(String.format("No user found for id %s!", invalidId));
    }

}
