/**
 * 
 */
package org.synyx.minos.i18n.web;

import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */

public class MessageView {

    private LocaleWrapper currentLocale;

    private Message message = new Message();

    private MessageView reference;


    public MessageView(LocaleWrapper currentLocale, Message message) {

        this.currentLocale = currentLocale;
        this.message = message;
        this.reference = this;
    }


    public MessageView(LocaleWrapper currentLocale, Message message, MessageView reference) {

        this.currentLocale = currentLocale;
        this.message = message;
        this.reference = reference;
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

}
