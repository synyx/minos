package org.synyx.minos.core.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link User}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UserUnitTest {

    private User user;


    @Before
    public void setUp() {

        user = new User("username", "email@adress.com", "password");
    }


    @Test(expected = IllegalArgumentException.class)
    public void preventsNullValuesForRoles() throws Exception {

        user.addRole(null);
    }


    @Test
    public void preventsDuplicateRoles() throws Exception {

        Role role = new Role("Sample role");

        user.addRole(role);
        user.addRole(role);

        assertEquals(1, user.getRoles().size());
    }


    @Test
    public void allowsNoPassword() throws Exception {

        new User("foo", "bar");
    }
}
