package org.synyx.minos.core.security;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;


/**
 * Unit test for {@link MinosUserDetails}.
 *
 * @author Stefan Kuhn - kuhn@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MinosUserDetailsUnitTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USER_ROLE = "USER";
    private static final String EMAIL = "email@address.com";

    // Test fixture
    private User user;
    private Role role;

    @Before
    public void setUp() {

        role = new Role(USER_ROLE);
        role.add("FOO");

        user = new User(USERNAME, EMAIL, PASSWORD);
        user.addRole(role);
    }


    @Test
    public void testSomeStuff() {

        MinosUserDetails minosUserDetails = new MinosUserDetails(user);

        minosUserDetails.getUser().equals(user);
        minosUserDetails.getUsername().equals(user.getUsername());
    }
}
