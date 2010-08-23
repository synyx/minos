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
import org.synyx.messagesource.util.LocaleUtils;
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
import org.synyx.minos.i18n.web.LocaleInformation;
import org.synyx.minos.i18n.web.MessageView;


/**
 * Service-Implementation for managing I18n Messages
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MessageServiceImpl implements MessageService {

    private MessageDao messageDao;

    private AvailableLanguageDao availableLanguageDao;

    private AvailableMessageDao availableMessageDao;

    private MessageTranslationDao messageTranslationDao;

    private Locale defaultLocale = Locale.getDefault();


    public MessageServiceImpl(MessageDao messageDao, AvailableLanguageDao availableLanguageDao,
            AvailableMessageDao availableMessageDao, MessageTranslationDao messageTranslationDao) {

        this.messageDao = messageDao;
        this.availableLanguageDao = availableLanguageDao;
        this.availableMessageDao = availableMessageDao;
        this.messageTranslationDao = messageTranslationDao;
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
            save(message);
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#save(org.synyx.minos.i18n.domain.Message)
     */
    @Override
    @Transactional
    public void save(Message message) {

        if (!message.getLocale().isDefault()) {
            Locale parent = LocaleUtils.getParent(message.getLocale().getLocale());
            Message parentMessage = getMessageEntity(message.getBasename(), message.getKey(), parent);
            if (parentMessage.getMessage().equals(message.getMessage())) {
                // skip messages that dont have differences to their parent
                if (!message.isNew()) {
                    messageDao.delete(messageDao.readByPrimaryKey(message.getId()));
                }
                return;

            }
        }

        if (StringUtils.hasLength(message.getMessage())) {
            messageDao.save(message);

        } else if (!message.isNew()) {
            messageDao.delete(messageDao.readByPrimaryKey(message.getId()));
        }

    }


    /**
     * @param basename
     * @param key
     * @param parent
     * @return
     */
    private Message getMessageEntity(String basename, String key, Locale locale) {

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
    @Transactional(readOnly = true)
    public List<LocaleWrapper> getLocales(String basename) {

        List<AvailableLanguage> languages = availableLanguageDao.findByBasename(basename);
        List<LocaleWrapper> locales = new ArrayList<LocaleWrapper>();

        for (AvailableLanguage lang : languages) {
            locales.add(lang.getLocale());
        }
        return locales;

    }


    @Override
    @Transactional(readOnly = true)
    public List<LocaleInformation> getLocaleInformations(String basename) {

        List<LocaleInformation> infos = new ArrayList<LocaleInformation>();
        for (LocaleWrapper locale : getLocales(basename)) {
            Long newCount = messageTranslationDao.countByStatus(basename, locale, MessageStatus.NEW);
            Long updatedCount = messageTranslationDao.countByStatus(basename, locale, MessageStatus.UPDATED);
            Long totalCount = messageDao.countByBasenameAndLocale(basename, locale);
            infos.add(new LocaleInformation(basename, locale, newCount, updatedCount, totalCount));
        }

        return infos;

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getMessagesHierarchically(java.lang.String, java.util.Locale)
     */
    @Override
    public List<MessageView> getMessages(String basename, Locale locale) {

        List<MessageView> result = new ArrayList<MessageView>();

        List<String> knownKeys = messageDao.getKnownKeys(basename);

        List<Map<String, Message>> messageChain = getMessageChain(basename, locale);

        for (String key : knownKeys) {

            MessageView message = getMessage(basename, key, locale, messageChain);
            if (message != null) {
                result.add(message);
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


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getMessage(String, String, Locale)
     */
    @Override
    public MessageView getMessage(String basename, String key, Locale locale) {

        List<Map<String, Message>> messageChain = getMessageChain(basename, locale);

        return getMessage(basename, key, locale, messageChain);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#getMetaMessage(java.lang.String, java.lang.String)
     */
    @Override
    public AvailableMessage getAvailableMessage(String basename, String key) {

        AvailableMessage availableMessage = availableMessageDao.findByBasenameAndKey(basename, key);

        if (null == availableMessage) {
            throw new IllegalArgumentException("No Available Message for basename: " + basename + " and key: " + key
                    + " !");
        }

        return availableMessage;
    }


    private MessageView getMessage(String basename, String key, Locale locale, List<Map<String, Message>> messageChain) {

        // retrieve message (setting the possibly found reference message)
        MessageView messageView = null;
        for (Map<String, Message> messages : messageChain) {
            if (messages.containsKey(key)) {

                Message message = null;
                LocaleWrapper resolvingLocale = new LocaleWrapper(locale);

                // if the messages locale does not equal the requested locale, create a new message entity prefilled
                // by the resolved message text and requested locale
                if (!resolvingLocale.equals(messages.get(key).getLocale())) {
                    Message resolvedMessage = messages.get(key);
                    resolvingLocale = resolvedMessage.getLocale();

                    message = new Message(locale, basename, key, resolvedMessage.getMessage());
                } else {
                    message = messages.get(key);
                }

                MessageTranslation translation = getTranslationInformation(basename, key, locale);
                messageView = new MessageView(resolvingLocale, message, translation);
                break;
            }
        }

        return messageView;
    }


    /**
     * @param basename
     * @param key
     * @param referenceLocale
     * @return
     */
    private MessageTranslation getTranslationInformation(String basename, String key, Locale locale) {

        AvailableLanguage lang = availableLanguageDao.findByBasenameAndLocale(basename, new LocaleWrapper(locale));
        AvailableMessage message = availableMessageDao.findByBasenameAndKey(basename, key);

        if (lang == null || message == null) {
            throw new IllegalArgumentException("Given combination of basename (" + basename + "), locale (" + locale
                    + ") and key (" + key + ") is invalid.");
        }

        return messageTranslationDao.findByAvailableMessageAndAvailableLanguage(message, lang);

    }


    /**
     * @param locale
     * @return
     */
    private List<Map<String, Message>> getMessageChain(String basename, Locale locale) {

        List<Locale> path = LocaleUtils.getPath(locale, defaultLocale);

        List<Map<String, Message>> chain = new ArrayList<Map<String, Message>>();
        for (Locale loc : path) {
            chain.add(toMap(getMessagesInternal(basename, loc)));
        }

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
     * @see org.synyx.minos.i18n.service.MessageService#addLanguage(java.lang.String,
     * org.synyx.minos.i18n.domain.LocaleWrapper)
     */
    @Override
    @Transactional
    public void addLanguage(AvailableLanguage language) {

        if (availableLanguageDao.findByBasenameAndLocale(language.getBasename(), language.getLocale()) == null) {

            language = availableLanguageDao.save(language);

            if (language.isRequired()) {

                List<AvailableMessage> messages = availableMessageDao.findByBasename(language.getBasename());
                for (AvailableMessage message : messages) {
                    MessageTranslation t = new MessageTranslation(message, language, MessageStatus.NEW);
                    messageTranslationDao.save(t);
                }
            }
        }

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.i18n.service.MessageService#removeTranslationInfo(org.synyx.minos.i18n.domain.Message)
     */
    @Override
    @Transactional
    public void removeTranslationInfo(Message message) {

        AvailableMessage availableMessage =
                availableMessageDao.findByBasenameAndKey(message.getBasename(), message.getKey());
        AvailableLanguage availableLanguage =
                availableLanguageDao.findByBasenameAndLocale(message.getBasename(), message.getLocale());

        if (availableMessage == null || availableLanguage == null) {
            throw new IllegalStateException(
                    "Missing available Message and/or available Language entry for given message!");
        }

        messageTranslationDao.deleteBy(availableMessage, availableLanguage);
    }


    @Transactional
    @Override
    public void removeLanguage(String basename, LocaleWrapper locale) {

        AvailableLanguage language = availableLanguageDao.findByBasenameAndLocale(basename, locale);

        messageTranslationDao.deleteByAvailableLanguage(language);
        messageDao.deleteBy(language.getBasename(), language.getLocale());
        availableLanguageDao.delete(language);

    }


    public void setDefaultLocale(Locale defaultLocale) {

        this.defaultLocale = defaultLocale;
    }

}
