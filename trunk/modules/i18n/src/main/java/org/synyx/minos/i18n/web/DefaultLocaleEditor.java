package org.synyx.minos.i18n.web;

import java.beans.PropertyEditor;
import java.util.Locale;

import org.springframework.beans.propertyeditors.LocaleEditor;


/**
 * {@link PropertyEditor} implementation extending {@link LocaleEditor} to interpret a text value of "default" as a
 * empty {@link Locale} - NOT setting the language field of the resulting {@link Locale} to "default".
 * 
 * @author Alexander Menz - Synyx GmbH & Co. KG
 */
public class DefaultLocaleEditor extends LocaleEditor {

    public static final String DEFAULT_LANGUAGE = "default";


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.propertyeditors.LocaleEditor#getAsText()
     */
    @Override
    public String getAsText() {

        String text = super.getAsText();

        return text != null && DEFAULT_LANGUAGE.equalsIgnoreCase(text) ? "" : text;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.propertyeditors.LocaleEditor#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) {

        if (text != null && DEFAULT_LANGUAGE.equalsIgnoreCase(text)) {
            super.setAsText("");
        } else {
            super.setAsText(text);
        }
    }

}
