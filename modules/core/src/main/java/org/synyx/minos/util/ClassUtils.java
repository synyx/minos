package org.synyx.minos.util;

import org.springframework.util.StringUtils;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ClassUtils {

    private ClassUtils() {
    }

    /**
     * Returns whether the given {@link Class} has a setter with the given argument types for the given property.
     *
     * @param clazz
     * @param propertyName
     * @param parameterTypes
     * @return
     */
    public static boolean hasSetter(Class<?> clazz, String propertyName, Class<?>... parameterTypes) {

        String setterName = String.format("set%s", StringUtils.capitalize(propertyName));

        return org.springframework.util.ClassUtils.hasMethod(clazz, setterName, parameterTypes);
    }
}
