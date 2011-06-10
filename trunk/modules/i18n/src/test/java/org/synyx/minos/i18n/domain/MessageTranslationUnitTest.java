package org.synyx.minos.i18n.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageTranslationUnitTest {

    private final MessageStatus MESSAGE_STATUS = MessageStatus.NEW;
    private final String BASE_NAME = "basename";
    private final String KEY = "key";
    private final String MESSAGE = "message";

    private MessageTranslation messageTranslation;

    private AvailableMessage availableMessage;
    private AvailableLanguage availableLanguage;

    @Before
    public void setUp() {
        availableMessage = new AvailableMessage(BASE_NAME, KEY, MESSAGE);
        availableLanguage = new AvailableLanguage(LocaleWrapper.DEFAULT, BASE_NAME);

        messageTranslation = new MessageTranslation(availableMessage, availableLanguage, MESSAGE_STATUS);
    }

    @Test
    public void testAvailableMessage() {
        Assert.assertEquals(availableMessage, messageTranslation.getAvailableMessage());
    }

    @Test
    public void testAvailableLanguage() {
        Assert.assertEquals(availableLanguage, messageTranslation.getAvailableLanguage());
    }

    @Test
    public void testMessageStatus() {
        Assert.assertEquals(MESSAGE_STATUS, messageTranslation.getMessageStatus());
    }
}
