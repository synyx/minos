package org.synyx.minos.core.notification;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.domain.User;

/**
 * Tests {@link org.synyx.minos.core.notification.SimpleMessageNotificationFactory}
 *
 * @author David Linsin
 */
public class SimpleMessageNotificationFactoryTest {
    private SimpleMessageNotificationFactory classUnderTest;
    private User dummy;

    @Before
    public void setUp() {
        dummy = new User("dlinsin", "linsin@synyx.de", "test");
    }

    @Test
    public void create_message() {
        classUnderTest = new SimpleMessageNotificationFactory("User %s has password %s now!");
        SimpleMessageNotification result = new SimpleMessageNotification(dummy, "User %s has password %s now!", dummy.getUsername(), "newPW");
        Notification notification = classUnderTest.create(dummy, dummy.getUsername(), "newPW");
        assertEquals(result.getMessage(), notification.getMessage());
        assertEquals(result.getRecipients(), notification.getRecipients());
    }

    @Test
    public void create_message_without_params() {
        classUnderTest = new SimpleMessageNotificationFactory("User has password now!");
        SimpleMessageNotification result = new SimpleMessageNotification(dummy, "User has password now!");
        Notification notification = classUnderTest.create(dummy);
        assertEquals(result.getMessage(), notification.getMessage());
        assertEquals(result.getRecipients(), notification.getRecipients());
    }
}