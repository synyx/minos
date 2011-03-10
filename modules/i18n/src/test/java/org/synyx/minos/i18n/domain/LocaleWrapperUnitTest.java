package org.synyx.minos.i18n.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class LocaleWrapperUnitTest {
    private final Locale locale = Locale.GERMAN;

    private LocaleWrapper localeWrapper;

    @Before
    public void setUp() {
        localeWrapper = new LocaleWrapper(locale);

    }

    @Test
    public void testDefault() {
        Assert.assertTrue(LocaleWrapper.DEFAULT.isDefault());
        Assert.assertFalse(localeWrapper.isDefault());
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(localeWrapper.equals(localeWrapper));
        Assert.assertFalse(localeWrapper.equals(null));
        Assert.assertFalse(localeWrapper.equals(""));

        LocaleWrapper otherLocaleWrapper = new LocaleWrapper(locale);
        Assert.assertTrue(localeWrapper.equals(otherLocaleWrapper));
    }

    @Test
    public void testLocale() {
        Assert.assertEquals(localeWrapper.getLocale(), locale);
    }

    @Test
    public void testToString() {
        Assert.assertEquals(localeWrapper.toString(), locale.toString());
        Assert.assertEquals(LocaleWrapper.DEFAULT.toString(), "default");
    }
}
