package org.synyx.minos.core.security;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.synyx.minos.util.Assert;

import java.util.Collections;
import java.util.List;


public class PluginAuthenticationProvider implements AuthenticationProvider, InitializingBean {

    private List<AuthenticationPlugin> authenticationPlugins = Collections.emptyList();

    public List<AuthenticationPlugin> getAuthenticationPlugins() {

        return authenticationPlugins;
    }


    public void setAuthenticationPlugins(List<AuthenticationPlugin> authenticationPlugins) {

        Assert.notNull(authenticationPlugins, "Authentication plugins list cannot be null");

        this.authenticationPlugins = authenticationPlugins;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        if (authenticationPlugins.isEmpty()) {
            throw new IllegalArgumentException("A list of AuthenticationPlugins is required");
        }
    }


    @Override
    public Authentication authenticate(Authentication authentication) {

        Authentication result = null;
        AuthenticationException lastException = null;

        for (AuthenticationPlugin authenticationPlugin : authenticationPlugins) {
            try {
                result = authenticationPlugin.authenticate(authentication);
            } catch (AuthenticationException e) {
                lastException = e;
            }

            if (result != null && result.isAuthenticated()) {
                return result;
            }
        }

        if (lastException == null) {
            lastException = new PluginNotFoundException("No AuthenticationPlugin found");
        }

        throw lastException;
    }


    @Override
    public boolean supports(Class<? extends Object> authentication) {

        boolean authenticationSupported = false;

        for (AuthenticationPlugin authenticationPlugin : authenticationPlugins) {
            authenticationSupported = authenticationPlugin.supports(authentication);

            if (authenticationSupported) {
                break;
            }
        }

        return authenticationSupported;
    }
}
