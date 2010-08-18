/**
 * 
 */
package org.synyx.minos.i18n.web;

import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.domain.MessageStatus;
import org.synyx.minos.i18n.domain.MessageTranslation;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */

public class MessageView {

    private LocaleWrapper currentLocale;

    private Message message = new Message();

    private MessageView reference;

    private MessageTranslation translation;


    public MessageView(LocaleWrapper currentLocale, Message message, MessageTranslation translation) {

        this.currentLocale = currentLocale;
        this.message = message;
        this.translation = translation;
    }


    public MessageView(LocaleWrapper currentLocale, Message message, MessageView reference,
            MessageTranslation translation) {

        this.currentLocale = currentLocale;
        this.message = message;
        this.reference = reference;
        this.translation = translation;
    }


    public boolean isDefinedInCurrent() {

        return currentLocale.equals(message.getLocale());
    }


    public LocaleWrapper getCurrentLocale() {

        return currentLocale;
    }


    public void setCurrentLocale(LocaleWrapper currentLocale) {

        this.currentLocale = currentLocale;
    }


    public Message getMessage() {

        return message;
    }


    public void setMessage(Message message) {

        this.message = message;
    }


    @Override
    public String toString() {

        return message.toString();
    }


    public void setReference(MessageView reference) {

        this.reference = reference;
    }


    public MessageView getReference() {

        return reference;
    }


    public void setTranslation(MessageTranslation translation) {

        this.translation = translation;
    }


    public MessageTranslation getTranslation() {

        return translation;
    }


    public boolean isNewForTranslation() {

        return translation != null && MessageStatus.NEW.equals(translation.getMessageStatus());
    }


    public boolean isUpdatedForTranslation() {

        return translation != null && MessageStatus.UPDATED.equals(translation.getMessageStatus());
    }

}
