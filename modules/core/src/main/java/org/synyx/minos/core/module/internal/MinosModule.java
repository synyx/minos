package org.synyx.minos.core.module.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.synyx.minos.core.module.Lifecycle;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.util.Assert;


/**
 * Core class to represent information for a Minos module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosModule implements Module {

    private static final String RESOURCE_BASE_TEMPLATE = "META-INF/minos/%s";
    private static final String DEFAULT_PACKAGE_TEMPLATE = "org.synyx.minos.%s";
    private static final Lifecycle NO_OP_LIFECYCLE = new SimpleNoOpLifecycle();

    private final String identifier;
    private String basePackage;
    private String resourceBase;

    private List<Module> modules = new ArrayList<Module>();

    private Lifecycle lifecycle = NO_OP_LIFECYCLE;


    /**
     * Creates a new {@link MinosModule} for the given identifier.
     * 
     * @param identifier
     */
    public MinosModule(String identifier) {

        Assert.hasText(identifier, "Module identifier must not be empty or null!");

        this.identifier = identifier;
        this.basePackage = String.format(DEFAULT_PACKAGE_TEMPLATE, identifier);
        this.resourceBase = String.format(RESOURCE_BASE_TEMPLATE, identifier);
    }


    /**
     * Sets the base package of the module. This package will be used for all kinds of lookups.
     * 
     * @param basePackage the basePackage to set
     */
    public void setBasePackage(String basePackage) {

        this.basePackage = basePackage;
    }


    /**
     * Sets the {@link Module}s the current {@link Module} depends on. Will filter {@literal null} values and apply
     * {@link Set} semantics to avoid duplicates.
     * 
     * @param modules the modules to set
     */
    public void setDependsOn(List<? extends Module> modules) {

        List<Module> result = new ArrayList<Module>();

        for (Module module : modules) {

            if (module != null && !result.contains(module)) {
                result.add(module);
            }
        }

        this.modules = Collections.unmodifiableList(result);
    }


    /*
     * (non-Javadoc)
     * @see org.synyx.minos.core.module.Module#getDirectDependencies()
     */
    @Override
    public List<Module> getDirectDependencies() {

        return this.modules;
    }


    /*
     * (non-Javadoc)
     * @see org.synyx.minos.core.module.Module#getDependencies()
     */
    @Override
    public List<Module> getDependencies() {

        Set<Module> alreadyFound = new HashSet<Module>();
        alreadyFound.add(this);

        return getDependencies(this, alreadyFound);
    }


    /**
     * Recursively retrieves the given modules dependencies.
     * 
     * @param module
     * @param alreadyFound
     * @return
     */
    private static List<Module> getDependencies(Module module, Set<Module> alreadyFound) {

        List<Module> result = new ArrayList<Module>();
        List<Module> toDigDeeper = new ArrayList<Module>();

        for (Module dependant : module.getDirectDependencies()) {

            if (!alreadyFound.contains(dependant)) {

                result.add(dependant);
                alreadyFound.add(dependant);
                toDigDeeper.add(dependant);
            }
        }

        for (Module dependant : toDigDeeper) {

            result.addAll(getDependencies(dependant, alreadyFound));
        }

        return result;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.Module#getLifecycle()
     */
    @Override
    public Lifecycle getLifecycle() {

        return lifecycle;
    }


    /**
     * Sets the {@link Lifecycle} to be used for the module.
     * 
     * @param lifecycle the lifecycle to set
     */
    public void setLifecycle(Lifecycle lifecycle) {

        this.lifecycle = lifecycle == null ? NO_OP_LIFECYCLE : lifecycle;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.Module#getBasePackage()
     */
    @Override
    public String getBasePackage() {

        return basePackage;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.Module#getModuleResource(java.lang.String)
     */
    @Override
    public Resource getModuleResource(String moduleResource) {

        return new ClassPathResource(getModuleResourcePath(moduleResource));
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.Module#getModuleResourcePath(java.lang.String )
     */
    @Override
    public String getModuleResourcePath(String moduleResource) {

        return String.format("%s/%s", resourceBase, moduleResource);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.Module#getIdentifier()
     */
    public String getIdentifier() {

        return identifier;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.Module#isModuleType(java.lang.Class)
     */
    @Override
    public boolean isModuleType(Class<?> type) {

        if (type == null) {
            return false;
        }

        String packageName = type.getPackage().getName();
        return packageName.startsWith(getBasePackage());
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hera.core.Plugin#supports(java.lang.Object)
     */
    @Override
    public boolean supports(String delimiter) {

        return this.identifier.equals(delimiter);
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Module module) {

        if (module == null) {
            return -1;
        }

        if (module.getDependencies().contains(this)) {
            return 1;
        }

        if (this.getDependencies().contains(module)) {
            return -1;
        }

        return 0;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getBasePackage();
    }
}
