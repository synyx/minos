package org.synyx.minos.core.security;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;


public class TestingAuthenticationPlugin implements AuthenticationPlugin {

    @Override
    public Authentication authenticate(Authentication authentication) {

        return authentication;
    }


    @Override
    public boolean supports(Class<? extends Object> authentication) {

        return TestingAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
