package org.synyx.minos.core.web;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.synyx.hades.domain.Order;
import org.synyx.hades.domain.Pageable;
import org.synyx.hades.domain.Sort;
import org.synyx.minos.core.web.PageableArgumentResolver;


/**
 * Unit test for {@link PageableArgumentResolver}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class PageableArgumentResolverUnitTest {

    private Method correctMethod;
    private Method failedMethod;
    private Method invalidQualifiers;

    private MockHttpServletRequest request;


    @Before
    public void setUp() throws SecurityException, NoSuchMethodException {

        correctMethod =
                SampleController.class.getMethod("correctMethod",
                        Pageable.class, Pageable.class);
        failedMethod =
                SampleController.class.getMethod("failedMethod",
                        Pageable.class, Pageable.class);
        invalidQualifiers =
                SampleController.class.getMethod("invalidQualifiers",
                        Pageable.class, Pageable.class);

        request = new MockHttpServletRequest();

        // Add pagination info for foo table
        request.addParameter("foo_page.size", "50");
        request.addParameter("foo_page.sort", "foo");
        request.addParameter("foo_page.sort.dir", "asc");

        // Add pagination info for bar table
        request.addParameter("bar_page.size", "60");
    }


    @Test
    public void testname() throws Exception {

        assertSizeForPrefix(50, new Sort(Order.ASCENDING, "foo"), 0);
        assertSizeForPrefix(60, null, 1);
    }


    @Test(expected = IllegalStateException.class)
    public void rejectsInvalidlyMappedPageables() throws Exception {

        MethodParameter parameter = new MethodParameter(failedMethod, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);

        new PageableArgumentResolver().resolveArgument(parameter, webRequest);
    }


    @Test(expected = IllegalStateException.class)
    public void rejectsInvalidQualifiers() throws Exception {

        MethodParameter parameter = new MethodParameter(invalidQualifiers, 0);
        NativeWebRequest webRequest = new ServletWebRequest(request);

        new PageableArgumentResolver().resolveArgument(parameter, webRequest);
    }


    private void assertSizeForPrefix(int size, Sort sort, int index)
            throws Exception {

        MethodParameter parameter = new MethodParameter(correctMethod, index);
        NativeWebRequest webRequest = new ServletWebRequest(request);

        PageableArgumentResolver resolver = new PageableArgumentResolver();

        Object argument = resolver.resolveArgument(parameter, webRequest);
        assertTrue(argument instanceof Pageable);

        Pageable pageable = (Pageable) argument;
        assertEquals(size, pageable.getPageSize());

        if (null != sort) {
            assertEquals(sort, pageable.getSort());
        }
    }

    @SuppressWarnings("unused")
    private class SampleController {

        public void correctMethod(@Qualifier("foo") Pageable first,
                @Qualifier("bar") Pageable second) {

        }


        public void failedMethod(Pageable first, Pageable second) {

        }


        public void invalidQualifiers(@Qualifier("foo") Pageable first,
                @Qualifier("foo") Pageable second) {

        }
    }
}
