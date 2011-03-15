package org.synyx.minos.core.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

import java.text.NumberFormat;


/**
 * Custom {@link java.beans.PropertyEditor} that uses {@code null} as default value if invalid values (nun numbers or
 * numbers in a inappropriate format) are bound to a parameter.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class IllegalToNullPropertyEditor extends CustomNumberEditor {

    private static final Log LOG = LogFactory.getLog(IllegalToNullPropertyEditor.class);

    /**
     * Creates a new {@link IllegalToNullPropertyEditor}.
     *
     * @param clazz
     * @param allowEmpty
     */
    public IllegalToNullPropertyEditor(Class<?> clazz, boolean allowEmpty) {

        super(clazz, allowEmpty);
    }


    /**
     * Creates a new {@link IllegalToNullPropertyEditor}.
     *
     * @param clazz
     * @param numberFormat
     * @param allowEmpty
     */
    public IllegalToNullPropertyEditor(Class<?> clazz, NumberFormat numberFormat, boolean allowEmpty) {

        super(clazz, numberFormat, allowEmpty);
    }

    @Override
    public void setAsText(String text) {

        try {
            super.setAsText(text);
        } catch (NumberFormatException e) {
            LOG.debug(String.format("Invalid parameter! Using null! %s", e.getMessage()));

            setValue(null);
        }
    }
}
