package org.synyx.minos.core.notification;

import org.synyx.minos.core.domain.User;


/**
 * Interface which defines the contract of a {@code factory}, used to create a {@link Notification}
 * 
 * @author David Linsin - linsin@synyx.de
 */
public interface NotificationFactory {

    /**
     * Creates a {@link Notification} for a {@link User}
     * 
     * @param recipient {@link User} instance which is the recipient
     * @param params variable parameters
     * @return {@link Notification} instance
     */
    Notification create(User recipient, Object... params);
}
