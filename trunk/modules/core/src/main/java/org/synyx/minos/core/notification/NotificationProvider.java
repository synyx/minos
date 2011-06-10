package org.synyx.minos.core.notification;

import org.synyx.hera.core.Plugin;
import org.synyx.hera.metadata.MetadataProvider;
import org.synyx.hera.metadata.PluginMetadata;

import org.synyx.minos.core.domain.User;


/**
 * Central {@code Plugin} based abstraction of various actual notification implementations. The
 * {@code NotificationService} can be equipped with implementations of this interfaces.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public interface NotificationProvider extends Plugin<PluginMetadata>, MetadataProvider {

    /**
     * Actually executes notification. Allows implementing classes to leverage various notification strategies or
     * technologies to be used.
     *
     * @param  notification  a notification
     * @param  recipient  a user to receive the notification
     */
    void notify(Notification notification, User recipient);
}
