package org.synyx.minos.i18n.web;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.synyx.minos.core.web.WebTestUtils;
import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.AvailableMessage;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.service.MessageService;
import org.synyx.minos.i18n.service.MessageTransferService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class I18nControllerUnitTest {

    private final String BASE_NAME = "foobar";
    private final String KEY = "key";
    private final Locale LOCALE = Locale.GERMAN;

    @InjectMocks
    private I18nController i18nController = new I18nController();

    @Mock
    private MessageService messageService;

    @Mock
    private MessageTransferService messageTransferService;

    private Model model;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;


    @Before
    public void setUp() {
        model = new ExtendedModelMap();
        request= new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        doNothing().when(messageTransferService).initializeMessageSources();
    }

    @Test
    public void testShowOverview() {
        Assert.assertEquals("i18n/main", i18nController.showOverview(model));
    }

    @Test
    public void testReinitializeMessageSources() {
        Assert.assertEquals("redirect:/i18n", i18nController.reinitializeMessageSources(model));
        WebTestUtils.assertSuccessMessage(model);
    }

    @Test
    public void testImportMessages() {
        doNothing().when(messageTransferService).importMessages();

        Assert.assertEquals("redirect:/i18n", i18nController.importMessages(model));
        WebTestUtils.assertSuccessMessage(model);
    }

    @Test
    public void testExportMessages() {
        OutputStream outputStream = mock(OutputStream.class);

        doNothing().when(messageTransferService).exportMessages(outputStream);

        i18nController.exportMessages(response, outputStream);

        Assert.assertEquals("application/zip", response.getContentType());
        verify(messageTransferService, atLeastOnce()).exportMessages(outputStream);
    }

    @Test
    public void testShowBasenames() {
        List<String> basenames = Collections.<String>emptyList();
        when(messageService.getBasenames()).thenReturn(basenames);

        Assert.assertEquals("i18n/basenames", i18nController.showBasenames(model));

        WebTestUtils.assertContains(model, "basenames", List.class);
    }

    @Test
    public void testShowBasename() {
        List<LocaleInformation> locales = Collections.<LocaleInformation>emptyList();

        when(messageService.getLocaleInformations(BASE_NAME)).thenReturn(locales);

        Assert.assertEquals("i18n/basename", i18nController.showBasename(BASE_NAME, model));

        WebTestUtils.assertContains(model, "basename", String.class);
        WebTestUtils.assertContains(model, "localeInformations", List.class);
        WebTestUtils.assertContains(model, "newLanguage", AvailableLanguage.class);
    }

    @Test
    public void testShowImportableLanguages() {
        List<LocaleInformation> locales = Collections.<LocaleInformation>emptyList();

        when(messageService.getLocaleInformations(BASE_NAME)).thenReturn(locales);

        Assert.assertEquals("i18n/import", i18nController.showImportableLanguages(BASE_NAME, model));

        WebTestUtils.assertContains(model, "basename", String.class);
        WebTestUtils.assertContains(model, "localeInformations", List.class);
    }

    @Test
    public void testImportLanguage() throws IOException {
        AvailableLanguage language = mock(AvailableLanguage.class);
        MockMultipartFile multipartFile = new MockMultipartFile("test", "TEST\n".getBytes());

        doNothing().when(messageService).saveAll(eq(language), Matchers.<Properties>anyObject());

        Assert.assertEquals("redirect:/i18n/basenames/foobar",
                i18nController.importLanguage(BASE_NAME, language, multipartFile, model));
    }

    @Test
    public void testAddNewLanguageForBasename() throws IOException {
        AvailableLanguage language = mock(AvailableLanguage.class);
        List<LocaleWrapper> localeWrappers = mock(List.class);

        when(messageService.getLocales(BASE_NAME)).thenReturn(localeWrappers);
        doNothing().when(messageService).addLanguage(language);

        Assert.assertEquals("redirect:/i18n/basenames/foobar",
                i18nController.addNewLanguageForBasename(BASE_NAME, language, model));

        WebTestUtils.assertSuccessMessage(model);
    }

    @Test
    public void testShowConfirmationForRemoveLanguageForBasename() {

        Assert.assertEquals("i18n/languagedeleteconfirmation",
                i18nController.showConfirmationForRemoveLanguageForBasename(BASE_NAME, LOCALE, model));

        WebTestUtils.assertContains(model, "basename", String.class);
        WebTestUtils.assertContains(model, "locale", Locale.class);
    }

    @Test
    public void testRemoveLanguageForBasenameSuccess() {
        Assert.assertEquals("redirect:/i18n/basenames/foobar",
                i18nController.removeLanguageForBasename(BASE_NAME, LOCALE, model));

        WebTestUtils.assertSuccessMessage(model);
    }

    @Test
    public void testRemoveLanguageForBasenameError() {
        Assert.assertEquals("redirect:/i18n/basenames/foobar",
                i18nController.removeLanguageForBasename(BASE_NAME, Locale.ROOT, model));

        WebTestUtils.assertErrorMessage(model);
    }

    @Test
    public void testShowMessages() {
        List<MessageView> messages = mock(List.class);
        List<LocaleWrapper> locales = mock(List.class);

        when(messageService.getMessages(eq(BASE_NAME), eq(LOCALE), anyBoolean(), anyBoolean())).thenReturn(messages);
        when(messageService.getLocales(BASE_NAME)).thenReturn(locales);

        Assert.assertEquals("i18n/messages",
                i18nController.showMessages(BASE_NAME, LOCALE, "", LOCALE, model));

        WebTestUtils.assertContains(model, "basename", String.class);
        WebTestUtils.assertContains(model, "locale", LocaleWrapper.class);
        WebTestUtils.assertContains(model, "reference", LocaleWrapper.class);
        WebTestUtils.assertContains(model, "filter", String.class);
        WebTestUtils.assertContains(model, "locales", List.class);
        WebTestUtils.assertContains(model, "messages", List.class);
    }

    @Test
    public void testShowMessage() throws Exception {
        MessageView messageView = mock(MessageView.class);
        AvailableMessage availableMessage = mock(AvailableMessage.class);
        org.synyx.minos.i18n.domain.Message message = mock(org.synyx.minos.i18n.domain.Message.class);

        when(messageService.getMessage(BASE_NAME, KEY, LOCALE)).thenReturn(messageView);
        when(messageService.getAvailableMessage(BASE_NAME, KEY)).thenReturn(availableMessage);

        when(messageView.getMessage()).thenReturn(message);
        when(messageView.getResolvingLocale()).thenReturn(LocaleWrapper.DEFAULT);

        when(message.getId()).thenReturn(Long.valueOf(1));
        when(message.getLocale()).thenReturn(LocaleWrapper.DEFAULT);

        i18nController.showMessage(BASE_NAME, LOCALE, KEY, LOCALE, request, response);
    }

    @Test
    public void testSaveMessage() throws Exception {
        org.synyx.minos.i18n.domain.Message message = mock(org.synyx.minos.i18n.domain.Message.class);
        boolean finished = true;

        doNothing().when(messageService).save(message, finished);

        i18nController.saveMessage(BASE_NAME, LOCALE, KEY, LOCALE, finished, message, request, response);
    }
}