package org.synyx.minos.core.web;

import java.text.NumberFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;


/**
 * Custom {@link java.beans.PropertyEditor} that uses {@code null} as default value if invalid values (nun numbers or
 * numbers in a inappropriate format) are bound to a parameter.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class IllegalToNullPropertyEditor extends CustomNumberEditor {

    private static final Log log = LogFactory.getLog(IllegalToNullPropertyEditor.class);


    /**
     * Creates a new {@link IllegalToNullPropertyEditor}.
     * 
     * @param clazz
     * @param allowEmpty
     * @throws IllegalArgumentException
     */
    public IllegalToNullPropertyEditor(Class<?> clazz, boolean allowEmpty) throws IllegalArgumentException {

        super(clazz, allowEmpty);
    }


    /**
     * Creates a new {@link IllegalToNullPropertyEditor}.
     * 
     * @param clazz
     * @param numberFormat
     * @param allowEmpty
     * @throws IllegalArgumentException
     */
    public IllegalToNullPropertyEditor(Class<?> clazz, NumberFormat numberFormat, boolean allowEmpty)
            throws IllegalArgumentException {

        super(clazz, numberFormat, allowEmpty);
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            super.setAsText(text);

        } catch (NumberFormatException e) {

            log.debug(String.format("Invalid parameter! Using null! %s", e.getMessage()));

            setValue(null);
        }
    }
}
