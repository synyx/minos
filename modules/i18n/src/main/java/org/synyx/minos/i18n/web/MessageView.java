package org.synyx.minos.i18n.web;

import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.domain.MessageStatus;
import org.synyx.minos.i18n.domain.MessageTranslation;


/**
 * View bean encapsulating all informations of a (resolved) message.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Alexander Menz - menz@synyx.de
 */

public class MessageView {

    private LocaleWrapper resolvingLocale;

    private Message message = new Message();

    private MessageTranslation translation;


    /**
     * Creates a new instance of {@link MessageView}.
     * 
     * @param resolvingLocale the locale by which the message could be resolved.
     * @param message the resolved message.
     * @param translation the messages translation info, if any.
     */
    public MessageView(LocaleWrapper resolvingLocale, Message message, MessageTranslation translation) {

        this.resolvingLocale = resolvingLocale;
        this.message = message;
        this.translation = translation;
    }


    /**
     * Returns <code>true</code> if the resolved message is defined for the locale for wich the message was requested.
     * In that case, the attribute <code>resolvingLocale</code> equals the messages locale.
     * 
     * @return <code>true</code> if the message is defined for the locale it was requested for.
     */
    public boolean isMessageResolved() {

        return !resolvingLocale.equals(message.getLocale());
    }


    /**
     * Returns the locale by which the requested message was resolved.
     * <p>
     * <b>Note:</b> The locale of the message entity <i>always</i> equals the locale the message was requested for!
     * </p>
     * 
     * @see MessageView#isMessageResolved()
     * @return the locale by which the requested message was resolved.
     */
    public LocaleWrapper getResolvingLocale() {

        return resolvingLocale;
    }


    /**
     * Set the locale by which the requested message was resolved.
     * 
     * @param resolvingLocale
     */
    public void setResolvingLocale(LocaleWrapper resolvingLocale) {

        this.resolvingLocale = resolvingLocale;
    }


    /**
     * Get the resolved {@link Message} entity. If the message could not be resolved by the requested locale, the locale
     * of the returned message entity equals the requested locale anyhow. In that case the message entity represents a
     * new and detached entity prefilled by the resolved message text.
     * 
     * @return the resolved {@link Message} entity.
     */
    public Message getMessage() {

        return message;
    }


    /**
     * Set the resolved {@link Message} entity.
     * 
     * @param message
     */
    public void setMessage(Message message) {

        this.message = message;
    }


    @Override
    public String toString() {

        return message.toString();
    }


    /**
     * Set the {@link MessageTranslation} entity according to the resolved {@link Message} entity.
     * 
     * @param translation
     */
    public void setTranslation(MessageTranslation translation) {

        this.translation = translation;
    }


    /**
     * Get the {@link MessageTranslation} entity for the resolved {@link Message} entity. Can be <code>null</code>.
     * 
     * @return the {@link MessageTranslation} entity for the resolved {@link Message} entity.
     */
    public MessageTranslation getTranslation() {

        return translation;
    }


    /**
     * Returns <code>true</code> if there is a {@link MessageTranslation} entity for the resolved {@link Message} entity
     * which status is {@link MessageStatus#NEW}.
     * 
     * @return
     */
    public boolean isNewForTranslation() {

        return translation != null && MessageStatus.NEW.equals(translation.getMessageStatus());
    }


    /**
     * Returns <code>true</code> if there is a {@link MessageTranslation} entity for the resolved {@link Message} entity
     * which status is {@link MessageStatus#UPDATED}.
     * 
     * @return
     */
    public boolean isUpdatedForTranslation() {

        return translation != null && MessageStatus.UPDATED.equals(translation.getMessageStatus());
    }

}
