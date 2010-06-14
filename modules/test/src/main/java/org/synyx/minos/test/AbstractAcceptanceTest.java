package org.synyx.minos.test;

import java.util.Locale;

import junit.framework.AssertionFailedError;
import net.sourceforge.jwebunit.junit.WebTestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Abstract class for acceptance testing Minos sites. Setups JWebunit and provides some utility methods.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public abstract class AbstractAcceptanceTest extends WebTestCase {

    private static final String CONTEXT_PATH = "/minos";

    private static ApplicationContext applicationContext;

    private Locale locale = Locale.GERMAN;
    private MessageSource messageSource;


    @Override
    protected void setUp() throws Exception {

        getTestContext().setBaseUrl("http://127.0.0.1:" + System.getProperty("jetty.testport", "7070") + CONTEXT_PATH);
        getTestContext().setLocale(locale);

        messageSource = (MessageSource) lazyLoadAppContext().getBean("messageSource");
    }


    private synchronized static ApplicationContext lazyLoadAppContext() {

        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("classpath*:META-INF/minos/**/module-context.xml");
        }
        return applicationContext;
    }


    /**
     * Wrapper method for {@link MessageSource#getMessage(String, Object[], Locale)}
     */
    protected String translateMessage(String code, Object... args) {

        return messageSource.getMessage(code, args, getLocale());
    }


    protected void setLocale(Locale locale) {

        this.locale = locale;
    }


    protected Locale getLocale() {

        return locale;
    }


    /**
     * Clicks the button with the given title key.
     * 
     * @param code The title as a message source key
     */
    protected void clickLinkByTitle(String code) {

        clickLinkByTitleText(translateMessage(code));
    }


    /**
     * Clicks the button with the given title.
     * 
     * @param code The title as a string
     */
    protected void clickLinkByTitleText(String title) {

        clickElementByXPath("//a[@title='" + title + "']");
    }


    /**
     * Asserts if the static parts of a message source are contained by a given message. E.g. a message source
     * "Successfully saved user {0}!" will match a message "Successfully saved user joe!", if it doesn't match the
     * method throws an {@link AssertionFailedError}.
     */
    protected void assertMessageSource(String code, String message) {

        String messageSource = translateMessage(code);
        for (String messageSourcePart : messageSource.trim().split("\\{\\d{1,2}(,\\s?\\w+)?\\}")) {
            assertTrue(String.format("Expected text part not found in message source: [%s]\n"
                    + " Message source content was: [%s]", messageSourcePart, message), message.trim().contains(
                    messageSourcePart));
        }
    }
}
