package org.synyx.minos.core.module;

/**
 * Interface for Minos module lifecycles. Allows modules to be called on
 * startup, shutdown, installation and uninstallation.
 * <p>
 * Implementations will typically be based on {@link SimpleLifecycle} to allow
 * selective overriding of lifecycle methods.
 * <p>
 * TODO: Add methods to react on installation and uninstallation of a module
 * 
 * @see SimpleLifecycle
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface Lifecycle {

    /**
     * Indicates module start event. Implementations can execute behaviour
     * required to be executed on every start of the module.
     */
    void onStart();


    /**
     * Indicates module stop event. Implementations can execute behaviour
     * required to be executed on shutdown of the module. This might include
     * freeing resources e.g.
     */
    void onStop();
}
