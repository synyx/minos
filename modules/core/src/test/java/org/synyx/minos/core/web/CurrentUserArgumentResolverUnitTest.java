package org.synyx.minos.core.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.security.AuthenticationService;


/**
 * Unit test for {@link CurrentUserArgumentResolver}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrentUserArgumentResolverUnitTest {

    private CurrentUserArgumentResolver resolver;
    @Mock
    private AuthenticationService authenticationService;

    private Method succeedingMethod;
    private Method failingMethod;
    private NativeWebRequest request;

    private static final User USER = new User("username", "emailAddress", "password");


    @Before
    public void setUp() throws SecurityException, NoSuchMethodException {

        resolver = new CurrentUserArgumentResolver(authenticationService);

        succeedingMethod = Foo.class.getMethod("currentUserAware", User.class, String.class);
        failingMethod = Foo.class.getMethod("userFromModelAware", User.class, Role.class);
        USER.setId(1L);

        request = new ServletWebRequest(new MockHttpServletRequest());
    }


    @Test
    public void recognizesUserArgumentCorrectly() throws Exception {

        when(authenticationService.getCurrentUser()).thenReturn(USER);

        MethodParameter parameter = new MethodParameter(succeedingMethod, 0);

        Object result = resolver.resolveArgument(parameter, request);
        assertTrue(result instanceof User);
        assertEquals(USER, result);
    }


    @Test
    public void doesNotResolveUnannotatedUser() throws Exception {

        MethodParameter parameter = new MethodParameter(failingMethod, 0);

        Object result = resolver.resolveArgument(parameter, request);
        assertEquals(WebArgumentResolver.UNRESOLVED, result);
    }

    @SuppressWarnings("unused")
    private class Foo {

        public void currentUserAware(@CurrentUser User user, String something) {

        }


        public void userFromModelAware(User user, Role role) {

        }
    }
}
