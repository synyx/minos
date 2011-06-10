package org.synyx.minos.i18n.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class AvailableLanguageUnitTest {

    private final String BASE_NAME = "de";

    private AvailableLanguage availableLanguage;

    @Before
    public void setUp() {
        availableLanguage = new AvailableLanguage(LocaleWrapper.DEFAULT, BASE_NAME);
    }

    @Test
    public void testBasename() {
        Assert.assertEquals(availableLanguage.getBasename(), BASE_NAME);
    }

    @Test
    public void testLocaleWrapper() {
        Assert.assertEquals(availableLanguage.getLocale(), LocaleWrapper.DEFAULT);
    }

    @Test
    public void testLocale() {
        Assert.assertEquals(availableLanguage.getRealLocale(), Locale.ROOT);
    }

    @Test
    public void testRequired() {
        Assert.assertTrue(availableLanguage.isRequired());

        availableLanguage.setRequired(false);
        Assert.assertFalse(availableLanguage.isRequired());
    }

    @Test
    public void testToString() {
        Assert.assertEquals(availableLanguage.toString(), BASE_NAME+" "+LocaleWrapper.DEFAULT.toString());
    }
}
