package org.synyx.minos.core.security;

import java.util.Collection;


/**
 * Interface to mark components that know about permissions they provide. The scope of the list provided depends on the
 * implementor.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface PermissionAware {

    /**
     * Returns all permissions available.
     * 
     * @return
     */
    Collection<String> getPermissions();
}
