package org.synyx.minos.core.module;

import java.util.List;

import org.springframework.core.io.Resource;
import org.synyx.hera.core.Plugin;


/**
 * Interface for a Minos module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface Module extends Plugin<String>, Comparable<Module> {

    /**
     * Returns the {@link Lifecycle} of the module. Will never return {@liteal
     * null}.
     * 
     * @return the lifecycle
     */
    Lifecycle getLifecycle();


    /**
     * Returns the base package of the module. Defaults to
     * <code>org.synyx.minos.${moduleIdentifier}</code>.
     * 
     * @return the basePackage
     */
    String getBasePackage();


    /**
     * Returns the resource relative to to module's base resource.
     * 
     * @param moduleResource
     * @return
     */
    Resource getModuleResource(String moduleResource);


    /**
     * Returns the relative path to the given module resource inside the
     * classpath.
     * 
     * @param moduleResource
     * @return
     */
    String getModuleResourcePath(String moduleResource);


    /**
     * Returns all {@link Module}s the current module depends on including
     * transitive dependencies.
     * 
     * @return
     */
    List<Module> getDependants();


    /**
     * Returns all {@link Module}s the current {@link Module} directly depends
     * on.
     * 
     * @return
     */
    List<Module> getDirectDependants();


    /**
     * Returns the identifier of the module.
     * 
     * @return the identifier
     */
    String getIdentifier();


    /**
     * Returns whether the given type can be regarded as owned by the module.
     * 
     * @param module
     * @return
     */
    boolean isModuleType(Class<?> module);
}