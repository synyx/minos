package org.synyx.minos.core.notification;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.synyx.minos.core.domain.User;


/**
 * Testing {@link MessageSourceNotificationFactory}
 * 
 * @author David Linsin - linsin@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageSourceNotificationFactoryTest {

    private MessageSourceNotificationFactory classUnderTest;

    @Mock
    private MessageSource mockMessageSource;
    private User dummy;

    private static final String KEY = "umt.user.username";
    private static final String NEW_PW = "newPW";


    @Before
    public void setUp() {

        classUnderTest = new MessageSourceNotificationFactory();
        classUnderTest.setMessageSource(mockMessageSource);
        dummy = new User("dlinsin", "linsin@synyx.de", "test");
        LocaleContextHolder.setLocale(Locale.GERMANY);
    }


    @Test
    public void create_message() {

        classUnderTest.setKey(KEY);
        String result = "New user password";
        when(mockMessageSource.getMessage(anyString(), (Object[]) anyObject(), (Locale) anyObject()))
                .thenReturn(result);

        Notification notification = classUnderTest.create(dummy, dummy.getUsername(), NEW_PW);

        assertEquals(result, notification.getMessage());
        assertEquals(dummy, notification.getRecipients().toArray()[0]);
    }


    @Test(expected = NullPointerException.class)
    public void no_message_source() {

        classUnderTest.setMessageSource(null);
        classUnderTest.create(dummy, dummy.getUsername(), NEW_PW);
    }
}