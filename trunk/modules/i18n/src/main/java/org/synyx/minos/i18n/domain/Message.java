package org.synyx.minos.i18n.domain;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.synyx.hades.domain.AbstractPersistable;


/**
 * Domain class representing a single internationalized message entry. This message entry consists of a locale,
 * basename, key and message. Uniquely identified by the first three. (Although there is no compound key but a single
 * id.)
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Entity
public class Message extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 833847763790147140L;

    @Embedded
    private LocaleWrapper locale = new LocaleWrapper();

    @Column(nullable = false, length = 63)
    private String basename;

    @Column(name = "keyName", nullable = false, length = 1024)
    private String key;

    @Column(nullable = false, length = 8192)
    private String message;


    /**
     * Create a new empty instance of {@link Message}.
     */
    public Message() {

    }


    /**
     * Create a new instance of {@link Message} setting the fields according to the given parameters.
     * 
     * @param locale the locale wrapped by {@link LocaleWrapper}.
     * @param basename the basename.
     * @param key the key.
     * @param message the message text.
     */
    public Message(LocaleWrapper locale, String basename, String key, String message) {

        super();
        this.locale = locale;
        this.basename = basename;
        this.key = key;
        this.message = message;
    }


    /**
     * Create a new instance of {@link Message} setting the fields according to the given parameters.
     * 
     * @param locale the {@link Locale}.
     * @param basename the basename.
     * @param key the key.
     * @param message the message text.
     */
    public Message(Locale locale, String basename, String key, String message) {

        this(new LocaleWrapper(locale), basename, key, message);
    }


    /**
     * Create a new instance of {@link Message} setting the fields according to the given parameters.
     * 
     * @param language the language code of the locale.
     * @param country the country code of the locale.
     * @param variant the variant of the locale.
     * @param basename the basename.
     * @param key the key.
     * @param message the message text.
     */
    public Message(String language, String country, String variant, String basename, String key, String message) {

        this(new LocaleWrapper(language, country, variant), basename, key, message);
    }


    /**
     * Get the basename of this {@link Message}.
     * 
     * @return the basename.
     */
    public String getBasename() {

        return basename;
    }


    /**
     * Set the basename of this {@link Message}.
     * 
     * @param basename the basename to set.
     */
    public void setBasename(String basename) {

        this.basename = basename;
    }


    /**
     * Get the key of this {@link Message}.
     * 
     * @return the key.
     */
    public String getKey() {

        return key;
    }


    /**
     * Set the key of this {@link Message}.
     * 
     * @param key the key to set.
     */
    public void setKey(String key) {

        this.key = key;
    }


    /**
     * Get the message text of this {@link Message}.
     * 
     * @return the message text.
     */
    public String getMessage() {

        return message;
    }


    /**
     * Set the message text of this {@link Message}.
     * 
     * @param message the message text to set.
     */
    public void setMessage(String message) {

        this.message = message;
    }


    /**
     * Get the locale of this {@link Message} wrapped by {@link LocaleWrapper}.
     * 
     * @return the locale.
     */
    public LocaleWrapper getLocale() {

        return locale;
    }


    /**
     * Set the locale of this {@link Message} wrapped by {@link LocaleWrapper}.
     * 
     * @param locale the locale to set.
     */
    public void setLocale(LocaleWrapper locale) {

        this.locale = locale;
    }


    /**
     * Return a readable string representation of this instance of {@link Message}.
     */
    @Override
    public String toString() {

        return String.format("[%s %s], %s=%s", basename, locale.toString(), key, message);
    }

}
