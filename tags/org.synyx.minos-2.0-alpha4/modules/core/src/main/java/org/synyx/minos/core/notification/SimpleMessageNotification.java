package org.synyx.minos.core.notification;

import java.util.Set;

import org.synyx.minos.core.domain.User;



/**
 * Implementation of {@code Notification} that uses a simple {@code String}
 * message to be send. It is able to resolve a {@code Formatter} based string
 * against additionally given message parameters.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SimpleMessageNotification extends AbstractNotification implements
        Notification {

    private String message;
    private Object[] messageParameters;


    /**
     * Creates a new {@code SimpleMessageNotification} with a parameterized text
     * message to be send to the given {@code User}.
     * 
     * @param recipient
     * @param message
     * @param parameters
     */
    public SimpleMessageNotification(User recipient, String message,
            Object... parameters) {

        this(null, recipient, message, parameters);
    }


    /**
     * Creates a new {@code SimpleMessageNotification} with a parameterized text
     * message from the given sender to be send to the given receiver.
     * 
     * @param sender the {@code User} to be used as sender
     * @param recipient
     * @param message
     * @param parameters
     */
    public SimpleMessageNotification(User sender, User recipient,
            String message, Object... parameters) {

        super(sender, recipient);
        this.message = message;
        this.messageParameters = parameters;
    }


    /**
     * Creates a new {@code SimpleMessageNotification} with a parameterized text
     * message to be send to the given {@code User}s.
     * 
     * @param recipients
     * @param message
     * @param parameters
     */
    public SimpleMessageNotification(Set<User> recipients, String message,
            Object... parameters) {

        this(null, recipients, message, parameters);
    }


    /**
     * Creates a new {@code SimpleMessageNotification} from the given sender to
     * the given recipients with a parameterized text message.
     * 
     * @param sender
     * @param recipients
     * @param message
     * @param parameters
     */
    public SimpleMessageNotification(User sender, Set<User> recipients,
            String message, Object... parameters) {

        super(sender, recipients);
        this.message = message;
        this.messageParameters = parameters;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.notification.Notification#getMessage()
     */
    public String getMessage() {

        if (null != messageParameters && messageParameters.length > 0) {

            return String.format(message, messageParameters);
        }

        return message;
    }
}
