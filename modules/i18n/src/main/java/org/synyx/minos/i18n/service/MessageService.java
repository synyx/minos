/**
 * 
 */
package org.synyx.minos.i18n.service;

import java.util.List;
import java.util.Locale;

import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.AvailableMessage;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.web.LocaleInformation;
import org.synyx.minos.i18n.web.MessageView;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageService {

    public List<MessageView> getMessages(String basename, Locale locale);


    public List<MessageView> getMessages(String basename, Locale locale, boolean includeNew, boolean includeUpdated);


    public MessageView getMessage(String basename, String key, Locale locale);


    public AvailableMessage getAvailableMessage(String basename, String key);


    public void saveAll(List<Message> messages);


    public void save(Message message);


    public List<String> getBasenames();


    public List<LocaleWrapper> getLocales(String basename);


    public List<LocaleInformation> getLocaleInformations(String basename);


    public void addLanguage(AvailableLanguage language);


    public void removeTranslationInfo(Message message);


    public void removeLanguage(String basename, LocaleWrapper localeWrapper);
}
