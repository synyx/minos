package org.synyx.minos.core.authentication;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.security.MinosUserDetails;


/**
 * Unit test for {@link MappingAuthenticationSuccessHandler}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MappingAuthenticationHandlerAdapterUnitTest {

    private static final String USER_VIEW = "user.jsp";
    private static final String ADMIN_VIEW = "admin.jsp";
    private static final String DEFAULT_VIEW = "/default";

    private MappingAuthenticationSuccessHandler handler;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private RedirectStrategy strategy;

    @Mock
    private Authentication authentication;
    @Mock
    private User user;


    @Before
    public void setUp() {

        when(authentication.getPrincipal()).thenReturn(new MinosUserDetails(user));

        Map<String, String[]> mappings = new HashMap<String, String[]>();
        mappings.put(ADMIN_VIEW, new String[] { "ADMIN", "FOO" });
        mappings.put(USER_VIEW, new String[] { "USER", "BAR" });

        handler = new MappingAuthenticationSuccessHandler();
        handler.setMappings(mappings);
        handler.setRedirectStrategy(strategy);
        handler.setDefaultTargetUrl(DEFAULT_VIEW);
    }


    @Test
    public void redirectsToAdminPageForAdmin() throws Exception {

        assertViewForRole(ADMIN_VIEW, "ADMIN");
    }


    @Test
    public void redirectsToAdminPageForFoo() throws Exception {

        assertViewForRole(ADMIN_VIEW, "FOO");
    }


    @Test
    public void redirectsToUserPageForUser() throws Exception {

        assertViewForRole(USER_VIEW, "USER");
    }


    @Test
    public void redirectsToUserPageForBar() throws Exception {

        assertViewForRole(USER_VIEW, "BAR");
    }


    @Test
    public void redirectsToDefaultPageForUnmappedRole() throws Exception {

        assertViewForRole(DEFAULT_VIEW, "FOOBAR");
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsDuplicateRoleMapping() throws Exception {

        Map<String, String[]> mappings = new HashMap<String, String[]>();
        mappings.put(ADMIN_VIEW, new String[] { "ADMIN" });
        mappings.put(USER_VIEW, new String[] { "ADMIN" });

        MappingAuthenticationSuccessHandler handler = new MappingAuthenticationSuccessHandler();
        handler.setMappings(mappings);
    }


    private void assertViewForRole(String view, String role) throws ServletException, IOException {

        when(user.hasRole(role)).thenReturn(true);

        handler.onAuthenticationSuccess(request, response, authentication);

        verify(strategy).sendRedirect(request, response, view);
    }
}
