package org.synyx.minos.core.notification;

import org.synyx.minos.core.domain.User;

import java.util.Set;


/**
 * Implementation of {@code Notification} that uses a simple {@code String} message to be send. It is able to resolve a
 * {@code Formatter} based string against additionally given message parameters.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class SimpleMessageNotification extends AbstractNotification implements Notification {

    private String message;
    private Object[] messageParameters;

    /**
     * Creates a new {@code SimpleMessageNotification} with a parameterized text message to be send to the given
     * {@code User}.
     *
     * @param  recipient  a {@link User} to receive the notification
     * @param  message  the message as a format string
     * @param  parameters  optional parameters for the message
     */
    public SimpleMessageNotification(User recipient, String message, Object... parameters) {

        this(null, recipient, message, parameters);
    }


    /**
     * Creates a new {@code SimpleMessageNotification} with a parameterized text message from the given sender to be
     * send to the given receiver.
     *
     * @param  sender  the {@code User} to be used as sender
     * @param  recipient  a {@code User} to receive the notification
     * @param  message  the message as a format string
     * @param  parameters  optional parameters for the message
     */
    public SimpleMessageNotification(User sender, User recipient, String message, Object... parameters) {

        super(sender, recipient);
        this.message = message;
        this.messageParameters = parameters;
    }


    /**
     * Creates a new {@code SimpleMessageNotification} with a parameterized text message to be send to the given
     * {@code User}s.
     *
     * @param  recipients  a set of {@code User}s to receive the notification
     * @param  message  the message as a format string
     * @param  parameters  optional parameters for the message
     */
    public SimpleMessageNotification(Set<User> recipients, String message, Object... parameters) {

        this(null, recipients, message, parameters);
    }


    /**
     * Creates a new {@code SimpleMessageNotification} from the given sender to the given recipients with a
     * parameterized text message.
     *
     * @param  sender  the {@code User} to be used as sender
     * @param  recipients  a set of {@code User}s to receive the notification
     * @param  message  the message as a format string
     * @param  parameters  optional parameters for the message
     */
    public SimpleMessageNotification(User sender, Set<User> recipients, String message, Object... parameters) {

        super(sender, recipients);
        this.message = message;
        this.messageParameters = parameters;
    }

    @Override
    public String getMessage() {

        if (null != messageParameters && messageParameters.length > 0) {
            return String.format(message, messageParameters);
        }

        return message;
    }
}
