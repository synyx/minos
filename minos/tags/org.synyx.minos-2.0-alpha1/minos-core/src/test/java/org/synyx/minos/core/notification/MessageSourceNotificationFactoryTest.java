package org.synyx.minos.core.notification;

import static org.easymock.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.synyx.minos.core.domain.User;

import java.util.Locale;

/**
 * Testing {@link MessageSourceNotificationFactory}
 *
 * @author David Linsin - linsin@synyx.de
 */
public class MessageSourceNotificationFactoryTest {
    private MessageSourceNotificationFactory classUnderTest;
    private MessageSource mockMessageSource;
    private User dummy;
    private static final String KEY = "umt.user.username";
    private static final String NEW_PW = "newPW";


    @Before
    public void setUp() {
        mockMessageSource = createMock(MessageSource.class);
        classUnderTest = new MessageSourceNotificationFactory();
        classUnderTest.setMessageSource(mockMessageSource);
        dummy = new User("dlinsin", "linsin@synyx.de", "test");
        LocaleContextHolder.setLocale(Locale.GERMANY);
    }

    @After
    public void tearDown() {
        reset(mockMessageSource);
    }

    @Test
    public void create_message() {
        classUnderTest.setKey(KEY);
        String result = "New user password";
        expect(mockMessageSource.getMessage((String) anyObject(), (Object[])anyObject(), (Locale) anyObject())).andReturn(result);
        replay(mockMessageSource);
        Notification notification = classUnderTest.create(dummy, dummy.getUsername(), NEW_PW);
        verify(mockMessageSource);
        assertEquals(result, notification.getMessage());
        assertEquals(dummy, notification.getRecipients().toArray()[0]);
    }

    @Test(expected = NullPointerException.class)
    public void no_message_source() {
        classUnderTest.setMessageSource(null);
        classUnderTest.create(dummy, dummy.getUsername(), NEW_PW);
    }
}