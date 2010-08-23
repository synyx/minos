package org.synyx.minos.i18n.web;

import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
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
import org.synyx.messagesource.jdbc.JdbcMessageProvider;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.i18n.domain.AvailableLanguage;
import org.synyx.minos.i18n.domain.AvailableMessage;
import org.synyx.minos.i18n.domain.LocaleWrapper;
import org.synyx.minos.i18n.domain.Message;
import org.synyx.minos.i18n.service.MessageService;
import org.synyx.minos.i18n.service.MessageTransferService;


/**
 * Controller for i18n module
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Controller
public class I18nController {

    public static final String URL_MAIN = "/i18n";
    public static final String URL_IMPORT = "/i18n/import";
    public static final String URL_EXPORT = "/i18n/export";

    public static final String URL_BASENAMES = "/i18n/basenames";
    public static final String URL_BASENAME = "/i18n/basenames/{basename}";
    public static final String URL_MESSAGES = "/i18n/basenames/{basename}/messages/{locale}";

    public static final String URL_MESSAGE_JSON = URL_MESSAGES + "/{key}/json";

    public static final String URL_REINITIALIZE = "/i18n/reinitialize";

    @Autowired
    private JdbcMessageProvider messageProvider;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageTransferService messageTransferService;


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

        messageTransferService.initializeMessageSources();

        model.addAttribute(Core.MESSAGE, org.synyx.minos.core.web.Message.success("i18n.messagesources.reinitialized"));
        return UrlUtils.redirect(URL_MAIN);
    }


    @RequestMapping(value = URL_IMPORT, method = RequestMethod.GET)
    public String importMessages(Model model) {

        messageTransferService.importMessages();

        model.addAttribute(Core.MESSAGE, org.synyx.minos.core.web.Message.success("i18n.messagesources.imported"));
        return UrlUtils.redirect(URL_MAIN);
    }


    @RequestMapping(value = URL_EXPORT, method = RequestMethod.GET)
    public void exportMessages(HttpServletResponse response, OutputStream out) {

        response.setContentType("application/zip");
        String filename = "export_i18n_" + new DateTime().toString("yyyy-MM-dd_HH-mm") + ".zip";
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));

        messageTransferService.exportMessages(out);

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
        model.addAttribute("newLanguage", new AvailableLanguage(LocaleWrapper.DEFAULT, basename, true));
        return "i18n/basename";
    }


    @RequestMapping(value = URL_BASENAME, method = RequestMethod.POST)
    public String addNewLanguageForBasename(@PathVariable("basename") String basename,
            @ModelAttribute("newLanguage") AvailableLanguage language, Model model) {

        List<LocaleWrapper> locales = messageService.getLocales(basename);

        if (!locales.contains(language.getLocale())) {
            // todo make boolean configurable
            messageService.addLanguage(language);
            model.addAttribute(Core.MESSAGE,
                    org.synyx.minos.core.web.Message.success("i18n.messages.newlanguage.success"));
        } else {
            model.addAttribute(Core.MESSAGE,
                    org.synyx.minos.core.web.Message.notice("i18n.messages.newlanguage.alreadyexists"));
        }

        return UrlUtils.redirect(URL_BASENAME.replace("{basename}", basename));
    }


    @RequestMapping(value = URL_MESSAGES + "/deleteconfirmation", method = RequestMethod.GET)
    public String showConfirmationForRemoveLanguageForBasename(@PathVariable("basename") String basename,
            @PathVariable("locale") Locale locale, Model model) {

        model.addAttribute("locale", locale);
        model.addAttribute("basename", basename);

        return "i18n/languagedeleteconfirmation";
    }


    @RequestMapping(value = URL_MESSAGES, method = RequestMethod.DELETE)
    public String removeLanguageForBasename(@PathVariable("basename") String basename,
            @PathVariable("locale") Locale locale, Model model) {

        LocaleWrapper lang = new LocaleWrapper(locale);
        if (!lang.isDefault()) {
            messageService.removeLanguage(basename, lang);

            model.addAttribute(Core.MESSAGE,
                    org.synyx.minos.core.web.Message.success("i18n.basename.deleteLanguage.message.success"));

        } else {
            model.addAttribute(Core.MESSAGE, org.synyx.minos.core.web.Message
                    .error("i18n.basename.deleteLanguage.message.impossiblebecauseofdefault"));

        }

        return UrlUtils.redirect(URL_BASENAME.replace("{basename}", basename));
    }


    @RequestMapping(value = URL_MESSAGES, method = RequestMethod.GET)
    public String showMessages(@PathVariable("basename") String basename, @PathVariable("locale") Locale locale,
            @RequestParam(value = "reference", required = false) Locale referenceLocale, Model model) {

        List<MessageView> messages = messageService.getMessages(basename, locale);

        List<LocaleWrapper> locales = messageService.getLocales(basename);
        model.addAttribute("basename", basename);
        model.addAttribute("locale", new LocaleWrapper(locale));
        model.addAttribute("reference", new LocaleWrapper(referenceLocale));
        model.addAttribute("locales", locales);
        model.addAttribute("messages", messages);

        return "i18n/messages";
    }


    @RequestMapping(value = URL_MESSAGE_JSON, method = RequestMethod.GET)
    public void showMessage(@PathVariable("basename") String basename, @PathVariable("locale") Locale locale,
            @PathVariable("key") String key,
            @RequestParam(value = "reference", required = false) Locale referenceLocale, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        MessageView message = messageService.getMessage(basename, key, locale);
        MessageView messageRef = messageService.getMessage(basename, key, referenceLocale);
        AvailableMessage availableMessage = messageService.getAvailableMessage(basename, key);

        Map<String, String> jsonMap = new HashMap<String, String>();

        if (message != null) {

            // MESSAGE
            if (message.getMessage().getId() != null) {
                jsonMap.put("id", message.getMessage().getId().toString());
            } else {
                jsonMap.put("id", "");
            }

            jsonMap.put("basename", message.getMessage().getBasename());
            jsonMap.put("language", message.getMessage().getLocale().getLanguage());
            jsonMap.put("country", message.getMessage().getLocale().getCountry());
            jsonMap.put("variant", message.getMessage().getLocale().getVariant());
            jsonMap.put("key", message.getMessage().getKey());
            jsonMap.put("message", message.getMessage().getMessage());

            if (message.getTranslation() != null) {
                jsonMap.put("status", message.getTranslation().getMessageStatus().toString());
            }

            jsonMap.put("locale", message.getResolvingLocale().toString());
            jsonMap.put("resolved", Boolean.toString(message.isMessageResolved()));

            // REFERENCE MESSAGE
            jsonMap.put("reference_message", messageRef.getMessage().getMessage());
            jsonMap.put("reference_locale", messageRef.getResolvingLocale().toString());
            if (messageRef.getTranslation() != null) {
                jsonMap.put("reference_status", messageRef.getTranslation().getMessageStatus().toString());
            } else {
                jsonMap.put("reference_status", "SYNC");
            }

            jsonMap.put("reference_locale", messageRef.getResolvingLocale().toString());

            // AVAILABLE MESSAGE
            jsonMap.put("available_message", availableMessage.getMessage());
        }

        MappingJacksonJsonView jsonView = new MappingJacksonJsonView();
        jsonView.render(jsonMap, request, response);
    }


    @RequestMapping(value = URL_MESSAGE_JSON, method = RequestMethod.PUT)
    public void saveMessage(@PathVariable("basename") String basename, @PathVariable("locale") Locale locale,
            @PathVariable("key") String key,
            @RequestParam(value = "reference", required = false) Locale referenceLocale,
            @RequestParam(value = "finished", required = false, defaultValue = "FALSE") Boolean finished,
            @ModelAttribute(value = "message") Message message, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        messageService.save(message);

        if (finished) {
            messageService.removeTranslationInfo(message);
        }

        showMessage(basename, locale, key, referenceLocale, request, response);
    }

}
