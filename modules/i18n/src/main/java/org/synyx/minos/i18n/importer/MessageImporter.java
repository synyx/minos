/**
 * 
 */
package org.synyx.minos.i18n.importer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
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
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MessageImporter {

    private Map<String, Resource> resources;

    private static final Log LOG = LogFactory.getLog(MessageImporter.class);

    private MessageDao messageDao;

    private AvailableMessageDao availableMessageDao;

    private AvailableLanguageDao availableLanguageDao;

    private MessageTranslationDao messageTranslationDao;


    public MessageImporter() {

    }


    public MessageImporter(MessageDao messageDao, AvailableMessageDao availableMessageDao,
            AvailableLanguageDao availableLanguageDao, MessageTranslationDao messageTranslationDao) {

        super();
        this.messageDao = messageDao;
        this.availableMessageDao = availableMessageDao;
        this.availableLanguageDao = availableLanguageDao;
        this.messageTranslationDao = messageTranslationDao;
    }


    public void importMessages(String basename, Resource resource) {

        Map<String, String> messages = loadProperties(resource);

        List<AvailableLanguage> availableLanguages = getAvailableLanguages(basename);

        // update/create messages
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


    /**
     * Loads {@link Map} of Key=Value pairs from the given {@link Resource} while handling errors.
     */
    private Map<String, String> loadProperties(Resource resource) {

        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(resource.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException("Could not load messages from " + resource.toString() + ": " + e.getMessage(), e);
        }

        Map<String, String> messages = new HashMap<String, String>(properties.size());

        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) properties.getProperty(key);
            messages.put(key, value);
        }

        return messages;

    }


    public void importMessages() {

        for (String resourceName : resources.keySet()) {
            importMessages(resourceName, resources.get(resourceName));
        }

    }


    public Map<String, Resource> getResources() {

        return resources;
    }


    public void setResources(Map<String, Resource> resources) {

        this.resources = resources;
    }

}
