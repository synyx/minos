package org.synyx.minos.calendar.configuration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.synyx.hera.metadata.PluginMetadata;
import org.synyx.minos.core.notification.EmailNotificationProvider;
import org.synyx.minos.core.notification.NotificationProvider;
import org.synyx.minos.test.AbstractModuleIntegrationTest;

import com.synyx.confyx.family.Family;


/**
 * Integration test to check configuration options of the calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class CalendarConfigurationIntegrationTest extends
        AbstractModuleIntegrationTest {

    @Autowired
    @Qualifier("minos.calendar.invitation.notification")
    private Family<PluginMetadata> notificationFamily;

    @Autowired
    private NotificationProvider emailNotificationProvider;


    /**
     * Checks, that the default member of the invitation notification family is
     * the {@link EmailNotificationProvider}.
     * 
     * @throws Exception
     */
    @Test
    public void testname() throws Exception {

        assertEquals(emailNotificationProvider.getMetadata(),
                notificationFamily.getDefaultMember());
    }
}
