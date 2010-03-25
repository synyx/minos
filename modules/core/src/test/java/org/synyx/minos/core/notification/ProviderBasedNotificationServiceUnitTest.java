package org.synyx.minos.core.notification;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.TestConstants.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.task.TaskExecutor;
import org.synyx.hera.core.MutablePluginRegistry;
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
@RunWith(MockitoJUnitRunner.class)
public class ProviderBasedNotificationServiceUnitTest {

    private ProviderBasedNotificationService service;

    private MutablePluginRegistry<NotificationProvider, PluginMetadata> registry;
    @Mock
    private NotificationProvider defaultNotificationProvider;
    @Mock
    private NotificationProvider notificationProvider;
    @Mock
    private ConfigurationService configService;

    @Mock
    private Notification notification;


    /**
     * Sets up a {@link PluginRegistry} for {@link NotificationProvider}
     * instances. The {@link ProviderBasedNotificationService} to test is
     * equipped with this registry. Furthermore we set up a {@link TaskExecutor}
     * and a {@link NotificationProvider} mock.
     */
    @Before
    public void setUp() {

        service =
                new ProviderBasedNotificationService(
                        defaultNotificationProvider);

        // Setup registry with providers
        when(notificationProvider.supports((PluginMetadata) anyObject()))
                .thenReturn(true);
        registry = SimplePluginRegistry.create();
        registry.addPlugin(notificationProvider);

        Set<User> users = new HashSet<User>();
        users.add(USER);

        when(notification.getRecipients()).thenReturn(users);
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

        service.notify(notification, context);

        verify(defaultNotificationProvider, only()).notify(notification, USER);
    }


    @Test
    public void usesProviderFromRegistryIfConfigured() throws Exception {

        service.setNotificationProviders(registry);
        service.setConfigurationService(configService);

        service.notify(notification, new ConfigBasedNotificationContext(""));

        verify(notificationProvider, times(1)).notify(notification, USER);
    }
}
