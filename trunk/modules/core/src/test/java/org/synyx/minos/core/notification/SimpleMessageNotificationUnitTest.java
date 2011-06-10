package org.synyx.minos.core.notification;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.notification.SimpleMessageNotification;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SimpleMessageNotificationUnitTest {

    @Test
    public void testname() throws Exception {

        Set<User> users = null;

        SimpleMessageNotification notification =
                new SimpleMessageNotification(users, "My message for {0}", new User("gierke", "gierke@synyx.de",
                        "password"));

        assertNotNull(notification.getRecipients());
    }
}
