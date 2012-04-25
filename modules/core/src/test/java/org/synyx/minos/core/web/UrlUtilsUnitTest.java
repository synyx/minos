package org.synyx.minos.core.web;

import static org.junit.Assert.*;
import static org.synyx.minos.TestConstants.*;

import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.synyx.hades.domain.Persistable;
import org.synyx.minos.core.web.UrlUtils;


/**
 * Unit test for {@link UrlUtils}
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Aljona Murygina - murygina@synyx.de
 */
public class UrlUtilsUnitTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    /**
     * Creates a {@link MockHttpServletRequest} as well as an empty parameters map.
     */
    @Before
    public void setUp() {

        request = new MockHttpServletRequest();
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
    
    /**
     * Test for UrlUtils method markCreated
     */
    @Test
    public void testMarkCreated() {
        
        response = new MockHttpServletResponse();
        
        UrlUtils.markCreated("module", request, response, USER);
        
        assertTrue(response.getStatus() == HttpServletResponse.SC_CREATED);
    }


    private static void assertEndsWith(String expected, String actual) {

        if (null == expected || null == actual) {
            fail("Cannot test null values!");
        }

        assertTrue(expected.endsWith(expected));
    }
}
