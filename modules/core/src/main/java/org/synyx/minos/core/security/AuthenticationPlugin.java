package org.synyx.minos.core.security;

import org.springframework.security.core.Authentication;

import org.synyx.hera.core.Plugin;


/**
 * Indicates a class can process a specific  {@link
 * org.springframework.security.core.Authentication} implementation.
 *
 * @author Jochen Schalanda
 */
public interface AuthenticationPlugin extends Plugin<java.lang.Class<? extends java.lang.Object>> {

     /**
     * Performs authentication with the same contract as {@link
     * org.springframework.security.authentication.AuthenticationManager#authenticate(Authentication)}.
     *
     * @param authentication the authentication request object.
     *
     * @return a fully authenticated object including credentials. May return <code>null</code> if the
     *         <code>AuthenticationProvider</code> is unable to support authentication of the passed
     *         <code>Authentication</code> object. In such a case, the next <code>AuthenticationProvider</code> that
     *         supports the presented <code>Authentication</code> class will be tried.
     */
    Authentication authenticate(Authentication authentication);
}
