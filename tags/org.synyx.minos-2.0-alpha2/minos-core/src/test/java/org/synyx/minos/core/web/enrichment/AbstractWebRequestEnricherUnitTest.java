package org.synyx.minos.core.web.enrichment;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.synyx.minos.core.web.enrichment.AbstractWebRequestEnricher;


/**
 * Unit test for {@link AbstractWebRequestEnricher}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class AbstractWebRequestEnricherUnitTest {

    private static final String PATH_PATTERN = "**/umt/users";


    @Test
    public void testname() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("http://foobar.de/minos/web/umt/users");

        SampleWebRequestEnricher enricher = new SampleWebRequestEnricher();
        assertTrue(enricher.supports(request));
    }

    private static class SampleWebRequestEnricher extends
            AbstractWebRequestEnricher {

        @Override
        protected String getPathPattern() {

            return PATH_PATTERN;
        }
    }
}
