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


    public Message() {

    }


    public Message(LocaleWrapper locale, String basename, String key, String message) {

        super();
        this.locale = locale;
        this.basename = basename;
        this.key = key;
        this.message = message;
    }


    public Message(Locale locale, String basename, String key, String message) {

        this(new LocaleWrapper(locale), basename, key, message);
    }


    public Message(String language, String country, String variant, String basename, String key, String message) {

        this(new LocaleWrapper(language, country, variant), basename, key, message);
    }


    public String getBasename() {

        return basename;
    }


    public void setBasename(String basename) {

        this.basename = basename;
    }


    public String getKey() {

        return key;
    }


    public void setKey(String key) {

        this.key = key;
    }


    public String getMessage() {

        return message;
    }


    public void setMessage(String message) {

        this.message = message;
    }


    public LocaleWrapper getLocale() {

        return locale;
    }


    public void setLocale(LocaleWrapper locale) {

        this.locale = locale;
    }


    @Override
    public String toString() {

        return String.format("[%s %s], %s=%s", basename, locale.toString(), key, message);
    }

}
