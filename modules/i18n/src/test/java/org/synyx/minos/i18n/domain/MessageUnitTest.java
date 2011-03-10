package org.synyx.minos.i18n.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class MessageUnitTest {

    private final Locale LOCALE = Locale.GERMAN;
    private static final String BASE_NAME = "de";
    private static final String KEY = "key";
    private static final String MESSAGE = "message";

    private Message message;

    private LocaleWrapper localeWrapper;


    @Before
    public void setUp() {
        message = new Message(LOCALE, BASE_NAME, KEY, MESSAGE);
        localeWrapper = new LocaleWrapper(LOCALE);
    }

    @Test
    public void testBasename() {
        Assert.assertEquals(BASE_NAME, message.getBasename());
    }

    @Test
    public void testKey() {
        Assert.assertEquals(KEY, message.getKey());
    }

    @Test
    public void testMessage() {
        Assert.assertEquals(MESSAGE, message.getMessage());
    }

    @Test
    public void testLocale() {
        Assert.assertEquals(localeWrapper, message.getLocale());
    }

    @Test
    public void testToString() {
        String stringFormat = String.format("[%s %s], %s=%s", BASE_NAME, localeWrapper.toString(), KEY, MESSAGE);

        Assert.assertEquals(stringFormat, message.toString());
    }




}
