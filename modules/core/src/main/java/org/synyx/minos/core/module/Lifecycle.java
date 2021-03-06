package org.synyx.minos.core.module;

/**
 * Interface for Minos module lifecycles. Allows modules to be called on startup, shutdown, installation and
 * uninstallation.
 *
 * <p>Implementations will typically be based on {@link SimpleNoOpLifecycle} to allow selective overriding of lifecycle
 * methods.
 *
 * <p>Furthermore implementations should consider to define an order the wish to be executed in. This way, they can rely
 * on other {@link Lifecycle}s being invoked before or after in the appropriate phase.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 * @see  SimpleNoOpLifecycle
 * @see  org.springframework.core.annotation.Order
 * @see  org.springframework.core.Ordered
 */
public interface Lifecycle {

    /**
     * Indicates module start event. Implementations can execute behaviour required to be executed on every start of the
     * module.
     */
    void onStart();


    /**
     * Indicates module stop event. Implementations can execute behaviour required to be executed on shutdown of the
     * module. This might include freeing resources e.g.
     */
    void onStop();


    /**
     * Installation phase of a module's lifecycle. This phase will only be invoked once if it finishes successfully.
     */
    void install();
}
