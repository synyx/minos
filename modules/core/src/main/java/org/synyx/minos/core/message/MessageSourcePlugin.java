package org.synyx.minos.core.message;

import org.springframework.context.MessageSource;

import org.synyx.hera.core.Plugin;


/**
 * Binds the Spring {@link MessageSource} to the Hera {@link Plugin} interface to allow pluggable {@link MessageSource}
 * that can be selected by prefix.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface MessageSourcePlugin extends MessageSource, Plugin<String> {

    /**
     * Returns whether the {@link MessageSourcePlugin} supports resolving messages with the given prefix.
     *
     * @param prefix
     * @see Plugin
     */
    @Override
    boolean supports(String prefix);
}
