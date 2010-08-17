/**
 * 
 */
package org.synyx.minos.i18n.service;

import java.util.List;
import java.util.Locale;

import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.web.MessageView;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MessageService {

    public List<MessageView> getMessages(String basename, Locale locale);


    public List<MessageView> getMessages(String basename, Locale locale, Locale referenceLocale);


    public void saveAll(List<Message> messages);


    public void initializeMessageSources();


    public List<String> getBasenames();


    public List<LocaleWrapper> getLocales(String basename);


    public void importMessages();
}
