package org.synyx.minos.core.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PluginAuthenticationProviderUnitTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USER_ROLE = "USER";

    PluginAuthenticationProvider authenticationProvider;

    private Authentication authentication;
    private AuthenticationPlugin authenticationPlugin;
    private List<AuthenticationPlugin> authenticationPlugins;

    @Before
    public void setUp() {
        authenticationProvider = new PluginAuthenticationProvider();

        authenticationPlugin = new TestingAuthenticationPlugin();
        authenticationPlugins = Arrays.asList(authenticationPlugin);

        authentication = new TestingAuthenticationToken(USERNAME, PASSWORD, USER_ROLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAuthenticationPluginListNull() {
        authenticationProvider.setAuthenticationPlugins(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAuthenticationPluginListEmpty() throws Exception {
        authenticationProvider.setAuthenticationPlugins(Collections.<AuthenticationPlugin>emptyList());
        authenticationProvider.afterPropertiesSet();
    }

    @Test(expected = PluginNotFoundException.class)
    public void testNoPluginFound() throws Exception {
        authenticationProvider.authenticate(authentication);
    }

    @Test
    public void testSupportedAuthenticationClass() {
        Class<? extends java.lang.Object> authenticationClass = TestingAuthenticationToken.class;
        authenticationProvider.setAuthenticationPlugins(authenticationPlugins);

        Assert.assertTrue(authenticationProvider.supports(authenticationClass));
    }

    @Test
    public void testAuthentication() {
        authenticationProvider.setAuthenticationPlugins(authenticationPlugins);

        Authentication testAuthentication = authenticationProvider.authenticate(authentication);

        Assert.assertEquals(testAuthentication.getPrincipal(), USERNAME);
        Assert.assertEquals(testAuthentication.getCredentials(), PASSWORD);
        Assert.assertTrue(testAuthentication.isAuthenticated());

        GrantedAuthority authority = new GrantedAuthorityImpl(USER_ROLE);
        Assert.assertTrue(testAuthentication.getAuthorities().contains(authority));
    }
}
