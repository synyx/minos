package org.synyx.minos.i18n.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class LocaleWrapperUnitTest {
    private static final Locale LOCALE = Locale.GERMAN;
    private static final String DUMMY_STRING = "foobar";

    private LocaleWrapper localeWrapper;

    @Before
    public void setUp() {
        localeWrapper = new LocaleWrapper(LOCALE);

    }

    @Test
    public void testConstructors() {
        LocaleWrapper emptyImplicit = new LocaleWrapper();
        LocaleWrapper emptyExplicit = new LocaleWrapper("", "", "");
        LocaleWrapper nullLocale = new LocaleWrapper(null, null, null);

        Assert.assertEquals(Locale.ROOT, emptyImplicit.getLocale());
        Assert.assertEquals(Locale.ROOT, emptyExplicit.getLocale());
        Assert.assertEquals(Locale.ROOT, nullLocale.getLocale());
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

        LocaleWrapper otherLocaleWrapper = new LocaleWrapper(LOCALE);
        Assert.assertTrue(localeWrapper.equals(otherLocaleWrapper));
    }

    @Test
    public void testLocale() {
        Assert.assertEquals(localeWrapper.getLocale(), LOCALE);
    }

    @Test
    public void testToString() {
        Assert.assertEquals(localeWrapper.toString(), LOCALE.toString());
        Assert.assertEquals(LocaleWrapper.DEFAULT.toString(), "default");
    }

    @Test
    public void testHashCode() {
        LocaleWrapper otherLocaleWrapper = new LocaleWrapper(LOCALE.getLanguage(), LOCALE.getCountry(),
                LOCALE.getVariant());

        Assert.assertEquals(localeWrapper.hashCode(), otherLocaleWrapper.hashCode());
        Assert.assertEquals(localeWrapper.hashCode(), localeWrapper.hashCode());
    }

    @Test
    public void testSetLanguage() {
        localeWrapper.setLanguage(null);
        Assert.assertEquals("", localeWrapper.getLanguage());

        localeWrapper.setLanguage(DUMMY_STRING);
        Assert.assertEquals(DUMMY_STRING, localeWrapper.getLanguage());
    }

    @Test
    public void testSetCountry() {
        localeWrapper.setCountry(null);
        Assert.assertEquals("", localeWrapper.getCountry());

        localeWrapper.setCountry(DUMMY_STRING);
        Assert.assertEquals(DUMMY_STRING, localeWrapper.getCountry());
    }

    @Test
    public void testSetVariant() {
        localeWrapper.setVariant(null);
        Assert.assertEquals("", localeWrapper.getVariant());

        localeWrapper.setVariant(DUMMY_STRING);
        Assert.assertEquals(DUMMY_STRING, localeWrapper.getVariant());
    }



}
