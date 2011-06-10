package org.synyx.minos.i18n.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.messagesource.InitializableMessageSource;
import org.synyx.messagesource.MessageProvider;
import org.synyx.minos.i18n.importer.MessageImporter;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageTransferServiceImplUnitTest {

    private MessageTransferServiceImpl messageTransferService;

    @Mock
    private MessageImporter importer;

    @Mock
    private MessageProvider messageProvider;

    @Mock
    private InitializableMessageSource messageSource;

    private List<InitializableMessageSource> mockMessageSources;

    @Before
    public void setUp() {
        mockMessageSources = new ArrayList<InitializableMessageSource>();
        mockMessageSources.add(messageSource);

        messageTransferService = new MessageTransferServiceImpl(importer, messageProvider);
        messageTransferService.setMessageSources(mockMessageSources);
    }

    @Test
    public void testExportMessages() {
        OutputStream outputStream = mock(OutputStream.class);

        messageTransferService.exportMessages(outputStream);
    }

    @Test
    public void testImportMessages() {
        messageTransferService.importMessages();

        verify(messageSource, atLeastOnce()).initialize();
        verify(importer, atLeastOnce()).importMessages();
    }

    @Test
    public void testInitializeMessageSources() {
        messageTransferService.initializeMessageSources();

        verify(messageSource, atLeastOnce()).initialize();
    }

    @Test
    public void testMessageSources() {
        Assert.assertEquals(mockMessageSources, messageTransferService.getMessageSources());
    }
}
