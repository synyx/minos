package org.synyx.minos.umt.aspect;

import static org.easymock.EasyMock.*;

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
import org.synyx.minos.umt.service.UserManagement;



/**
 * Integration test for {@code MailNotificationAspect}.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-context.xml")
public class UmtNotificationIntegrationTest extends
        AbstractModuleIntegrationTest {

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private NotificationService notificationService;

    // Test fixture
    private User user;


    @Before
    public void setUp() {

        user = new User("username", "email@address.com", null);
    }


    /**
     * Checks that
     */
    @Test
    public void triggersNotificationAfterSavingANewUser() {

        notificationService.notify((Notification) anyObject(),
                (NotificationContext) anyObject());
        expectLastCall().once();

        replay(notificationService);

        userManagement.save(user);

        verify(notificationService);
    }
}
