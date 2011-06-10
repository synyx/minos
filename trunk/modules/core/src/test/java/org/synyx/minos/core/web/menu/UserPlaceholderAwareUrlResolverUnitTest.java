package org.synyx.minos.core.web.menu;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.security.AuthenticationService;


public class UserPlaceholderAwareUrlResolverUnitTest {

    @Test
    public void replacesUserCorrectly() throws Exception {

        AuthenticationService authService = Mockito.mock(AuthenticationService.class);
        User user = new User("oliver.gierke", "gierke@synyx.de", "password");
        when(authService.getCurrentUser()).thenReturn(user);

        String url = String.format("/foo/%s/resume", UserPlaceholderAwareUrlResolver.DEFAULT_PLACEHOLDER);
        UserPlaceholderAwareUrlResolver strategy = new UserPlaceholderAwareUrlResolver(url, authService);

        assertEquals("/foo/oliver.gierke/resume", strategy.resolveUrl(null));
    }


    @Test
    public void removesPlaceholderCorrectlyIfNoUserProvided() throws Exception {

        AuthenticationService authService = Mockito.mock(AuthenticationService.class);
        when(authService.getCurrentUser()).thenReturn(null);
        String url = String.format("/foo/%s/resume", UserPlaceholderAwareUrlResolver.DEFAULT_PLACEHOLDER);
        UserPlaceholderAwareUrlResolver strategy = new UserPlaceholderAwareUrlResolver(url, authService);

        assertEquals("/foo/resume", strategy.resolveUrl(null));
    }

}
