package org.synyx.minos.core.notification;

import org.synyx.hera.core.PluginRegistry;
import org.synyx.hera.metadata.PluginMetadata;
import org.synyx.minos.core.domain.User;


/**
 * Implementation of {@link Notification} that selects {@link NotificationProvider}s according to the given
 * {@link NotificationContext}.
 * <p>
 * It will use that to resolve the appropriate {@link NotificationProvider} through the injected
 * {@link ConfigurationService}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ProviderBasedNotificationService implements NotificationService {

    private PluginRegistry<NotificationProvider, PluginMetadata> providers;
    private NotificationProvider defaultNotificationProvider;


    /**
     * Creates a new {@link ProviderBasedNotificationService} using the given {@link NotificationProvider} as its
     * default one.
     */
    public ProviderBasedNotificationService(NotificationProvider defaultProvider) {

        this.defaultNotificationProvider = defaultProvider;
    }


    /**
     * Setter to inject {@link NotificationProvider}s.
     * 
     * @param registry
     */
    public void setNotificationProviders(PluginRegistry<NotificationProvider, PluginMetadata> registry) {

        this.providers = registry;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.notification.NotificationService#notify(com.synyx
     * .minos.core.notification.Notification, com.synyx.minos.core.notification.NotificationContext)
     */
    public void notify(final Notification notification, final NotificationContext context) {

        for (User recipient : notification.getRecipients()) {

            getNotificationProvider(context, recipient).notify(notification, recipient);
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.notification.NotificationService#notify(com.synyx
     * .minos.core.notification.Notification)
     */
    public void notify(Notification notification) {

        notify(notification, null);
    }


    /**
     * Returns the {@link NotificationProvider} to be used for the given receipient and context. This will regard the
     * receipients prefered notification provider if {@link ConfigurationService} and a valid key are given falling back
     * on the {@link #defaultNotificationProvider} otherwise.
     * 
     * @param context
     * @param recipient
     * @return
     */
    private NotificationProvider getNotificationProvider(NotificationContext context, User recipient) {

        return defaultNotificationProvider;
        //        
        // String configKey = context.getConfigurationKey();
        //
        // // No configuration service configured? Use default provider
        // if (null == configKey || null == configurationService) {
        // return defaultNotificationProvider;
        // }
        //
        // // Lookup receipients preferred notification plugin
        // PluginMetadata userPluginMetadata =
        // configurationService.getConfigValue(configKey, recipient);
        //
        // // Select provider based on context or use default provider if
        // // the selected provider is not available
        // return providers.getPluginFor(userPluginMetadata,
        // defaultNotificationProvider);
    }
}
