package org.synyx.minos.core.notification;

import static org.junit.Assert.*;
import static org.synyx.minos.TestConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.notification.AbstractNotification;


/**
 * Unit test for {@link AbstractNotification}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class AbstractNotificationUnitTest {

    private AbstractNotification notification;


    @Before
    public void setUp() {

        this.notification = new AbstractNotification(USER) {

            public String getMessage() {

                return null;
            }
        };
    }


    /**
     * Tests, that {@link AbstractNotification#hasSender()} only returns {@code
     * true} if a sender is set.
     */
    @Test
    public void senderFlag() {

        assertFalse(notification.hasSender());
        assertNull(notification.getSender());

        notification.sender = USER;

        assertTrue(notification.hasSender());
        assertEquals(USER, notification.getSender());
    }
}
