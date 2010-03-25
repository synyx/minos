package org.synyx.minos.core.notification;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.synyx.minos.core.domain.User;

/**
 * Creates a {@link SimpleMessageNotificationFactory} instance using a {@link MessageSource}
 *
 * @author David Linsin - linsin@synyx.de
 */
public class MessageSourceNotificationFactory implements NotificationFactory, MessageSourceAware {
    private String key;
    private MessageSource messageSource;

    /**
     * @param messageSource {@link MessageSource} used to retrieved a localized {@link String}
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * @param argKey {@link String} used as key to retrieve {@link String} from {@link MessageSource}
     */
    public void setKey(String argKey) {
        key = argKey;
    }

    /**
     * Creates a {@link Notification} instance of type {@link SimpleMessageNotification}
     *
     * @param argRecipient {@link User} instance which is the recipient
     * @param argParams    variable parameters
     * @return {@link Notification} instance
     * @throws NullPointerException in case {@link MessageSource} wasn't set 
     */
    public Notification create(User argRecipient, Object... argParams) {
        return new SimpleMessageNotification(argRecipient, messageSource.getMessage(key, argParams, LocaleContextHolder.getLocale()));
    }
}
