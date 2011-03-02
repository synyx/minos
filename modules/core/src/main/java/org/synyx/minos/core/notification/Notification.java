package org.synyx.minos.core.notification;

import org.synyx.minos.core.domain.User;

import java.util.Set;


/**
 * Interface for the abstraction of a notification.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface Notification {

    /**
     * Returns the sender of the notification. If {@link #hasSender()} returns {@code true} this method does not return
     * {@code null}.
     *
     * @return the sender of the notification or {@code null} if none available
     */
    User getSender();


    /**
     * Returns if the notification has a sender.
     *
     * @return true, if there is a sender available, false otherwise
     */
    boolean hasSender();


    /**
     * Returns the recipients of the notification.
     *
     * @return the list of recipients, never {@code null}
     */
    Set<User> getRecipients();


    /**
     * Returns the message to be sent. Implementations can build it from various sources or strategies.
     *
     * @return the notification message
     */
    String getMessage();
}
