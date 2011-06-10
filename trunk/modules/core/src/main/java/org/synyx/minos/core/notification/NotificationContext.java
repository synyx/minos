package org.synyx.minos.core.notification;

/**
 * Interface for notification context information. This will be used by the {@code NotificationService} to select the
 * appropriate {@code NotificationProvider}s to actually execute notification.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public interface NotificationContext {

    /**
     * Returns the key, {@link NotificationService} implementations might use to tweak their notification behaviour.
     *
     * @return  the configuration key
     */
    String getConfigurationKey();
}
