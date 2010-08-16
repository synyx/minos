/**
 * 
 */
package org.synyx.minos.i18n.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.synyx.messagesource.InitializableMessageSource;
import org.synyx.messagesource.importer.Importer;
import org.synyx.messagesource.util.LocaleUtils;
import org.synyx.minos.i18n.dao.MessageDao;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.web.MessageView;


/**
 * Service-Implementation for managing I18n Messages
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MessageServiceImpl implements MessageService {

    private Importer importer;

    private Importer exporter;

    private MessageDao messageDao;

    private List<InitializableMessageSource> messageSources;


    public MessageServiceImpl(Importer importer, Importer exporter, MessageDao messageDao) {

        this.importer = importer;
        this.exporter = exporter;
        this.messageDao = messageDao;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getMessages(java.lang.String, java.util.Locale)
     */

    private List<Message> getMessagesInternal(String basename, Locale locale) {

        return messageDao.findByBasenameAndLocale(basename, new LocaleWrapper(locale));
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#saveAll(java.util.List)
     */
    @Override
    @Transactional
    public void saveAll(List<Message> messages) {

        for (Message message : messages) {
            if (!message.getLocale().isDefault()) {
                Locale parent = LocaleUtils.getParent(message.getLocale().getLocale());
                Message parentMessage = getMessage(message.getBasename(), message.getKey(), parent);
                if (parentMessage.getMessage().equals(message.getMessage())) {
                    // skip messages that dont have differences to their parent
                    if (!message.isNew()) {
                        messageDao.delete(messageDao.readByPrimaryKey(message.getId()));
                    }
                    continue;

                }
            }

            if (StringUtils.hasLength(message.getMessage())) {
                messageDao.save(message);

            } else if (!message.isNew()) {
                messageDao.delete(messageDao.readByPrimaryKey(message.getId()));
            }
        }
    }


    /**
     * @param basename
     * @param key
     * @param parent
     * @return
     */
    private Message getMessage(String basename, String key, Locale locale) {

        Message message = null;

        while (message == null) {
            List<Message> messages =
                    messageDao.findByBasenameAndLanguageAndCountryAndVariantAndKey(basename, LocaleUtils
                            .getLanguage(locale), LocaleUtils.getCountry(locale), LocaleUtils.getVariant(locale), key);
            if (!messages.isEmpty()) {

                // this is done because of case-insensitive collation that is mostly used
                // this leads to non-unique results returned. the easiest way to find out the
                // correct one is do a manual comparison...
                for (Message msg : messages) {
                    if (msg.getKey().equals(key)) {
                        message = msg;
                    }
                }

            }

            if (locale == null) {
                return message;
            }
            locale = LocaleUtils.getParent(locale);
        }

        return message;

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#initializeMessageSources()
     */
    @Override
    public void initializeMessageSources() {

        if (messageSources == null) {
            return;
        }

        for (InitializableMessageSource source : messageSources) {
            source.initialize();
        }
    }


    /**
     * @param messageSources the messageSources to set
     */
    public void setMessageSources(List<InitializableMessageSource> messageSources) {

        this.messageSources = messageSources;
    }


    /**
     * @return the messageSources
     */
    public List<InitializableMessageSource> getMessageSources() {

        return messageSources;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getBasenames()
     */
    @Override
    public List<String> getBasenames() {

        return messageDao.findBasenames();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getLocales(java.lang.String)
     */
    @Override
    public List<LocaleWrapper> getLocales(String basename) {

        return messageDao.findLocales(basename);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getMessagesHierarchically(java.lang.String, java.util.Locale,
     * java.util.Locale)
     */
    @Override
    @Transactional(readOnly = true)
    public List<MessageView> getMessages(String basename, Locale locale) {

        return getMessages(basename, locale, LocaleUtils.getParent(locale));

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getMessagesHierarchically(java.lang.String, java.util.Locale)
     */
    @Override
    public List<MessageView> getMessages(String basename, Locale locale, Locale referenceLocale) {

        List<MessageView> result = new ArrayList<MessageView>();

        List<String> knownKeys = messageDao.getKnownKeys(basename);

        List<Map<String, Message>> messageChain = getMessageChain(basename, locale);
        List<Map<String, Message>> referenceMessageChain = getMessageChain(basename, referenceLocale);

        Map<String, MessageView> referenceMessageMap = new HashMap<String, MessageView>();
        // Build a Map of referenceMessages
        for (String key : knownKeys) {
            for (Map<String, Message> keys : referenceMessageChain) {
                if (keys.containsKey(key)) {
                    referenceMessageMap.put(key, new MessageView(new LocaleWrapper(referenceLocale), keys.get(key)));
                    break;
                }
            }
        }

        // add the first message in chain with the given key to the result
        for (String key : knownKeys) {
            for (Map<String, Message> keys : messageChain) {
                if (keys.containsKey(key)) {
                    result.add(new MessageView(new LocaleWrapper(locale), keys.get(key), referenceMessageMap.get(key)));
                    break;
                }
            }
        }

        Collections.sort(result, new Comparator<MessageView>() {

            @Override
            public int compare(MessageView o1, MessageView o2) {

                return o1.getMessage().getKey().compareTo(o2.getMessage().getKey());
            }
        });
        return result;

    }


    /**
     * @param locale
     * @return
     */
    private List<Map<String, Message>> getMessageChain(String basename, Locale locale) {

        List<Map<String, Message>> chain = new ArrayList<Map<String, Message>>();

        // add the requested language
        chain.add(toMap(getMessagesInternal(basename, locale)));

        // add all parents including locale == null
        do {
            locale = LocaleUtils.getParent(locale);
            chain.add(toMap(getMessagesInternal(basename, locale)));

        } while (locale != null);

        return chain;
    }


    private Map<String, Message> toMap(List<Message> messages) {

        Map<String, Message> map = new HashMap<String, Message>(messages.size());
        for (Message message : messages) {
            map.put(message.getKey(), message);
        }

        return map;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#exportMessages()
     */
    @Override
    public void exportMessages() {

        exporter.importMessages();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#importMessages()
     */
    @Override
    public void importMessages() {

        importer.importMessages();
        initializeMessageSources();

    }

}
