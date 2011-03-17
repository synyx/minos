package org.synyx.minos.core;

/**
 * Collection of system wide constants.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public final class Core {

    public static final String IDENTIFIER = "core";

    // Base namespace of properties to avoid conflicts with other apps
    private static final String MINOS_BASE = "com.synyx.minos";

    /**
     * Key for system messages.
     */
    public static final String MESSAGE = MINOS_BASE + ".message";

    /**
     * The key under which events are registered.
     */
    public static final String EVENT_KEY = MINOS_BASE + ".web.event";

    private Core() {
    }
}
