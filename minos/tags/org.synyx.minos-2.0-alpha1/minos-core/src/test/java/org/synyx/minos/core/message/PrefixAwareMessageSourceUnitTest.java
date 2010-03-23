package org.synyx.minos.core.message;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link PrefixAwareMessageSource}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class PrefixAwareMessageSourceUnitTest {

    private PrefixAwareMessageSource messageSource;


    @Before
    public void setUp() {

        this.messageSource = new PrefixAwareMessageSource();
    }


    @Test
    public void supportsAnyPrefixIfNoneConfigured() throws Exception {

        assertThat(messageSource.supports("foo"), is(true));
        assertThat(messageSource.supports("bar"), is(true));
    }


    @Test
    public void supportsConfiguredPrefixes() throws Exception {

        messageSource.setPrefixes(Arrays.asList("foo", "bar"));

        assertThat(messageSource.supports("foo"), is(true));
        assertThat(messageSource.supports("bar"), is(true));

        assertThat(messageSource.supports("acme"), is(false));
    }


    @Test
    public void supportsAllPrefixesAfterResetting() throws Exception {

        supportsConfiguredPrefixes();

        messageSource.setPrefixes(null);
        assertThat(messageSource.supports("acme"), is(true));
    }


    @Test
    public void settingNullAsPrefixResetsMessageSource() throws Exception {

        supportsConfiguredPrefixes();

        messageSource.setPrefix(null);
        assertThat(messageSource.supports("acme"), is(true));
    }
}
