package org.synyx.minos.umt.aspect;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.notification.Notification;
import org.synyx.minos.core.notification.NotificationContext;
import org.synyx.minos.core.notification.NotificationService;
import org.synyx.minos.test.AbstractModuleIntegrationTest;
import org.synyx.minos.umt.service.UmtNotificationAspect;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Integration test for {@link UmtNotificationAspect}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-context.xml")
public class UmtNotificationIntegrationTest extends AbstractModuleIntegrationTest {

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private NotificationService notificationService;

    // Test fixture
    private User user;


    @Before
    public void setUp() {

        user = new User("username", "email@address.com");
    }


    /**
     * Checks that
     */
    @Test
    public void triggersNotificationAfterSavingANewUser() {

        userManagement.save(user);

        verify(notificationService).notify((Notification) anyObject(), (NotificationContext) anyObject());
    }
}
