package org.synyx.minos.core.notification;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.TestConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.task.TaskExecutor;
import org.synyx.minos.core.domain.User;


/**
 * Unit test for {@link AbstractNotificationProvider}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractNotificationProviderUnitTest {

    private AbstractNotificationProvider provider;

    @Mock
    private TaskExecutor taskExecutor;


    @Before
    public void setUp() {

        provider = new SampleNotificationProvider();
    }


    /**
     * Asserts that the service uses the configured {@link TaskExecutor} for executing the given
     * {@link NotificationProvider}.
     */
    @Test
    public void usesConfiguredTaskExecutorForNotificationProviders() {

        // Apply task executor to service
        provider.setTaskExecuter(taskExecutor);

        expectTaskExecutorToBeExecutedOnce(provider);
    }


    private void expectTaskExecutorToBeExecutedOnce(NotificationProvider notificationProvider) {

        provider.notify(mock(Notification.class), USER);

        verify(taskExecutor, only()).execute((Runnable) anyObject());
    }

    private class SampleNotificationProvider extends AbstractNotificationProvider {

        public SampleNotificationProvider() {

            super("", "");
        }


        @Override
        public void notifyInExecutor(Notification notification, User recipient) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
