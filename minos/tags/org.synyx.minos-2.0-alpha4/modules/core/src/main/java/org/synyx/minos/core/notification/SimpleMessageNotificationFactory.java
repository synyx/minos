package org.synyx.minos.core.notification;

import org.synyx.minos.core.domain.User;

/**
 * Creates a {@link SimpleMessageNotification} instance using a {@link String}
 *
 * @author David Linsin - linsin@synyx.de
 */
public class SimpleMessageNotificationFactory implements NotificationFactory {
    private final String message;

    /**
     * Initializes invariables
     *
     * @param argMessage {@link String} used to create a {@link SimpleMessageNotification}
     */
    public SimpleMessageNotificationFactory(String argMessage) {
        message = argMessage;
    }

    /**
     * Creates a {@link Notification} instance of type {@link SimpleMessageNotification}
     *
     * @param argRecipient {@link User} instance which is the recipient
     * @param argParams    variable parameters
     * @return {@link Notification} instance
     */
    public Notification create(User argRecipient, Object... argParams) {
        return new SimpleMessageNotification(argRecipient, message, argParams);
    }
}
