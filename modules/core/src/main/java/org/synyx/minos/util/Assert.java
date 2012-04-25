package org.synyx.minos.util;

import org.synyx.hades.domain.Persistable;


/**
 * Simple wrapper for Spring's {@link org.springframework.util.Assert} class to not directly depend on it in client
 * classes.
 *
 * @author Oliver Gierke - gierke@synyx.de
 * @author Aljona Murygina - murygina@synyx.de
 */
public abstract class Assert extends org.synyx.util.Assert {

    /**
     * Private constructor to prevent instantiation.
     */
    private Assert() {
    }
    
    /**
     * Asserts that the given {@link Persistable} is not {@literal null} and not new.
     *
     * @param persistable
     */
    public static void notNew(Persistable<?> persistable) {

        org.springframework.util.Assert.notNull(persistable);
        org.springframework.util.Assert.isTrue(!persistable.isNew());
    }
}
