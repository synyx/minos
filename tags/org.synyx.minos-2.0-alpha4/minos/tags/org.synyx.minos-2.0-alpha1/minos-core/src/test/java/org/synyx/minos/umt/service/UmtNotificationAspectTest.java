package org.synyx.minos.umt.service;

import org.aspectj.lang.ProceedingJoinPoint;
import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Test;
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
public class UmtNotificationAspectTest {
    private UmtNotificationAspect classUnderTest;
    private NotificationService notificationService;
    private NotificationFactory notificationFactory;
    private ProceedingJoinPoint joinPoint;
    private Object[] MOCKS;
    private ConfigBasedNotificationContext configBasedNotificationContext;
    User dummy = new User("dlinsin", "linsin@synyx.de", "blah");

    @Before
    public void setUp() {
        joinPoint = createMock(ProceedingJoinPoint.class);
        configBasedNotificationContext = new ConfigBasedNotificationContext("blah");
        notificationService = createMock(NotificationService.class);
        notificationFactory = createMock(NotificationFactory.class);

        classUnderTest = new UmtNotificationAspect() {
            @Override
            protected ConfigBasedNotificationContext createContext() {
                return configBasedNotificationContext;
            }
        };

        classUnderTest.setNotificationService(notificationService);
        classUnderTest.setNotificationFactory(notificationFactory);

        MOCKS = new Object[]{notificationService, notificationFactory, joinPoint};
    }

    @Test
    public void send_new_password() throws Throwable {
        classUnderTest.createNewPassword("blah1");

        expect(joinPoint.proceed()).andReturn(null);
        SimpleMessageNotification notification = new SimpleMessageNotification(dummy, "new pw");
        expect(notificationFactory.create(dummy, "dlinsin", "blah1")).andReturn(notification);
        notificationService.notify(notification, configBasedNotificationContext);
        replay(MOCKS);

        classUnderTest.sendNewPassword(joinPoint, dummy);

        verify(MOCKS);
    }

    @Test
    public void do_not_send_new_password() throws Throwable {
        expect(joinPoint.proceed()).andReturn(null);
        replay(MOCKS);

        classUnderTest.sendNewPassword(joinPoint, dummy);

        verify(MOCKS);
    }
}