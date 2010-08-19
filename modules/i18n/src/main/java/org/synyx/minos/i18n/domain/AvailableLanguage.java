/**
 * 
 */
package org.synyx.minos.i18n.domain;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.synyx.hades.domain.AbstractPersistable;


/**
 * Domain class that defines that a language({@link LocaleWrapper}) is available/required for a basename.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Entity
public class AvailableLanguage extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -8825239414786003379L;

    @Embedded
    private LocaleWrapper locale;

    @Column(nullable = false, length = 63)
    private String basename;

    private boolean required = true;


    public AvailableLanguage() {

    }


    public AvailableLanguage(LocaleWrapper locale, String basename) {

        super();
        this.locale = locale;
        this.basename = basename;
    }


    public AvailableLanguage(LocaleWrapper locale, String basename, boolean required) {

        super();
        this.locale = locale;
        this.basename = basename;
        this.required = required;
    }


    public LocaleWrapper getLocale() {

        return locale;
    }


    public void setLocale(LocaleWrapper locale) {

        this.locale = locale;
    }


    public Locale getRealLocale() {

        return locale.getLocale();
    }


    public void setRealLocale(Locale locale) {

        this.locale = new LocaleWrapper(locale);
    }


    public String getBasename() {

        return basename;
    }


    public void setBasename(String basename) {

        this.basename = basename;
    }


    public boolean isRequired() {

        return required;
    }


    public void setRequired(boolean required) {

        this.required = required;
    }

}
