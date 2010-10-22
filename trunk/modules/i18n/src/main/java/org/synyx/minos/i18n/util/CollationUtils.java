/**
 * 
 */
package org.synyx.minos.i18n.util;

import java.util.Collection;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


/**
 * Util class for Collation
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class CollationUtils {

    private CollationUtils() {

    }


    /**
     * Returns the first element within the given {@link Collection} which has the given field set to the given value.
     * 
     * @param <E> elements of the {@link Collection}
     * @param col the {@link Collection}
     * @param fieldName the name of the property (e.g. "key" for getKey())
     * @param value the value to compare (using equals) against the returned value of the object
     * @return the first object thats field equals the given value or null if none found
     */
    public static <E> E getRealMatch(Collection<E> col, String fieldName, String value) {

        for (E e : col) {
            BeanWrapper w = new BeanWrapperImpl(e);
            if (value.equals(w.getPropertyValue(fieldName))) {
                return e;
            }
        }

        return null;
    }
}
