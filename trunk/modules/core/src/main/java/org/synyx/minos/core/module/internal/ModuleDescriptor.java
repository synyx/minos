package org.synyx.minos.core.module.internal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.synyx.hades.domain.AbstractPersistable;
import org.synyx.minos.core.module.Module;


/**
 * Simple entity to capture module state.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class ModuleDescriptor extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -3891002529927598035L;

    @Column(unique = true)
    private String identifier;

    @Column(nullable = false)
    private boolean installed = false;


    protected ModuleDescriptor() {

    }


    /**
     * Creates a new {@link ModuleDescriptor} for the module with the given identifier.
     * 
     * @param identifier
     */
    public ModuleDescriptor(String identifier) {

        this.identifier = identifier;
    }


    /**
     * Create a new {@link ModuleDescriptor} for the given module.
     * 
     * @param module
     */
    public ModuleDescriptor(Module module) {

        this.identifier = module.getIdentifier();
    }


    /**
     * @return the name
     */
    public String getIdentifier() {

        return identifier;
    }


    /**
     * Mark the module as installed.
     * 
     * @param installed the installed to set
     */
    public void setInstalled() {

        this.installed = true;
    }


    /**
     * Marks the module as uninstalled.
     */
    public void setUninstalled() {

        this.installed = false;
    }


    /**
     * Returns whether the module is installed.
     * 
     * @return
     */
    public boolean isInstalled() {

        return this.installed;
    }
}
