package org.synyx.minos.core.module;

/**
 * Indicates that a type needs to get access to a {@link ModuleManager}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ModuleManagerAware {

    /**
     * Injects the {@link ModuleManager}.
     * 
     * @param manager
     */
    void setModuleManager(ModuleManager manager);
}
