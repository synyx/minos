/**
 *
 */
package org.synyx.minos.i18n.domain;

import org.synyx.hades.domain.AbstractPersistable;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;


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

    /**
     * Create a new empty instance of {@link AvailableLanguage}. This constructor is needed by the OR mapper.
     */
    protected AvailableLanguage() {
    }


    /**
     * Creates a new instance of {@link AvailableLanguage} for the given combination of language (wrapped via
     * {@link LocaleWrapper}) and basename. The language is flagged as not required.
     *
     * @param locale the language
     * @param basename the basename
     */
    public AvailableLanguage(LocaleWrapper locale, String basename) {

        super();
        this.locale = locale;
        this.basename = basename;
    }


    /**
     * Creates a new instance of {@link AvailableLanguage} for the given combination of language (wrapped via
     * {@link LocaleWrapper}) and basename.
     *
     * @param locale the language
     * @param basename the basename
     * @param required flag indicating that the language is required
     */
    public AvailableLanguage(LocaleWrapper locale, String basename, boolean required) {

        super();
        this.locale = locale;
        this.basename = basename;
        this.required = required;
    }

    /**
     * Get the locale (wrapped via {@link LocaleWrapper}) of this available language.
     *
     * @return the locale of this available language.
     */
    public LocaleWrapper getLocale() {

        return locale;
    }


    /**
     * Set the locale (wrapped via {@link LocaleWrapper}) of this available language.
     *
     * @param locale the locale to set.
     */
    public void setLocale(LocaleWrapper locale) {

        this.locale = locale;
    }


    /**
     * Get the {@link Locale} of this available language.
     *
     * @return the {@link Locale} of this available language.
     */
    public Locale getRealLocale() {

        return locale.getLocale();
    }


    /**
     * Set the {@link Locale} of this available language.
     *
     * @param locale the {@link Locale} to set.
     */
    public void setRealLocale(Locale locale) {

        this.locale = new LocaleWrapper(locale);
    }


    /**
     * Get the basename of this available language.
     *
     * @return the basename of this available language.
     */
    public String getBasename() {

        return basename;
    }


    /**
     * Set the basename of this available language.
     *
     * @param basename the basename to set.
     */
    public void setBasename(String basename) {

        this.basename = basename;
    }


    /**
     * Get the required flag of this available language.
     *
     * @return the required flag of this available language.
     */
    public boolean isRequired() {

        return required;
    }


    /**
     * Set the required flag of this available language.
     *
     * @param required the required flag to set.
     */
    public void setRequired(boolean required) {

        this.required = required;
    }


    @Override
    public String toString() {

        return String.format("%s %s", basename, locale.toString());
    }
}
