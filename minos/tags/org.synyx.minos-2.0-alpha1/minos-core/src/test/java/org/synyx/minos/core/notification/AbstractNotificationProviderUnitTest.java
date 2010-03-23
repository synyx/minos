package org.synyx.minos.core.notification;

import static org.easymock.EasyMock.*;
import static org.synyx.minos.TestConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.task.TaskExecutor;
import org.synyx.minos.core.domain.User;


/**
 * Unit test for {@link AbstractNotificationProvider}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class AbstractNotificationProviderUnitTest {

    private AbstractNotificationProvider provider;
    private TaskExecutor taskExecutor;


    @Before
    public void setUp() {

        provider = new SampleNotificationProvider();
        taskExecutor = createNiceMock(TaskExecutor.class);
    }


    /**
     * Asserts that the service uses the configured {@link TaskExecutor} for
     * executing the given {@link NotificationProvider}.
     */
    @Test
    public void usesConfiguredTaskExecutorForNotificationProviders() {

        // Apply task executor to service
        provider.setTaskExecuter(taskExecutor);

        expectTaskExecutorToBeExecutedOnce(provider);
    }


    private void expectTaskExecutorToBeExecutedOnce(
            NotificationProvider notificationProvider) {

        taskExecutor.execute((Runnable) anyObject());
        expectLastCall().once();

        replay(taskExecutor);

        provider.notify(createNiceMock(Notification.class), USER);

        verify(taskExecutor);
    }

    private class SampleNotificationProvider extends
            AbstractNotificationProvider {

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
