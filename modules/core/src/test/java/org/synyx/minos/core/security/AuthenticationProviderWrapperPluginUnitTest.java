package org.synyx.minos.core.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class AuthenticationProviderWrapperPluginUnitTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USER_ROLE = "USER";

    AuthenticationPlugin authenticationPlugin;

    private Authentication authentication;
    private AuthenticationProvider authenticationProvider;

    @Before
    public void setUp() {
        authenticationProvider = new TestingAuthenticationProvider();

        authentication = new TestingAuthenticationToken(USERNAME, PASSWORD, USER_ROLE);

        authenticationPlugin = new AuthenticationProviderWrapperPlugin(authenticationProvider);
    }

    @Test
    public void testSupportedAuthenticationClass() {

        Class<? extends java.lang.Object> authenticationClass = TestingAuthenticationToken.class;

        Assert.assertTrue(authenticationPlugin.supports(authenticationClass));
    }

    @Test
    public void testAuthentication() {

        Authentication testAuthentication = authenticationPlugin.authenticate(authentication);

        Assert.assertEquals(testAuthentication.getPrincipal(), USERNAME);
        Assert.assertEquals(testAuthentication.getCredentials(), PASSWORD);
        Assert.assertTrue(testAuthentication.isAuthenticated());

        GrantedAuthority authority = new GrantedAuthorityImpl(USER_ROLE);
        Assert.assertTrue(testAuthentication.getAuthorities().contains(authority));
    }

}
