package org.synyx.minos.core.notification;

import org.synyx.hera.core.PluginRegistry;
import org.synyx.hera.metadata.PluginMetadata;

import org.synyx.minos.core.domain.User;


/**
 * Implementation of {@link Notification} that selects {@link NotificationProvider}s according to the given
 * {@link NotificationContext}.
 *
 * <p>It will use that to resolve the appropriate {@link NotificationProvider} through the injected
 * {@link ConfigurationService}.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class ProviderBasedNotificationService implements NotificationService {

    // TODO: This is not used anymore. Is the former {@link #getNotifcationProviders(NotificationContext, User)
    // implementation likely to come back?
    private PluginRegistry<NotificationProvider, PluginMetadata> providers;
    private NotificationProvider defaultNotificationProvider;

    /**
     * Creates a new {@link ProviderBasedNotificationService} using the given {@link NotificationProvider} as its
     * default one.
     *
     * @param  defaultProvider  the notification provider
     */
    public ProviderBasedNotificationService(NotificationProvider defaultProvider) {

        this.defaultNotificationProvider = defaultProvider;
    }

    /**
     * Setter to inject {@link NotificationProvider}s.
     *
     * @param  registry  a registry of notification providers
     */
    public void setNotificationProviders(PluginRegistry<NotificationProvider, PluginMetadata> registry) {

        this.providers = registry;
    }


    @Override
    public void notify(final Notification notification, final NotificationContext context) {

        for (User recipient : notification.getRecipients()) {
            getNotificationProvider(context, recipient).notify(notification, recipient);
        }
    }


    @Override
    public void notify(Notification notification) {

        notify(notification, null);
    }


    /**
     * Returns the {@link NotificationProvider} to be used for the given receipient and context. This will regard the
     * receipients prefered notification provider if {@link ConfigurationService} and a valid key are given falling back
     * on the {@link #defaultNotificationProvider} otherwise.
     *
     * @param  context
     * @param  recipient
     *
     * @return
     */
    private NotificationProvider getNotificationProvider(NotificationContext context, User recipient) {

        return defaultNotificationProvider;
    }
}
