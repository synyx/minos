package org.synyx.minos.i18n.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.domain.MessageStatus;
import org.synyx.minos.i18n.domain.MessageTranslation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageViewUnitTest {
    private MessageView messageView;

    private LocaleWrapper resolvingLocale;

    @Mock
    private Message message;

    @Mock
    private MessageTranslation translation;

    @Before
    public void setUp() {
        resolvingLocale = LocaleWrapper.DEFAULT;
        message = mock(Message.class);
        translation = mock(MessageTranslation.class);

        messageView = new MessageView(resolvingLocale, message, translation);
    }

    @Test
    public void testIsNewForTranslation() {
        when(translation.getMessageStatus()).thenReturn(MessageStatus.NEW);

        Assert.assertTrue(messageView.isNewForTranslation());
    }

    @Test
    public void testIsUpdatedForTranslation() {
        when(translation.getMessageStatus()).thenReturn(MessageStatus.UPDATED);

        Assert.assertTrue(messageView.isUpdatedForTranslation());
    }

    @Test
    public void testIsMessageResolved() {
        when(message.getLocale()).thenReturn(resolvingLocale);

        Assert.assertFalse(messageView.isMessageResolved());
    }

}