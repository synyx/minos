package org.synyx.minos.umt.service;

import static org.mockito.Mockito.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.notification.ConfigBasedNotificationContext;
import org.synyx.minos.core.notification.NotificationFactory;
import org.synyx.minos.core.notification.NotificationService;
import org.synyx.minos.core.notification.SimpleMessageNotification;


/**
 * Testing {@link UmtNotificationAspect}
 * 
 * @author David Linsin - linsin@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class UmtNotificationAspectTest {

    private UmtNotificationAspect classUnderTest;
    @Mock
    private NotificationService notificationService;
    @Mock
    private NotificationFactory notificationFactory;
    @Mock
    private ProceedingJoinPoint joinPoint;

    private ConfigBasedNotificationContext configBasedNotificationContext;
    User dummy = new User("dlinsin", "linsin@synyx.de", "blah");


    @Before
    public void setUp() {

        configBasedNotificationContext =
                new ConfigBasedNotificationContext("blah");

        classUnderTest = new UmtNotificationAspect() {

            @Override
            protected ConfigBasedNotificationContext createContext() {

                return configBasedNotificationContext;
            }
        };

        classUnderTest.setNotificationService(notificationService);
        classUnderTest.setNotificationFactory(notificationFactory);
    }


    @Test
    public void send_new_password() throws Throwable {

        classUnderTest.createNewPassword("blah1");
        SimpleMessageNotification notification =
                new SimpleMessageNotification(dummy, "new pw");
        when(notificationFactory.create(dummy, "dlinsin", "blah1")).thenReturn(
                notification);
        notificationService
                .notify(notification, configBasedNotificationContext);

        classUnderTest.sendNewPassword(joinPoint, dummy);

        verify(joinPoint).proceed();
    }


    @Test
    public void do_not_send_new_password() throws Throwable {

        classUnderTest.sendNewPassword(joinPoint, dummy);

        verify(joinPoint).proceed();
    }
}