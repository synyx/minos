/**
 * 
 */
package org.synyx.minos.i18n.service;

import java.io.OutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.messagesource.InitializableMessageSource;
import org.synyx.messagesource.MessageProvider;
import org.synyx.messagesource.filesystem.ZipMessageAcceptor;
import org.synyx.messagesource.importer.Importer;
import org.synyx.minos.i18n.importer.MessageImporter;


/**
 * Implementation of {@link MessageTransferService}
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MessageTransferServiceImpl implements MessageTransferService {

    private static final Log LOG = LogFactory.getLog(MessageTransferServiceImpl.class);

    private List<InitializableMessageSource> messageSources;

    private MessageImporter importer;

    private MessageProvider messageProvider;


    /**
     * Creates a new instance of {@link MessageTransferService}
     * 
     * @param importer the importer
     * @param messageProvider the {@link MessageProvider}
     */
    public MessageTransferServiceImpl(MessageImporter importer, MessageProvider messageProvider) {

        super();
        this.importer = importer;
        this.messageProvider = messageProvider;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageTransferService#exportMessages(java.io.OutputStream)
     */
    @Override
    @Transactional
    public void exportMessages(OutputStream stream) {

        LOG.info("Exporting all messages to zip.");

        ZipMessageAcceptor zipAcceptor = new ZipMessageAcceptor(stream);
        zipAcceptor.initialize();
        Importer importer = new Importer(messageProvider, zipAcceptor);
        importer.importMessages();
        zipAcceptor.finish();

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageTransferService#importMessages()
     */
    @Override
    @Transactional
    public void importMessages() {

        importer.importMessages();
        initializeMessageSources();

        LOG.info("Imported messages and reinitialized MessageSources.");

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageTransferService#initializeMessageSources()
     */
    @Override
    public void initializeMessageSources() {

        if (messageSources == null) {
            return;
        }

        for (InitializableMessageSource source : messageSources) {
            source.initialize();
        }

        LOG.info("Reinitialized " + messageSources.size() + " MessageSources.");
    }


    public void setMessageSources(List<InitializableMessageSource> messageSources) {

        this.messageSources = messageSources;
    }


    public List<InitializableMessageSource> getMessageSources() {

        return messageSources;
    }

}
