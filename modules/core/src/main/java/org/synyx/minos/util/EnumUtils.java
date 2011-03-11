package org.synyx.minos.util;

import org.apache.commons.lang.StringUtils;


/**
 * Simple utility class to work with enumerations.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EnumUtils {

    private EnumUtils() {
    }

    /**
     * Returns the enum for the given value or the {@code defaultValue}. Transforms the value to UPPERCASE prior to
     * value parsing.
     *
     * @param <T>
     * @param value
     * @param defaultValue
     * @return
     */
    public static <T extends Enum<T>> T valueOf(String value, T defaultValue) {

        if (null == defaultValue) {
            throw new IllegalArgumentException("Default value must not be null!");
        }

        if (null == value) {
            return defaultValue;
        }

        try {
            return Enum.valueOf(defaultValue.getDeclaringClass(), StringUtils.upperCase(value));
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }
}
