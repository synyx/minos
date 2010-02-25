package org.synyx.minos.im.notification;

import org.synyx.hera.metadata.AbstractMetadataBasedPlugin;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.notification.Notification;
import org.synyx.minos.core.notification.NotificationProvider;
import org.synyx.minos.im.domain.InstantMessage;
import org.synyx.minos.im.service.InstantMessagingService;


/**
 * {@link NotificationProvider} using {@link InstantMessage}es.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class InstantMessageNotificationProvider extends
        AbstractMetadataBasedPlugin implements NotificationProvider {

    private InstantMessagingService instantMessagingService;


    /**
     * Creates a new {@link InstantMessageNotificationProvider}.
     */
    public InstantMessageNotificationProvider() {

        super("com.minos.notification.provider.im", "1.0");
    }


    /**
     * Setter to inject the {@link InstantMessagingService} to actually send the
     * message.
     * 
     * @param instantMessagingService the instantMessagingService to set
     */
    public void setInstantMessagingService(
            InstantMessagingService instantMessagingService) {

        this.instantMessagingService = instantMessagingService;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.notification.NotificationProvider#notify(com.synyx
     * .minos.core.notification.Notification, com.synyx.minos.core.domain.User)
     */
    public void notify(Notification notification, User recipient) {

        InstantMessage message = new InstantMessage();

        message.setReceipient(recipient);
        message.setText(notification.getMessage());

        instantMessagingService.send(message);
    }
}
