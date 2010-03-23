package org.synyx.minos.core.message;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.synyx.hera.core.MutablePluginRegistry;
import org.synyx.hera.core.OrderAwarePluginRegistry;


/**
 * Unit test for {@link DispatchingMessageSource}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class DispatchingMessageSourceUnitTest {

    private MessageSource dispatcher;


    @Before
    public void setUp() {

        DispatchingMessageSource dispatcher = new DispatchingMessageSource();

        MutablePluginRegistry<MessageSourcePlugin, String> registry =
                OrderAwarePluginRegistry.create();

        registry.addPlugin(getMessageSource("foo", "NewFooMessage", 100));
        registry.addPlugin(getMessageSource("foo", "FooMessage", 0));
        registry.addPlugin(getMessageSource("bar", "BarMessage", 0));

        dispatcher.setSources(registry);

        this.dispatcher = dispatcher;
    }


    @Test
    public void resolvesMessagesCorrectly() throws Exception {

        // Expect "FooMessage" as it has higher precendece
        assertEquals("FooMessage", dispatcher
                .getMessage("foo.hust", null, null));
        assertEquals("BarMessage", dispatcher
                .getMessage("bar.hust", null, null));
    }


    @Test(expected = NoSuchMessageException.class)
    public void throwsMessageNotFoundExceptionIfNoMessageFound()
            throws Exception {

        dispatcher.getMessage("foobar.hust", null, null);
    }


    /**
     * Returns a {@link MessageSourcePlugin} that statically returns the given
     * message for codes prefixed with the given prefix.
     * 
     * @param prefix
     * @param message
     * @param order
     * @return
     */
    private MessageSourcePlugin getMessageSource(String prefix,
            final String message, int order) {

        PrefixAwareMessageSource source = new PrefixAwareMessageSource() {

            @Override
            protected String getMessageInternal(String code, Object[] args,
                    Locale locale) {

                return message;
            }
        };

        source.setPrefix(prefix);
        source.setOrder(order);

        return source;
    }
}
