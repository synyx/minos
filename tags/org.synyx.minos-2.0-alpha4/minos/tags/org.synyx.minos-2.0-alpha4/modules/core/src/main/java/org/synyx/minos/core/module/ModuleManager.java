package org.synyx.minos.core.module;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 * Interface for a module manager.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ModuleManager extends
        ApplicationListener<ContextRefreshedEvent> {

    /**
     * Returns whether the module with the given identifier is available, which
     * means it is installed and successfully started.
     * 
     * @param identifier
     * @return
     */
    boolean isAvailable(String identifier);


    /**
     * Returns all registered modules. They will be ordered with the more core
     * modules returned later.
     * 
     * @return
     */
    List<Module> getModules();
}
