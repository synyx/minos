package org.synyx.minos.core.notification;

import org.synyx.minos.core.domain.User;

import java.util.HashSet;
import java.util.Set;


/**
 * Abstract base implementation of {@code Notification} that handles sender and recipient management. This allows
 * subclasses to implement various strategies to provide the notification message.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractNotification implements Notification {

    protected User sender;
    protected Set<User> recipients;

    /**
     * Creates an {@code AbstractNotification} with the given {@code User} as recipient.
     *
     * @param recipient
     */
    public AbstractNotification(User recipient) {

        this(null, recipient);
    }


    /**
     * Creates an {@code AbstractNotification} with a sender and a single recipient.
     *
     * @param sender
     * @param recipient
     */
    public AbstractNotification(User sender, User recipient) {

        this.sender = sender;

        this.recipients = new HashSet<User>();
        this.recipients.add(recipient);
    }


    /**
     * Creates an {@code AbstractNotification} with the given {@code User}s as recipients.
     *
     * @param recipients
     */
    public AbstractNotification(Set<User> recipients) {

        this(null, recipients);
    }


    /**
     * Creates an {@code AbstractNotification} with a single sender and a list of recipients.
     *
     * @param sender
     * @param recipients
     */
    public AbstractNotification(User sender, Set<User> recipients) {

        this.sender = sender;
        this.recipients = null == recipients ? new HashSet<User>() : recipients;
    }

    public User getSender() {

        return sender;
    }


    public Set<User> getRecipients() {

        return recipients;
    }


    public boolean hasSender() {

        return null != sender;
    }
}
