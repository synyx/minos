package org.synyx.minos.i18n.importer;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.synyx.messagesource.MessageAcceptor;
import org.synyx.messagesource.Messages;
import org.synyx.minos.i18n.dao.MessageDao;
import org.synyx.minos.i18n.dao.MessageMetaDao;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.domain.MessageMeta;
import org.synyx.minos.i18n.domain.MessageMetaStatus;


/**
 * @author Alexander Menz - menz@synyx.de
 */
public class MessageMetaAcceptor implements MessageAcceptor {

    private static final Log LOG = LogFactory.getLog(MessageAcceptor.class);

    private MessageDao messageDao;

    private MessageMetaDao messageMetaDao;


    public MessageMetaAcceptor(MessageDao messageDao, MessageMetaDao messageMetaDao) {

        this.messageDao = messageDao;
        this.messageMetaDao = messageMetaDao;
    }


    @Override
    public void setMessages(String basename, Messages messages) {

        LOG.info("Syncing messages for basename: " + basename);

        Map<String, String> messagesKeyValue = messages.getMessages(null);

        // update message meta by messages
        for (String key : messagesKeyValue.keySet()) {

            String message = messagesKeyValue.get(key);
            setMessage(basename, key, message);
        }

        // find deleted message meta
        List<MessageMeta> metaMessages = messageMetaDao.findByBasename(basename);
        for (MessageMeta messageMeta : metaMessages) {

            String key = messageMeta.getKey();
            if (messages.getMessage(null, key) == null) {

                messageMeta.setStatus(MessageMetaStatus.DELETED);
                messageMetaDao.save(messageMeta);

                LOG.debug("Deleted message for basename: " + basename + " key: " + key);
            }
        }
    }


    protected void setMessage(String basename, String key, String message) {

        MessageMeta messageMeta = messageMetaDao.findByBasenameAndKey(basename, key);

        // new key for the given basename
        if (null == messageMeta) {

            messageMetaDao.save(new MessageMeta(basename, key, message));
            messageDao.save(new Message(null, basename, key, message));

            LOG.debug("Added new message for basename: " + basename + " key: " + key);

            return;
        }

        // existing key with different value
        if (!messageMeta.getMessage().equals(message)) {

            messageMeta.setMessage(message);
            messageMeta.setStatus(MessageMetaStatus.UPDATED);
            messageMetaDao.save(messageMeta);

            LOG.debug("Updated existing message for basename: " + basename + " key: " + key);

            return;
        }

        // nothing changed
    }

}
