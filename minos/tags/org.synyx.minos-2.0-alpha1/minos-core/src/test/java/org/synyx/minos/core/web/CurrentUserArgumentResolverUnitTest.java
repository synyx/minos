package org.synyx.minos.core.web;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.CurrentUser;
import org.synyx.minos.core.web.CurrentUserArgumentResolver;



/**
 * Unit test for {@link CurrentUserArgumentResolver}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class CurrentUserArgumentResolverUnitTest {

    private CurrentUserArgumentResolver resolver;
    private AuthenticationService authenticationService;

    private Method succeedingMethod;
    private Method failingMethod;
    private NativeWebRequest request;

    private static final User USER =
            new User("username", "emailAddress", "password");


    @Before
    public void setUp() throws SecurityException, NoSuchMethodException {

        authenticationService = createNiceMock(AuthenticationService.class);
        resolver = new CurrentUserArgumentResolver(authenticationService);

        succeedingMethod =
                Foo.class.getMethod("currentUserAware", User.class,
                        String.class);
        failingMethod =
                Foo.class.getMethod("userFromModelAware", User.class,
                        Role.class);
        USER.setId(1L);

        request = new ServletWebRequest(new MockHttpServletRequest());
    }


    @Test
    public void recognizesUserArgumentCorrectly() throws Exception {

        expect(authenticationService.getCurrentUser()).andReturn(USER);
        replay(authenticationService);

        MethodParameter parameter = new MethodParameter(succeedingMethod, 0);

        Object result = resolver.resolveArgument(parameter, request);
        assertTrue(result instanceof User);
        assertEquals(USER, (User) result);
    }


    @Test
    public void doesNotResolveUnannotatedUser() throws Exception {

        replay(authenticationService);

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
