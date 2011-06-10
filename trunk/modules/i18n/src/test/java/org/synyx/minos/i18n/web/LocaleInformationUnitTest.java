package org.synyx.minos.i18n.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.LocaleWrapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocaleInformationUnitTest {
    private final long COUNT_NEW = 5;
    private final long COUNT_UPDATED = 5;
    private final long COUNT_TOTAL = 20;

    private LocaleInformation localeInformation;

    @Mock
    private AvailableLanguage availableLanguage;

    @Before
    public void setUp() {
        availableLanguage = mock(AvailableLanguage.class);

        localeInformation = new LocaleInformation(availableLanguage, COUNT_NEW, COUNT_UPDATED, COUNT_TOTAL);
    }

    @Test
    public void testCount() {
        Assert.assertEquals(Long.valueOf(COUNT_NEW), localeInformation.getCountNew());
        Assert.assertEquals(Long.valueOf(COUNT_UPDATED), localeInformation.getCountUpdated());
        Assert.assertEquals(Long.valueOf(COUNT_TOTAL), localeInformation.getCountTotal());
        Assert.assertEquals(Long.valueOf(COUNT_TOTAL - COUNT_UPDATED - COUNT_NEW), localeInformation.getCountUnchanged());
    }

    @Test
    public void testLanguage() {
        String basename = "foobar";

        when(availableLanguage.isRequired()).thenReturn(true);
        when(availableLanguage.getBasename()).thenReturn(basename);
        when(availableLanguage.getLocale()).thenReturn(LocaleWrapper.DEFAULT);

        Assert.assertTrue(localeInformation.isRequired());
        Assert.assertFalse(localeInformation.isDeletable());
        Assert.assertEquals(LocaleWrapper.DEFAULT, localeInformation.getLocale());
        Assert.assertEquals(basename, localeInformation.getBasename());
    }


}
