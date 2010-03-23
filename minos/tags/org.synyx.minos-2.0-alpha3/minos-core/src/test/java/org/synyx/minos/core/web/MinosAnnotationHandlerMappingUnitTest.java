package org.synyx.minos.core.web;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link MinosAnnotationHandlerMapping}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosAnnotationHandlerMappingUnitTest {

    private MinosAnnotationHandlerMapping mapping;
    private Set<String> urls;


    @Before
    public void setUp() {

        mapping = new MinosAnnotationHandlerMapping();
        urls = new HashSet<String>();
    }


    @Test
    public void addsTrailingSlashAndSuffixMapping() throws Exception {

        mapping.addUrlsForPath(urls, "/user");

        assertTrue(urls.contains("/user"));
        assertTrue(urls.contains("/user/"));
        assertTrue(urls.contains("/user.*"));
    }


    @Test
    public void dropsSuffixMappingsIfConfigured() throws Exception {

        mapping.setUseDefaultSuffixPattern(false);

        mapping.addUrlsForPath(urls, "/user");

        assertTrue(urls.contains("/user"));
        assertTrue(urls.contains("/user/"));
        assertFalse(urls.contains("/user.*"));
    }
}
