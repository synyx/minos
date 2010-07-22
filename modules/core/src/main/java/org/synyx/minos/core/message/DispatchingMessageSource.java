package org.synyx.minos.core.message;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import org.synyx.hera.core.OrderAwarePluginRegistry;
import org.synyx.hera.core.PluginRegistry;
import org.synyx.minos.core.module.internal.ModuleAwareComparator;


/**
 * MessageSource implementation that delegates to a list of sources by the messages prefix. If a message starts with
 * {@code something.} this delegates to a MinosMessageSource that returns something on getPrefix()
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke - gierke@synyx.de
 */
public class DispatchingMessageSource extends AbstractMessageSource {

    private PluginRegistry<ModuleMessageSource, String> sources = OrderAwarePluginRegistry.create();


    /**
     * Inject all {@link ModuleMessageSource}es available in the system.
     * 
     * @param sources
     */
    public void setSources(List<ModuleMessageSource> sources) {

        this.sources = OrderAwarePluginRegistry.create(sources, new ModuleAwareComparator());
    }


    /**
     * Returns the prefix (everything until the first occurence of .
     * 
     * @param code the code to retuorn the prefix for
     * @return the prefix or the whole code if no dot exists
     */
    private String getPrefixFromCode(String code) {

        int firstDotPosition = code.indexOf(".");

        return firstDotPosition > 0 ? code.substring(0, firstDotPosition) : code;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.support.AbstractMessageSource#resolveCode (java.lang.String, java.util.Locale)
     */
    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {

        List<ModuleMessageSource> candidates = sources.getPluginsFor(getPrefixFromCode(code));

        // No message source at all found
        if (candidates.isEmpty()) {
            return null;
        }

        for (MessageSourcePlugin source : candidates) {

            MessageFormat format = resolveMessageWithSource(source, code, locale);

            if (null != format) {
                return format;
            }
        }

        return null;
    }


    /**
     * Resolves the message with the given {@link MessageSource}. Returns {@literal null} if no message could be
     * resolved or the code was returned itself.
     * 
     * @param source
     * @param code
     * @param locale
     */
    private MessageFormat resolveMessageWithSource(MessageSource source, String code, Locale locale) {

        try {
            String message = source.getMessage(code, null, locale);

            if (!code.equals(message)) {
                return createMessageFormat(message, locale);
            }

        } catch (NoSuchMessageException e) {

        }

        return null;
    }
}
