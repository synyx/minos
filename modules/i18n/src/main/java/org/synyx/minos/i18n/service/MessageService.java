/**
 *
 */
package org.synyx.minos.i18n.service;

import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.AvailableMessage;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.web.LocaleInformation;
import org.synyx.minos.i18n.web.MessageView;

import java.util.List;
import java.util.Locale;
import java.util.Properties;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageService {

    /**
     * Returns all messages for the given basename and {@link Locale} as {@link MessageView}
     *
     * @param basename the basename to return messages for
     * @param locale the {@link Locale} to return messages for
     * @return all messages matching the given criteria
     */
    List<MessageView> getMessages(String basename, Locale locale);


    /**
     * Returns messages for the given basename and {@link Locale} as {@link MessageView} filtered.
     *
     * @param basename the basename to return messages for
     * @param locale the {@link Locale} to return messages for
     * @param includeNew if set to true messages with existing "new-translation" are included into the result
     * @param includeUpdated if set to true messages with existing "updated-translation" are included into the result
     * @return all messages matching the given criteria
     */
    List<MessageView> getMessages(String basename, Locale locale, boolean includeNew, boolean includeUpdated);


    /**
     * Returns a {@link MessageView} matching the given criteria
     *
     * @param basename the basename of the message
     * @param key the key of the message
     * @param locale the {@link Locale} of the message
     * @return a {@link MessageView} matching the given criteria or null
     */
    MessageView getMessage(String basename, String key, Locale locale);


    /**
     * Returns the {@link AvailableMessage} matching the given basename and key
     *
     * @param basename the basename of the {@link AvailableMessage}
     * @param key the key of the {@link AvailableMessage}
     * @return the {@link AvailableMessage} or null
     */
    AvailableMessage getAvailableMessage(String basename, String key);


    /**
     * Save the given message. If finished is set to true the translation information for the message gets deleted if
     * available.
     *
     * @param message the {@link Message} to save
     * @param finished if set to true mark the Message as "translated" (SYNC)
     */
    void save(Message message, boolean finished);


    /**
     * Returns all available Basenames.
     *
     * @return a {@link List} of Basenames
     */
    List<String> getBasenames();


    /**
     * Returns all available langauges represented as {@link LocaleWrapper} for a given basename
     *
     * @param basename
     * @return a {@link List} of {@link LocaleWrapper}
     */
    List<LocaleWrapper> getLocales(String basename);


    /**
     * Returns all available languages represented as {@link LocaleInformation} for a given basename
     *
     * @param basename
     * @return a list of {@link LocaleInformation}
     */
    List<LocaleInformation> getLocaleInformations(String basename);


    /**
     * Adds a new language
     *
     * @param language the {@link AvailableLanguage} to add
     */
    void addLanguage(AvailableLanguage language);


    /**
     * Removes the translation-information for a given {@link Message}
     *
     * @param message the message to remove translation-information for
     */
    void removeTranslationInfo(Message message);


    /**
     * Remove a given Language
     *
     * @param basename
     * @param localeWrapper
     */
    void removeLanguage(String basename, LocaleWrapper localeWrapper);


    /**
     * Batch-Import a List of Key=Value Pairs for a given {@link AvailableLanguage}. This omits keys not "known" as
     * {@link AvailableMessage}, updates existing keys and creates new ones.
     *
     * @param language the {@link AvailableLanguage} there the {@link Properties} should be imported for
     * @param p the {@link Properties} instance holding all keys and values to import.
     */
    void saveAll(AvailableLanguage language, Properties p);
}
