package org.synyx.minos.core.security;

import org.springframework.security.core.AuthenticationException;


/**
 * Thrown by {@link org.synyx.minos.core.security.PluginAuthenticationProvider} if no  {@link org.synyx.minos.core.security.AuthenticationPlugin}
 * could be found that supports the presented {@link org.springframework.security.core.Authentication} object.
 */
public class PluginNotFoundException extends AuthenticationException {

    /**
     * Constructs a <code>PluginNotFoundException</code> with the specified
     * message.
     *
     * @param msg the detail message
     */
    public PluginNotFoundException(String msg) {

        super(msg);
    }


    /**
     * Constructs a <code>PluginNotFoundException</code> with the specified
     * message and root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public PluginNotFoundException(String msg, Throwable t) {

        super(msg, t);
    }
}
