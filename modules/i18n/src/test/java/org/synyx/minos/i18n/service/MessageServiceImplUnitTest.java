package org.synyx.minos.i18n.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.i18n.dao.AvailableLanguageDao;
import org.synyx.minos.i18n.dao.AvailableMessageDao;
import org.synyx.minos.i18n.dao.MessageDao;
import org.synyx.minos.i18n.dao.MessageTranslationDao;
import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.AvailableMessage;
import org.synyx.minos.i18n.domain.LocaleWrapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplUnitTest {

    private static final String BASE_NAME = "foobar";
    private static final String KEY = "key";
    private static final Locale LOCALE = Locale.GERMAN;

    private MessageServiceImpl messageService;

    @Mock
    private MessageDao messageDao;

    @Mock
    private AvailableLanguageDao availableLanguageDao;

    @Mock
    private AvailableMessageDao availableMessageDao;

    @Mock
    private MessageTranslationDao messageTranslationDao;


    @Before
    public void setUp() {
        messageService = new MessageServiceImpl(messageDao, availableLanguageDao, availableMessageDao,
                messageTranslationDao);
    }

    @Test
    public void testGetBasenames() {
        when(messageDao.findBasenames()).thenReturn(Collections.<String>emptyList());

        Assert.assertTrue(messageService.getBasenames().isEmpty());
    }

    @Test
    public void testGetLocales() {
        when(availableLanguageDao.findByBasename(BASE_NAME)).thenReturn(Collections.<AvailableLanguage>emptyList());

        Assert.assertTrue(messageService.getLocales(BASE_NAME).isEmpty());

    }

    @Test
    public void testGetLocaleInformations() {
        when(availableLanguageDao.findByBasename(BASE_NAME)).thenReturn(Collections.<AvailableLanguage>emptyList());

        Assert.assertTrue(messageService.getLocaleInformations(BASE_NAME).isEmpty());
    }

    @Test
    public void testRemoveLanguage() {
        LocaleWrapper locale = LocaleWrapper.DEFAULT;
        AvailableLanguage language = mock(AvailableLanguage.class);

        when(availableLanguageDao.findByBasenameAndLocale(BASE_NAME, LocaleWrapper.DEFAULT)).thenReturn(language);

        messageService.removeLanguage(BASE_NAME, LocaleWrapper.DEFAULT);

        verify(availableLanguageDao, atLeastOnce()).delete(language);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAvailableMessageException() {
        messageService.getAvailableMessage(BASE_NAME, KEY);
    }

    @Test
    public void testGetAvailableMessage() {
        AvailableMessage message = new AvailableMessage(BASE_NAME, KEY, "");

        when(availableMessageDao.findByBasenameAndKey(BASE_NAME, KEY)).thenReturn(Arrays.asList(message));

        Assert.assertEquals(message, messageService.getAvailableMessage(BASE_NAME, KEY));
    }

    @Test
    public void testGetMessages() {
        when(messageDao.getKnownKeys(BASE_NAME)).thenReturn(Collections.<String>emptyList());

        Assert.assertTrue(messageService.getMessages(BASE_NAME, LOCALE).isEmpty());
    }
}
