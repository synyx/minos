package org.synyx.minos.core.message;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.internal.MinosModule;


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

        List<ModuleMessageSource> registry = new ArrayList<ModuleMessageSource>();

        Module first = new MinosModule("foo");

        MinosModule second = new MinosModule("foo");
        second.setDependsOn(Arrays.asList(first));

        Module third = new MinosModule("bar");

        registry.add(getMessageSource(first, "NewFooMessage", 100));
        registry.add(getMessageSource(second, "FooMessage", 0));
        registry.add(getMessageSource(third, "BarMessage", 0));

        dispatcher.setSources(registry);

        this.dispatcher = dispatcher;
    }


    @Test
    public void resolvesMessagesCorrectly() throws Exception {

        // Expect "FooMessage" as it has higher precendece
        assertEquals("FooMessage", dispatcher.getMessage("foo.hust", null, null));
        assertEquals("BarMessage", dispatcher.getMessage("bar.hust", null, null));
    }


    @Test(expected = NoSuchMessageException.class)
    public void throwsMessageNotFoundExceptionIfNoMessageFound() throws Exception {

        dispatcher.getMessage("foobar.hust", null, null);
    }


    /**
     * Returns a {@link MessageSourcePlugin} that statically returns the given message for codes prefixed with the given
     * prefix.
     * 
     * @param prefix
     * @param message
     * @param order
     * @return
     */
    private ModuleMessageSource getMessageSource(Module module, final String message, int order) {

        ModuleMessageSource source = new ModuleMessageSourceImpl(module) {

            @Override
            protected String getMessageInternal(String code, Object[] args, Locale locale) {

                return message;
            }
        };

        return source;
    }
}
