package org.synyx.minos;

import org.synyx.minos.core.domain.User;


/**
 * Utility class to hold constants useful during testing.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class TestConstants {

    public static final User USER = new User("username", "email@address.com", "password");

    static {
        USER.setId(1L);
    }


    private TestConstants() {

    }
}
