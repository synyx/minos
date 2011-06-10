package org.synyx.minos.core.module;

/**
 * Interface for components that are tied to a {@link Module}. Mainly used to let implementors be sorted by the
 * referenced {@link Module} by {@link org.synyx.minos.core.module.internal.ModuleAwareComparator}.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public interface ModuleAware {

    /**
     * Returns the {@link Module} the component is bound to.
     *
     * @return  the {@link Module} the component is bound to
     */
    Module getModule();
}
