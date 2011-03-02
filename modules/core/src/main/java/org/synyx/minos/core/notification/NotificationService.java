package org.synyx.minos.core.notification;

/**
 * Interface for Minos' notificaton component. This allows clients to trigger notifications without caring about the
 * actual implementation.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface NotificationService {

    /**
     * Sends a notification. The implementation will choose the appropriate sending strategy itself. If implementations
     * provide configuration means for the strategies to be used, you can expect some default strategy to be used when
     * calling this method.
     *
     * @param notification
     */
    void notify(Notification notification);


    /**
     * Sends a notification regarding the given {@link NotificationContext}. Clients might use this to hint for a given
     * sending strategy.
     *
     * @param notification
     * @param context
     */
    void notify(Notification notification, NotificationContext context);
}
