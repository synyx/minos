package org.synyx.minos.core.security;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * Simple {@link AuthenticationPlugin} for testing purposes which will return the provided {@link Authentication} object
 * unaltered.
 *
 * @author Jochen Schalanda
 */
public class TestingAuthenticationPlugin implements AuthenticationPlugin {

    /**
     * Returns the provided {@link Authentication} object with the same contract as {@link
     * org.springframework.security.authentication.AuthenticationManager#authenticate(Authentication)}.
     *
     * @param authentication the authentication request object.
     *
     * @return the unaltered authenticat request object.
     */
    @Override
    public Authentication authenticate(Authentication authentication) {

        return authentication;
    }


    @Override
    public boolean supports(Class<? extends Object> authentication) {

        return TestingAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
