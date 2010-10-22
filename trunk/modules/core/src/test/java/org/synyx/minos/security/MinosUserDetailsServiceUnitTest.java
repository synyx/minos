package org.synyx.minos.security;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Unit test for {@link MinosUserDetailsService}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MinosUserDetailsServiceUnitTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USER_ROLE = "USER";

    // Instance to test
    private MinosUserDetailsService service;

    // Dependency
    @Mock
    private UserDao userDao;

    // Test fixture
    private User user;
    private Role role;


    @Before
    public void setUp() {

        role = new Role(USER_ROLE);
        role.add("FOO");

        user = new User(USERNAME, "email@address.com", PASSWORD);
        user.addRole(role);

        service = new MinosUserDetailsService();
        service.setUserDao(userDao);
    }


    /**
     * Tests, that the service adaptor returns a valid object, if the user can't be found.
     */
    @Test
    public void testSuccessfulAuthentication() {

        when(userDao.findByUsername(USERNAME)).thenReturn(user);

        UserDetails userDetails = service.loadUserByUsername(USERNAME);
        Assert.assertEquals(userDetails.getUsername(), user.getUsername());
        Assert.assertEquals(userDetails.getPassword(), user.getPassword());

        GrantedAuthority authority = new GrantedAuthorityImpl(role.toString());

        Assert.assertTrue(userDetails.getAuthorities().contains(authority));
    }
}
