package org.synyx.minos.core.module;

/**
 * Exception being thrown on errors during module lifecycle phases.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class ModuleLifecycleException extends RuntimeException {

    private static final long serialVersionUID = -1251967443788030845L;

    /**
     * Creates a new {@link ModuleLifecycleException}.
     *
     * @param  message  the detail message
     */
    public ModuleLifecycleException(String message) {

        super(message);
    }
}
