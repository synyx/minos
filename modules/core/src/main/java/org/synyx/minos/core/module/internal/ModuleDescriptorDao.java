package org.synyx.minos.core.module.internal;

import org.synyx.hades.dao.GenericDao;


/**
 * DAO interface to manage {@link ModuleDescriptor} instances.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ModuleDescriptorDao extends GenericDao<ModuleDescriptor, Long> {

    /**
     * Returns the {@link ModuleDescriptor} for the given identifier.
     * 
     * @param moduleIdentifier
     * @return
     */
    ModuleDescriptor findByIdentifier(String moduleIdentifier);
}
