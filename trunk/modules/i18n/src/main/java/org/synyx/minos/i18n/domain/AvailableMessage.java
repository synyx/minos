package org.synyx.minos.i18n.domain;

import org.synyx.hades.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * Domain class that defines a available message key and basename combination. These entities define the general set of
 * available combinations of message key and basename. Additionally the entity bears a message text that may be defined
 * by the developer (creator of the message key) to assist comprehension and translation of the intended message.
 *
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

    /**
     * Create a new empty instance of {@link AvailableMessage}. This constructor is needed by the OR mapper.
     */
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
     * Get the basename of this available message.
     *
     * @return the basename of this available message.
     */
    public String getBasename() {

        return basename;
    }


    /**
     * Set the basename of this available message.
     *
     * @param basename the basename to set.
     */
    public void setBasename(String basename) {

        this.basename = basename;
    }


    /**
     * Get the key of this available message.
     *
     * @return the key of this available message.
     */
    public String getKey() {

        return key;
    }


    /**
     * Set the key of this available message.
     *
     * @param key the key to set.
     */
    public void setKey(String key) {

        this.key = key;
    }


    /**
     * Get the message text of this available message.
     *
     * @return the message text of this available message.
     */
    public String getMessage() {

        return message;
    }


    /**
     * Set the message text of this available message.
     *
     * @param message the message to set.
     */
    public void setMessage(String message) {

        this.message = message;
    }
}
