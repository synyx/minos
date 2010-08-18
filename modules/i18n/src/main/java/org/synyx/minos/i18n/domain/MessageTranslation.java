/**
 * 
 */
package org.synyx.minos.i18n.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.synyx.hades.domain.AbstractPersistable;


/**
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


    public MessageTranslation() {

    }


    public MessageTranslation(AvailableMessage availableMessage, AvailableLanguage availableLanguage,
            MessageStatus messageStatus) {

        super();
        this.availableMessage = availableMessage;
        this.availableLanguage = availableLanguage;
        this.messageStatus = messageStatus;
    }


    public AvailableMessage getAvailableMessage() {

        return availableMessage;
    }


    public void setAvailableMessage(AvailableMessage availableMessage) {

        this.availableMessage = availableMessage;
    }


    public AvailableLanguage getAvailableLanguage() {

        return availableLanguage;
    }


    public void setAvailableLanguage(AvailableLanguage availableLanguage) {

        this.availableLanguage = availableLanguage;
    }


    public MessageStatus getMessageStatus() {

        return messageStatus;
    }


    public void setMessageStatus(MessageStatus messageStatus) {

        this.messageStatus = messageStatus;
    }

}
