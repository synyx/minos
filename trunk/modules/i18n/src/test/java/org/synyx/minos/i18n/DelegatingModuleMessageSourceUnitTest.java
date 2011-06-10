package org.synyx.minos.i18n;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.synyx.minos.core.module.Module;

import java.util.Locale;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DelegatingModuleMessageSourceUnitTest {

    private static final String MESSAGE = "message";
    private static final String MODULE_IDENTIFIER = "ModuleIdentifier";
    private static final String WRONG_IDENTIFIER = "NoIdentifier";

    private DelegatingModuleMessageSource delegatingModuleMessageSource;

    @Mock
    private Module module;

    @Mock
    private MessageSource messageSource;


    @Before
    public void setUp() {

        module = mock(Module.class);
        messageSource = mock(MessageSource.class);

        delegatingModuleMessageSource = new DelegatingModuleMessageSource(module, messageSource);

    }


    @Test
    public void testGetMessage() {

        MessageSourceResolvable messageSourceResolvable = mock(MessageSourceResolvable.class);

        when(messageSource.getMessage(Matchers.<MessageSourceResolvable>anyObject(), Matchers.<Locale>anyObject()))
                .thenReturn(MESSAGE);
        when(messageSource.getMessage(anyString(), Matchers.<Object[]>anyObject(), Matchers.<Locale>anyObject()))
                .thenReturn(MESSAGE);
        when(messageSource.getMessage(anyString(), Matchers.<Object[]>anyObject(), anyString(),
                Matchers.<Locale>anyObject())).thenReturn(MESSAGE);

        Assert.assertEquals(MESSAGE, delegatingModuleMessageSource.getMessage(messageSourceResolvable, Locale.ROOT));
        Assert.assertEquals(MESSAGE, delegatingModuleMessageSource.getMessage("", new Object[] {}, Locale.ROOT));
        Assert.assertEquals(MESSAGE, delegatingModuleMessageSource.getMessage("", new Object[] {}, "", Locale.ROOT));
    }


    @Test
    public void testSupports() {
        when(module.getIdentifier()).thenReturn(MODULE_IDENTIFIER);

        Assert.assertTrue(delegatingModuleMessageSource.supports(MODULE_IDENTIFIER));
        Assert.assertFalse(delegatingModuleMessageSource.supports(WRONG_IDENTIFIER));
    }


    @Test
    public void testLenient() {
        when(module.getIdentifier()).thenReturn("");

        Assert.assertFalse(delegatingModuleMessageSource.supports(WRONG_IDENTIFIER));

        delegatingModuleMessageSource.setLenient(true);
        Assert.assertTrue(delegatingModuleMessageSource.supports(WRONG_IDENTIFIER));
    }


    @Test
    public void testGetModule() {
        Assert.assertEquals(module, delegatingModuleMessageSource.getModule());
    }
}
