package org.synyx.minos.i18n.domain;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.StringUtils;
import org.synyx.messagesource.util.LocaleUtils;


/**
 * Wrapper around {@link Locale} to store it in the database.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Embeddable
public class LocaleWrapper {

    @Column(nullable = false, length = 7)
    private String language;

    @Column(nullable = false, length = 7)
    private String country;
    @Column(nullable = false, length = 15)
    private String variant;

    public static final LocaleWrapper DEFAULT = new LocaleWrapper();


    /**
     * Create a new instance of {@link LocaleWrapper} wrapping the given {@link Locale}.
     * 
     * @param locale the {@link Locale} to wrap.
     */
    public LocaleWrapper(Locale locale) {

        this.language = LocaleUtils.getLanguage(locale);
        this.country = LocaleUtils.getCountry(locale);
        this.variant = LocaleUtils.getVariant(locale);
    }


    /**
     * Create a new instance of {@link LocaleWrapper} wrapping a {@link Locale} constructed from the given language,
     * country and variant code. Each of the codes can be {@code null}, resulting in setting the code of the wrapped
     * instance of {@link Locale} to "".
     * 
     * @param language the language code.
     * @param country the country code.
     * @param variant the variant code.
     */
    public LocaleWrapper(String language, String country, String variant) {

        this.language = language == null ? "" : language;
        this.country = country == null ? "" : country;
        this.variant = variant == null ? "" : variant;
    }


    /**
     * Create a new instance of {@link LocaleWrapper} wrapping an empty {@link Locale} (language, country and variant
     * code set to "").
     */
    public LocaleWrapper() {

        this("", "", "");
    }


    /**
     * Get the language code of the {@link Locale} wrapped by this {@link LocaleWrapper}.
     * 
     * @return the language code.
     */
    public String getLanguage() {

        return language;
    }


    /**
     * Set the language code of the {@link Locale} wrapped by this {@link LocaleWrapper}.
     * 
     * @param language the language code to set. Can be {@code null}, resulting in setting the language code of the
     *            wrapped instance of {@link Locale} to "".
     */
    public void setLanguage(String language) {

        this.language = language == null ? "" : language;
    }


    /**
     * Get the country code of the {@link Locale} wrapped by this {@link LocaleWrapper}.
     * 
     * @return the country code.
     */
    public String getCountry() {

        return country;
    }


    /**
     * Set the country code of the {@link Locale} wrapped by this {@link LocaleWrapper}.
     * 
     * @param language the country code to set. Can be {@code null}, resulting in setting the country code of the
     *            wrapped instance of {@link Locale} to "".
     */
    public void setCountry(String country) {

        this.country = country == null ? "" : country;
    }


    /**
     * Get the variant code of the {@link Locale} wrapped by this {@link LocaleWrapper}.
     * 
     * @return the variant code.
     */
    public String getVariant() {

        return variant;
    }


    /**
     * Set the variant code of the {@link Locale} wrapped by this {@link LocaleWrapper}.
     * 
     * @param language the variant code to set. Can be {@code null}, resulting in setting the variant code of the
     *            wrapped instance of {@link Locale} to "".
     */
    public void setVariant(String variant) {

        this.variant = variant == null ? "" : variant;
    }


    /**
     * Get the instance of {@link Locale} wrapped by the {@link LocaleWrapper}.
     * 
     * @return the instance of {@link Locale} wrapped by the {@link LocaleWrapper}.
     */
    public Locale getLocale() {

        return new Locale(language, country, variant);
    }


    /**
     * Return a readable string representation of this instance of {@link LocaleWrapper}.
     */
    public String toString() {

        if (isDefault()) {
            return "default";
        }
        return getLocale().toString();
    }


    /**
     * Returns {@code true} if the instance of {@link LocaleWrapper} wraps an empty {@link Locale}. All codes (language,
     * country variant) of the wrapped {@link Locale} are set to "".
     * 
     * @return {@code true} if the instance of {@link LocaleWrapper} wraps an empty {@link Locale}.
     */
    public boolean isDefault() {

        return (!StringUtils.hasLength(language) && !StringUtils.hasLength(country) && !StringUtils.hasLength(variant));
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        result = prime * result + ((variant == null) ? 0 : variant.hashCode());
        return result;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocaleWrapper other = (LocaleWrapper) obj;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (language == null) {
            if (other.language != null)
                return false;
        } else if (!language.equals(other.language))
            return false;
        if (variant == null) {
            if (other.variant != null)
                return false;
        } else if (!variant.equals(other.variant))
            return false;
        return true;
    }

}
