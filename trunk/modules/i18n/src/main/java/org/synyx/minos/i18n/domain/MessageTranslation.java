package org.synyx.minos.i18n.domain;

import org.synyx.hades.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;


/**
 * Domain class linking an {@link AvailableMessage} to an {@link AvailableLanguage} indicating that the
 * {@link AvailableMessage} is either new ({@link MessageStatus#NEW} or updated ({@link MessageStatus#UPDATED} and
 * therefore the translation for {@link AvailableLanguage} needs to be checked.
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Entity
public class MessageTranslation extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -3381096806734953868L;

    @ManyToOne(optional = false)
    private AvailableMessage availableMessage;

    @ManyToOne(optional = false)
    private AvailableLanguage availableLanguage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    /**
     * Create a new empty instance of {@link MessageTranslation}. This constructor is needed by the OR mapper.
     */
    protected MessageTranslation() {
    }


    /**
     * Create an new instance of {@link MessageTranslation} for the given {@link AvailableMessage} and
     * {@link AvailableLanguage} setting the given {@link MessageStatus}.
     *
     * @param availableMessage the {@link AvailableMessage}.
     * @param availableLanguage the {@link AvailableLanguage}.
     * @param messageStatus the {@link MessageStatus}.
     */
    public MessageTranslation(AvailableMessage availableMessage, AvailableLanguage availableLanguage,
        MessageStatus messageStatus) {

        super();
        this.availableMessage = availableMessage;
        this.availableLanguage = availableLanguage;
        this.messageStatus = messageStatus;
    }

    /**
     * Get the {@link AvailableMessage} of this instance of {@link MessageTranslation}.
     *
     * @return the {@link AvailableMessage}.
     */
    public AvailableMessage getAvailableMessage() {

        return availableMessage;
    }


    /**
     * Set the {@link AvailableMessage} of this instance of {@link MessageTranslation}.
     *
     * @param availableMessage the {@link AvailableMessage} to set.
     */
    public void setAvailableMessage(AvailableMessage availableMessage) {

        this.availableMessage = availableMessage;
    }


    /**
     * Get the {@link AvailableLanguage} of this instance of {@link MessageTranslation}.
     *
     * @return the {@link AvailableLanguage}.
     */
    public AvailableLanguage getAvailableLanguage() {

        return availableLanguage;
    }


    /**
     * Set the {@link AvailableLanguage} of this instance of {@link MessageTranslation}.
     *
     * @param availableLanguage the {@link AvailableLanguage} to set.
     */
    public void setAvailableLanguage(AvailableLanguage availableLanguage) {

        this.availableLanguage = availableLanguage;
    }


    /**
     * Get the {@link MessageStatus} of this instance of {@link MessageTranslation}.
     *
     * @return the {@link MessageStatus}.
     */
    public MessageStatus getMessageStatus() {

        return messageStatus;
    }


    /**
     * Set the {@link MessageStatus} of this instance of {@link MessageTranslation}.
     *
     * @param messageStatus the {@link MessageStatus} to set.
     */
    public void setMessageStatus(MessageStatus messageStatus) {

        this.messageStatus = messageStatus;
    }
}
