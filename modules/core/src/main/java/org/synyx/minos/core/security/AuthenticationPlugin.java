package org.synyx.minos.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.synyx.hera.core.Plugin;


public interface AuthenticationPlugin extends Plugin<java.lang.Class<? extends java.lang.Object>> {

    Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
