package org.synyx.minos.core.notification;

import static org.easymock.EasyMock.*;
import static org.synyx.minos.TestConstants.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.synyx.minos.core.domain.User;


/**
 * Unit test for {@link EmailNotificationProvider}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EmailNotificationProviderUnitTest {

    private EmailNotificationProvider provider;

    private MailSender mailSender;


    @Before
    public void setUp() {

        mailSender = createNiceMock(MailSender.class);

        provider = new EmailNotificationProvider(mailSender);
    }


    @Test
    public void notifiesEveryUser() {

        Set<User> users = new HashSet<User>();
        users.add(USER);
        users.add(new User("username", "email@address.com", "password"));

        Notification notification = createNiceMock(Notification.class);
        expect(notification.getRecipients()).andStubReturn(users);

        mailSender.send((SimpleMailMessage) anyObject());
        expectLastCall().times(users.size());

        replay(mailSender, notification);

        for (User recipient : users) {
            provider.notify(notification, recipient);
        }

        verify(mailSender, notification);
    }
}
