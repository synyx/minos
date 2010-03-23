package org.synyx.minos.core.notification;

import static org.easymock.EasyMock.*;
import static org.synyx.minos.TestConstants.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.task.TaskExecutor;
import org.synyx.hera.core.PluginRegistry;
import org.synyx.hera.core.SimplePluginRegistry;
import org.synyx.hera.metadata.PluginMetadata;
import org.synyx.minos.core.configuration.ConfigurationService;
import org.synyx.minos.core.domain.User;


/**
 * Unit test for {@link ProviderBasedNotificationService}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ProviderBasedNotificationServiceUnitTest {

    private ProviderBasedNotificationService service;

    private PluginRegistry<NotificationProvider, PluginMetadata> registry;
    private NotificationProvider defaultNotificationProvider;
    private NotificationProvider notificationProvider;
    private ConfigurationService configService;

    private Notification notification;


    /**
     * Sets up a {@link PluginRegistry} for {@link NotificationProvider}
     * instances. The {@link ProviderBasedNotificationService} to test is
     * equipped with this registry. Furthermore we set up a {@link TaskExecutor}
     * and a {@link NotificationProvider} mock.
     */
    @Before
    public void setUp() {

        // Basic service setup
        defaultNotificationProvider =
                createNiceMock(NotificationProvider.class);
        service =
                new ProviderBasedNotificationService(
                        defaultNotificationProvider);

        // Setup registry with providers
        notificationProvider = createNiceMock(NotificationProvider.class);
        expect(notificationProvider.supports((PluginMetadata) anyObject()))
                .andReturn(true).anyTimes();
        registry = SimplePluginRegistry.create();
        registry.addPlugin(notificationProvider);

        configService = createNiceMock(ConfigurationService.class);

        Set<User> users = new HashSet<User>();
        users.add(USER);

        notification = createNiceMock(Notification.class);
        expect(notification.getRecipients()).andReturn(users);
    }


    /**
     * Returns all mock objects.
     * 
     * @return
     */
    private Object[] getMocks() {

        return new Object[] { defaultNotificationProvider,
                notificationProvider, notification, configService };
    }


    /**
     * Asserts that the service uses the default {@link NotificationProvider} if
     * the one the context requires is not available.
     * 
     * @throws Exception
     */
    @Test
    public void usesDefaultProviderIfContextOneNotFound() throws Exception {

        // Create notification context that requires a non existing provider
        NotificationContext context = new ConfigBasedNotificationContext("");

        // Expect invocation on the default provider
        defaultNotificationProvider.notify(notification, USER);
        expectLastCall().once();

        // Invoke
        replay(getMocks());
        service.notify(notification, context);
        verify(getMocks());
    }


    @Test
    public void usesProviderFromRegistryIfConfigured() throws Exception {

        service.setNotificationProviders(registry);
        service.setConfigurationService(configService);

        notificationProvider.notify(notification, USER);
        expectLastCall().once();

        // Invoke
        replay(getMocks());
        service.notify(notification, new ConfigBasedNotificationContext(""));
        verify(getMocks());
    }
}
