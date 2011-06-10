package org.synyx.minos.core.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.service.UserManagement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MinosUserDetailsContextMapperUnitTest {


    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USER_ROLE = "USER";
    private static final String EMAIL = "email@address.com";

    // Instance to test
    private MinosUserDetailsContextMapper contextMapper;

    // Dependency
    @Mock
    private UserManagement userManagement;

    @Mock
    private DirContextOperations context;

    // Test fixture
    private User user;
    private Role role;


    @Before
    public void setUp() {

        role = new Role(USER_ROLE);
        role.add("FOO");

        user = new User(USERNAME, EMAIL, PASSWORD);
        user.addRole(role);

        contextMapper = new MinosUserDetailsContextMapper(userManagement);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyDefaultRoles() {
        contextMapper.setDefaultRoles("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDefaultRoles() {
        contextMapper.setDefaultRoles(null);
    }

    @Test
    public void testSuccessfulMappingExistingUser() {

        when(userManagement.getUser(USERNAME)).thenReturn(user);

        GrantedAuthority authority = new GrantedAuthorityImpl(role.toString());
        List<GrantedAuthority> grantedAuthorities = Arrays.asList(authority);

        UserDetails userDetails = contextMapper.mapUserFromContext(context, USERNAME, grantedAuthorities);

        Assert.assertEquals(userDetails.getUsername(), user.getUsername());
        Assert.assertEquals(userDetails.getPassword(), user.getPassword().toString());
        Assert.assertTrue(userDetails.getAuthorities().contains(authority));
    }

    @Test
    public void testSuccessfulMappingNonExistingUser() {

        when(userManagement.getUser(USERNAME)).thenReturn(null);
        when(context.getObjectAttribute("mail")).thenReturn(EMAIL);

        contextMapper.setDefaultRoles(USER_ROLE);

        UserDetails userDetails = contextMapper.mapUserFromContext(context, USERNAME, Collections.<GrantedAuthority>emptyList());

        Assert.assertEquals(userDetails.getUsername(), user.getUsername());
        Assert.assertNull(userDetails.getPassword());
    }
}
