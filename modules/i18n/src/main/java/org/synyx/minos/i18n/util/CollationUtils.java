/**
 * 
 */
package org.synyx.minos.i18n.util;

import java.util.Collection;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class CollationUtils {

    private CollationUtils() {

    }


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
