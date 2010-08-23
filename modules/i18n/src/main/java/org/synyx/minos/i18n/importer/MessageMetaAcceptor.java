package org.synyx.minos.i18n.importer;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.synyx.messagesource.MessageAcceptor;
import org.synyx.messagesource.Messages;
import org.synyx.minos.i18n.dao.AvailableLanguageDao;
import org.synyx.minos.i18n.dao.AvailableMessageDao;
import org.synyx.minos.i18n.dao.MessageDao;
import org.synyx.minos.i18n.dao.MessageTranslationDao;
import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.AvailableMessage;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.domain.MessageStatus;
import org.synyx.minos.i18n.domain.MessageTranslation;
import org.synyx.minos.i18n.util.CollationUtils;


/**
 * @author Alexander Menz - menz@synyx.de
 */
public class MessageMetaAcceptor implements MessageAcceptor {

    private static final Log LOG = LogFactory.getLog(MessageAcceptor.class);

    private MessageDao messageDao;

    private AvailableMessageDao availableMessageDao;

    private AvailableLanguageDao availableLanguageDao;

    private MessageTranslationDao messageTranslationDao;


    public MessageMetaAcceptor(MessageDao messageDao, AvailableMessageDao messageMetaDao,
            MessageTranslationDao messageTranslationDao, AvailableLanguageDao availableLanguageDao) {

        this.messageDao = messageDao;
        this.availableMessageDao = messageMetaDao;
        this.messageTranslationDao = messageTranslationDao;
        this.availableLanguageDao = availableLanguageDao;
    }


    public void setMessages(String basename, Map<String, String> messages) {

        List<AvailableLanguage> availableLanguages = getAvailableLanguages(basename);

        // update availablemessages by messages
        for (String key : messages.keySet()) {

            String message = messages.get(key);
            setMessage(basename, key, message, availableLanguages);
        }

        // find deleted message meta
        List<AvailableMessage> availableMessages = availableMessageDao.findByBasename(basename);
        for (AvailableMessage availableMessage : availableMessages) {

            String key = availableMessage.getKey();
            if (!messages.containsKey(key)) {

                // remove translationinfo
                messageTranslationDao.deleteBy(availableMessage);
                // and info about the key
                availableMessageDao.delete(availableMessage);
                // and all messages
                messageDao.deleteBy(basename, key);

                LOG.debug("Deleted meta-information and all translation-requests for basename: " + basename + " key: "
                        + key);
            }
        }
    }


    @Override
    public void setMessages(String basename, Messages messages) {

        LOG.info("Syncing messages for basename: " + basename);

        // since the messages imported only represent the "available" (or needed) messages
        // we ignore any other messages than the "base"
        Map<String, String> messagesKeyValue = messages.getMessages(null);

        setMessages(basename, messagesKeyValue);

    }


    private List<AvailableLanguage> getAvailableLanguages(String basename) {

        List<AvailableLanguage> availableLanguages = availableLanguageDao.findByBasename(basename);

        if (availableLanguages.isEmpty()) {
            // first time we "see" this basename
            // create a "default" language entry for it

            AvailableLanguage lang = new AvailableLanguage(LocaleWrapper.DEFAULT, basename, true);
            availableLanguageDao.save(lang);
            availableLanguages = availableLanguageDao.findByBasename(basename);

            LOG.info("Created new entry for default-language of new basename: " + basename);
        }

        return availableLanguages;
    }


    protected void setMessage(String basename, String key, String message, List<AvailableLanguage> availableLanguages) {

        AvailableMessage availableMessage =
                CollationUtils.getRealMatch(availableMessageDao.findByBasenameAndKey(basename, key), "key", key);

        MessageStatus status = null;
        if (null == availableMessage) { // new
            availableMessage = new AvailableMessage(basename, key, message);
            availableMessage = availableMessageDao.save(availableMessage);

            // create a (base) message so that the keys get resolved
            messageDao.save(new Message("", "", "", basename, key, message));
            LOG.debug("Added new message for basename: " + basename + " and key: " + key);
            status = MessageStatus.NEW;
        } else if (!availableMessage.getMessage().equals(message)) { // updated
            status = MessageStatus.UPDATED;

            availableMessage.setMessage(message);
            availableMessageDao.save(availableMessage);
            LOG.debug("Updated existing message for basename: " + basename + " and key: " + key);

        } else { // unchanged
            return;
        }

        // now update the information what has to be translated
        for (AvailableLanguage lang : availableLanguages) {
            MessageTranslation translation =
                    messageTranslationDao.findByAvailableMessageAndAvailableLanguage(availableMessage, lang);

            if (translation == null) {

                // if the language is not required we need to check if the key is defined
                if (!lang.isRequired()) {

                    boolean found = false;

                    // check if the key is definied in the current language
                    List<Message> messages = messageDao.findByBasenameAndLocaleAndKey(basename, lang.getLocale(), key);
                    for (Message msg : messages) {
                        if (msg.getKey().equals(key)) {
                            found = true;
                            break;
                        }
                    }

                    // if not skip translation for the language
                    if (!found) {
                        continue;
                    }

                }

                translation = new MessageTranslation(availableMessage, lang, status);
            }

            translation.setMessageStatus(status);

            messageTranslationDao.save(translation);
            LOG.debug("Added/Updated new translation (" + lang.getLocale().toString() + ")for basename: " + basename
                    + " key: " + key);
        }

    }

}
