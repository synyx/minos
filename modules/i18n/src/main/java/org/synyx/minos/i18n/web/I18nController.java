package org.synyx.minos.i18n.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.synyx.messagesource.Messages;
import org.synyx.messagesource.jdbc.JdbcMessageProvider;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.service.MessageService;


/**
 * Controller for i18n module
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Controller
public class I18nController {

    public static final String URL_MAIN = "/i18n";
    public static final String URL_TEST = "/i18n/test";
    public static final String URL_IMPORT = "/i18n/import";
    public static final String URL_EXPORT = "/i18n/export";

    public static final String URL_BASENAMES = "/i18n/basenames";
    public static final String URL_BASENAME = "/i18n/basenames/{basename}";
    public static final String URL_MESSAGES = "/i18n/basenames/{basename}/messages/{locale}";

    public static final String URL_MESSAGE = URL_MESSAGES + "/{key}/json";

    public static final String URL_REINITIALIZE = "/i18n/reinitialize";

    @Autowired
    private JdbcMessageProvider messageProvider;

    @Autowired
    private MessageService messageService;


    @InitBinder
    public void initBinder(DataBinder binder, Locale locale) {

        binder.registerCustomEditor(Locale.class, new DefaultLocaleEditor());
    }


    @RequestMapping(value = URL_MAIN, method = RequestMethod.GET)
    public String showOverview(Model model) {

        return "i18n/main";
    }


    @RequestMapping(value = URL_REINITIALIZE, method = RequestMethod.GET)
    public String reinitializeMessageSources(Model model) {

        messageService.initializeMessageSources();

        model.addAttribute(Core.MESSAGE, org.synyx.minos.core.web.Message.success("i18n.messagesources.reinitialized"));
        return UrlUtils.redirect(URL_MAIN);
    }


    @RequestMapping(value = URL_IMPORT, method = RequestMethod.GET)
    public String importMessages(Model model) {

        messageService.importMessages();

        model.addAttribute(Core.MESSAGE, org.synyx.minos.core.web.Message.success("i18n.messagesources.imported"));
        return UrlUtils.redirect(URL_MAIN);
    }


    @RequestMapping(value = URL_EXPORT, method = RequestMethod.GET)
    public String exportMessages(Model model) {

        // TODO export me

        // model.addAttribute(Core.MESSAGE, org.synyx.minos.core.web.Message.success("i18n.messagesources.exported"));

        throw new RuntimeException("Export is not yet implemented!");

        // return UrlUtils.redirect(URL_MAIN);
    }


    @RequestMapping(value = URL_BASENAMES, method = RequestMethod.GET)
    public String showBasenames(Model model) {

        Collection<String> basenames = messageService.getBasenames();
        model.addAttribute("basenames", basenames);

        return "i18n/basenames";
    }


    @RequestMapping(value = URL_BASENAME, method = RequestMethod.GET)
    public String showBasename(@PathVariable("basename") String basename, Model model) {

        List<LocaleInformation> locales = messageService.getLocaleInformations(basename);

        model.addAttribute("basename", basename);
        model.addAttribute("localeInformations", locales);

        return "i18n/basename";
    }


    @RequestMapping(value = URL_MESSAGES, method = RequestMethod.GET)
    public String showMessages(@PathVariable("basename") String basename, @PathVariable("locale") Locale locale,
            @RequestParam(value = "reference", required = false) Locale referenceLocale, Model model) {

        List<MessageView> messages = messageService.getMessages(basename, locale, referenceLocale);

        List<LocaleWrapper> locales = messageService.getLocales(basename);
        model.addAttribute("basename", basename);
        model.addAttribute("locale", new LocaleWrapper(locale));
        model.addAttribute("reference", new LocaleWrapper(referenceLocale));
        model.addAttribute("locales", locales);
        model.addAttribute("messages", messages);

        return "i18n/messages";
    }


    @RequestMapping(value = URL_MESSAGE, method = RequestMethod.GET)
    public void showMessage(@PathVariable("basename") String basename, @PathVariable("locale") Locale locale,
            @PathVariable("key") String key,
            @RequestParam(value = "reference", required = false) Locale referenceLocale, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        MessageView message = messageService.getMessage(basename, key, locale, referenceLocale);

        Map<String, String> jsonMap = new HashMap<String, String>();

        if (message != null) {
            jsonMap.put("id", message.getMessage().getId().toString());
            jsonMap.put("basename", message.getMessage().getBasename());
            jsonMap.put("language", message.getCurrentLocale().getLanguage());
            jsonMap.put("country", message.getCurrentLocale().getCountry());
            jsonMap.put("variant", message.getCurrentLocale().getVariant());
            jsonMap.put("key", message.getMessage().getKey());
            jsonMap.put("locale", message.getMessage().getLocale().toString());
            jsonMap.put("message", message.getMessage().getMessage());

            jsonMap.put("reference_message", message.getReference().getMessage().getMessage());
            jsonMap.put("reference_locale", message.getReference().getMessage().getLocale().toString());

            jsonMap.put("definedInCurrent", Boolean.toString(message.isDefinedInCurrent()));
        }

        MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
        jsonView.render(jsonMap, request, response);
    }


    @RequestMapping(value = URL_MESSAGES, method = RequestMethod.PUT)
    public String saveMessages(@PathVariable("basename") String basename, @PathVariable("locale") Locale locale,
            @ModelAttribute(value = "messages") MessagesView messages, Model model) {

        if (messages.getMessages() != null) {

            messageService.saveAll(messages.getMessages());
            model.addAttribute(Core.MESSAGE, org.synyx.minos.core.web.Message.success("i18n.messages.save.success"));

        }

        messageService.initializeMessageSources();
        return UrlUtils.redirect(URL_MESSAGES.replace("{basename}", basename).replace("{locale}",
                locale == null ? "" : locale.toString()));

    }


    @RequestMapping(value = URL_TEST, method = RequestMethod.GET)
    public String showTestPage(Model model) {

        Collection<String> basenames = messageProvider.getAvailableBaseNames();
        Map<String, Messages> messagesList = new HashMap<String, Messages>();
        for (String basename : basenames) {
            messagesList.put(basename, messageProvider.getMessages(basename));
        }

        model.addAttribute("availableMessages", messagesList);
        return "i18n/test";
    }

    public static class MessagesView {

        List<Message> messages = new ArrayList<Message>();


        public List<Message> getMessages() {

            return messages;
        }


        public void setMessages(List<Message> messages) {

            this.messages = messages;
        }

    }

}
