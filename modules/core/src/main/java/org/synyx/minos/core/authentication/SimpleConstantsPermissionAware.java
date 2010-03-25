package org.synyx.minos.core.authentication;

import java.util.Collection;

import org.synyx.minos.util.Assert;


/**
 * {@link ReflectivePermissionAwareSupport} to use the collection of
 * {@link Class}es configured for reflective permission lookup.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SimpleConstantsPermissionAware extends
        ReflectivePermissionAwareSupport {

    private final Collection<Class<?>> classes;


    /**
     * Creates a new {@link SimpleConstantsPermissionAware} with the given
     * collection of {@link Class} to scan for permissions.
     * 
     * @param classes
     */
    public SimpleConstantsPermissionAware(Collection<Class<?>> classes) {

        Assert.notNull(classes);
        this.classes = classes;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.core.authentication.ReflectivePermissionAwareSupport#
     * getClassesToScan()
     */
    @Override
    protected Collection<Class<?>> getClassesToScan() {

        return classes;
    }
}
