package org.synyx.minos.core.web;

import static org.junit.Assert.*;
import static org.synyx.minos.TestConstants.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.synyx.hades.domain.Persistable;
import org.synyx.minos.core.web.UrlUtils;


/**
 * Unit test for {@link UrlUtils}
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UrlUtilsUnitTest {

    private MockHttpServletRequest request;
    private Map<String, Object> parameters;


    /**
     * Creates a {@link MockHttpServletRequest} as well as an empty parameters map.
     */
    @Before
    public void setUp() {

        request = new MockHttpServletRequest();
        parameters = new HashMap<String, Object>();
    }


    /**
     * Asserts that providing {@code null} as module raises an {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void preventsMissingModuleOnToUrl() {

        UrlUtils.toUrl(null, new MockHttpServletRequest());
    }


    /**
     * Asserts that providing {@code null} as request raises an {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void preventsMissingRequestOnToUrl() {

        UrlUtils.toUrl("", null);
    }


    /**
     * Asserts that an empty parameter map is handled correctly.
     */
    @Test
    public void doesNotAppendEmptyParameterMap() {

        assertEndsWith("module", UrlUtils.toUrl("module", request, parameters));
    }


    /**
     * Asserts that provided parameters get appended to the URL correctly.
     */
    @Test
    public void appendsParametersCorrectly() {

        parameters.put("foo", "bar");
        parameters.put("bar", "foo");

        assertEndsWith("module?foo=bar&bar=foo", UrlUtils.toUrl("module", request, parameters));
    }


    /**
     * Asserts that a {@code null} parameter map gets handled correctly by simply not applying it.
     */
    @Test
    public void doesNotAppendNullParameterMap() {

        assertEndsWith("module", UrlUtils.toUrl("module", request, (Map<String, Object>) null));
    }


    /**
     * Asserts that an entity id gets appended correctly to the URL.
     */
    @Test
    public void addsEntityIdCorrectly() {

        assertEndsWith("module?id=1", UrlUtils.toUrl("module", request, USER));
    }


    /**
     * Asserts that a {@code null} entity gets handled correctly.
     */
    @Test
    public void handlesNullEntityCorrectly() {

        assertEndsWith("module", UrlUtils.toUrl("module", request, (Persistable<?>) null));
    }


    private static void assertEndsWith(String expected, String actual) {

        if (null == expected || null == actual) {
            fail("Cannot test null values!");
        }

        assertTrue(expected.endsWith(expected));
    }
}
