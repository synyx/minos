/**
 * 
 */
package org.synyx.minos.i18n.domain;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.StringUtils;
import org.synyx.messagesource.util.LocaleUtils;


/**
 * Wrapper aroung Locale to store it in Database
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


    public LocaleWrapper(Locale locale) {

        if (locale == null) {
            this.language = "";
            this.country = "";
            this.variant = "";
        }

        this.language = LocaleUtils.getLanguage(locale);
        this.country = LocaleUtils.getCountry(locale);
        this.variant = LocaleUtils.getVariant(locale);
    }


    public LocaleWrapper(String language, String country, String variant) {

        this.language = language == null ? "" : language;
        this.country = country == null ? "" : country;
        this.variant = variant == null ? "" : variant;
    }


    /**
     * 
     */
    public LocaleWrapper() {

        this("", "", "");
    }


    public String getLanguage() {

        return language;
    }


    public void setLanguage(String language) {

        this.language = language == null ? "" : language;
        this.language = language;
    }


    public String getCountry() {

        return country;
    }


    public void setCountry(String country) {

        this.country = country == null ? "" : country;
        this.country = country;
    }


    public String getVariant() {

        return variant;
    }


    public void setVariant(String variant) {

        this.variant = variant == null ? "" : variant;
        this.variant = variant;
    }


    public Locale getLocale() {

        return new Locale(language, country, variant);
    }


    public String toString() {

        if (isDefault()) {
            return "default";
        }
        return getLocale().toString();
    }


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
