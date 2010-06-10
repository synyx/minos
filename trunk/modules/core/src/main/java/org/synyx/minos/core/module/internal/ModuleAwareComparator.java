package org.synyx.minos.core.module.internal;

import java.util.Comparator;

import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.ModuleAware;


/**
 * {@link Comparator} implementation to order implementations of
 * {@link ModuleAware} by comparing the underlying {@link Module} instances.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleAwareComparator implements Comparator<ModuleAware> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
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
