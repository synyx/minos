package org.synyx.minos.core.notification;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.TestConstants.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.synyx.minos.core.domain.User;


/**
 * Unit test for {@link EmailNotificationProvider}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailNotificationProviderUnitTest {

    private EmailNotificationProvider provider;

    @Mock
    private MailSender mailSender;


    @Before
    public void setUp() {

        provider = new EmailNotificationProvider(mailSender);
    }


    @Test
    public void notifiesEveryUser() {

        Set<User> users = new HashSet<User>();
        users.add(USER);
        users.add(new User("username", "email@address.com", "password"));

        Notification notification = mock(Notification.class);
        when(notification.getRecipients()).thenReturn(users);

        for (User recipient : users) {
            provider.notify(notification, recipient);
        }

        verify(mailSender, times(users.size())).send(
                (SimpleMailMessage) anyObject());
    }
}
