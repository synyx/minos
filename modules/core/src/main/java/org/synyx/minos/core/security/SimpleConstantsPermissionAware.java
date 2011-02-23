package org.synyx.minos.core.security;

import org.synyx.minos.util.Assert;

import java.util.Collection;


/**
 * {@link ReflectivePermissionAwareSupport} to use the collection of {@link Class}es configured for reflective
 * permission lookup.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class SimpleConstantsPermissionAware extends ReflectivePermissionAwareSupport {

    private final Collection<Class<?>> classes;

    /**
     * Creates a new {@link SimpleConstantsPermissionAware} with the given collection of {@link Class} to scan for
     * permissions.
     *
     * @param  classes  a collection of classes to scan for permissions
     */
    public SimpleConstantsPermissionAware(Collection<Class<?>> classes) {

        Assert.notNull(classes);
        this.classes = classes;
    }

    @Override
    protected Collection<Class<?>> getClassesToScan() {

        return classes;
    }
}
