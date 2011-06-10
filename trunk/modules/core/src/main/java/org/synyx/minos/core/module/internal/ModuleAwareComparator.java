package org.synyx.minos.core.module.internal;

import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.ModuleAware;

import java.io.Serializable;

import java.util.Comparator;


/**
 * {@link Comparator} implementation to order implementations of {@link ModuleAware} by comparing the underlying
 * {@link Module} instances.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleAwareComparator implements Comparator<ModuleAware>, Serializable {

    private static final long serialVersionUID = 6644747725130777645L;

    @Override
    public int compare(ModuleAware first, ModuleAware second) {

        Module one = first == null ? null : first.getModule();
        Module two = second == null ? null : second.getModule();

        if (one == null) {
            return two == null ? 0 : 1;
        }

        return one.compareTo(two);
    }
}
