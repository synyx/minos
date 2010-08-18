package org.synyx.minos.i18n.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.synyx.hades.domain.AbstractPersistable;


/**
 * @author Alexander Menz - menz@synyx.de
 */
@Entity
public class AvailableMessage extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -2857407678649571912L;

    @Column(nullable = false, length = 63)
    private String basename;

    @Column(name = "keyName", nullable = false, length = 1024)
    private String key;

    @Column(nullable = false, length = 8192)
    private String message;


    protected AvailableMessage() {

    }


    /**
     * Create a new instance of {@link AvailableMessage} using the given field values. Sets the status field to
     * {@link MessageStatus} {@code NEW}.
     * 
     * @param basename
     * @param key
     * @param message
     */
    public AvailableMessage(String basename, String key, String message) {

        this.basename = basename;
        this.key = key;
        this.message = message;
    }


    /**
     * @return the basename
     */
    public String getBasename() {

        return basename;
    }


    /**
     * @param basename the basename to set
     */
    public void setBasename(String basename) {

        this.basename = basename;
    }


    /**
     * @return the key
     */
    public String getKey() {

        return key;
    }


    /**
     * @param key the key to set
     */
    public void setKey(String key) {

        this.key = key;
    }


    /**
     * @return the message
     */
    public String getMessage() {

        return message;
    }


    /**
     * @param message the message to set
     */
    public void setMessage(String message) {

        this.message = message;
    }

}
