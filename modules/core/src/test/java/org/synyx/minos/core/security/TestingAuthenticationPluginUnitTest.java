package org.synyx.minos.core.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TestingAuthenticationPluginUnitTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private AuthenticationPlugin authenticationPlugin;
    private Authentication authentication;

    @Before
    public void setUp() {
        authenticationPlugin = new TestingAuthenticationPlugin();

        authentication = new TestingAuthenticationToken(USERNAME, PASSWORD);
    }

    @Test
    public void testAuthentication() {
        Authentication testAuthentication = authenticationPlugin.authenticate(authentication);

        Assert.assertEquals(testAuthentication, authentication);
    }

    @Test
    public void testSupportedAuthenticationClass() {
        Class<? extends java.lang.Object> authenticationClass = TestingAuthenticationToken.class;

        Assert.assertTrue(authenticationPlugin.supports(authenticationClass));
    }
}
