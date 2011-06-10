package org.synyx.minos.core.notification;

/**
 * Bean style implementation of a {@link NotificationContext}. Stores a configuration key.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ConfigBasedNotificationContext implements NotificationContext {

    private String configKey;

    /**
     * Creates a new {@code NotificationContext} based on the given {@code PluginMetadata}.
     *
     * @param metadata
     */
    public ConfigBasedNotificationContext(String configKey) {

        this.configKey = configKey;
    }

    public String getConfigurationKey() {

        return configKey;
    }
}
