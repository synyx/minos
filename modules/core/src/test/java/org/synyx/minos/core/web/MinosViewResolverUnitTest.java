package org.synyx.minos.core.web;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;
import org.springframework.web.servlet.ViewResolver;
import org.synyx.minos.core.web.MinosViewResolver.ServletMappingAwareRedirectView;


/**
 * Unit test for {@link MinosViewResolver}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosViewResolverUnitTest {

    @Test
    public void usesCustomRedirectViewForRedirectPrefix() throws Exception {

        ViewResolver viewResolver = new MinosViewResolver();

        assertThat(
                viewResolver.resolveViewName("redirect:/foo", Locale.GERMAN),
                is(instanceOf(ServletMappingAwareRedirectView.class)));
    }
}
