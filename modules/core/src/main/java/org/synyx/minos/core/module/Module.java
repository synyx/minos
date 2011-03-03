package org.synyx.minos.core.module;

import org.springframework.core.io.Resource;

import org.synyx.hera.core.Plugin;

import java.util.List;


/**
 * Interface for a Minos module. Natural ordering of the modules has to be implemented so that more core modules will be
 * appear later in a list of modules than their dependenants. E.g. if a module B depends on A, the natural order of the
 * modules has to be B, A.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public interface Module extends Plugin<String>, Comparable<Module> {

    /**
     * Returns the {@link Lifecycle} of the module. Will never return {@literal null}.
     *
     * @return  the lifecycle
     */
    Lifecycle getLifecycle();


    /**
     * Returns the base package of the module. Defaults to <code>org.synyx.minos.${moduleIdentifier}</code>.
     *
     * @return  the basePackage
     */
    String getBasePackage();


    /**
     * Returns the resource relative to to module's base resource.
     *
     * @param  moduleResource  a resource
     *
     * @return  the resource relative to to module's base resource
     */
    Resource getModuleResource(String moduleResource);


    /**
     * Returns the relative path to the given module resource inside the classpath.
     *
     * @param  moduleResource  a resource
     *
     * @return  the relative path to the given module resource inside the classpath
     */
    String getModuleResourcePath(String moduleResource);


    /**
     * Returns all {@link Module}s the current module depends on including transitive dependencies.
     *
     * @return  all {@link Module}s the current module depends on including transitive dependencies
     */
    List<Module> getDependencies();


    /**
     * Returns all {@link Module}s the current {@link Module} directly depends on.
     *
     * @return  all {@link Module}s the current {@link Module} directly depends on
     */
    List<Module> getDirectDependencies();


    String getIdentifier();


    /**
     * Returns whether the given type can be regarded as owned by the module.
     *
     * @param  module  a type to check
     *
     * @return  whether the given type can be regarded as owned by the module
     */
    boolean isModuleType(Class<?> module);
}
