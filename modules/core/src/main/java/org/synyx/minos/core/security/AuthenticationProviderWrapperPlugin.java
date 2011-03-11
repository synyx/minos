package org.synyx.minos.core.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;


public class AuthenticationProviderWrapperPlugin implements AuthenticationPlugin {

    private AuthenticationProvider authenticationProvider;

    public AuthenticationProviderWrapperPlugin(AuthenticationProvider authenticationProvider) {

        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        return authenticationProvider.authenticate(authentication);
    }


    @Override
    public boolean supports(Class<? extends Object> delimiter) {

        return authenticationProvider.supports(delimiter);
    }
}
