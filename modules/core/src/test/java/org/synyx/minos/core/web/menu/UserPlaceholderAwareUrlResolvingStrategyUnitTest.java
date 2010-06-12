package org.synyx.minos.core.web.menu;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.User;


public class UserPlaceholderAwareUrlResolvingStrategyUnitTest {

    @Test
    public void replacesUserCorrectly() throws Exception {

        AuthenticationService authService = Mockito.mock(AuthenticationService.class);
        User user = new User("oliver.gierke", "gierke@synyx.de", "password");
        when(authService.getCurrentUser()).thenReturn(user);

        String url = String.format("/foo/%s/resume", UserPlaceholderAwareUrlResolvingStrategy.USER_PLACEHOLDER);
        UserPlaceholderAwareUrlResolvingStrategy strategy =
                new UserPlaceholderAwareUrlResolvingStrategy(url, authService);

        assertEquals("/foo/oliver.gierke/resume", strategy.resolveUrl(null));
    }


    @Test
    public void removesPlaceholderCorrectlyIfNoUserProvided() throws Exception {

        AuthenticationService authService = Mockito.mock(AuthenticationService.class);
        when(authService.getCurrentUser()).thenReturn(null);
        String url = String.format("/foo/%s/resume", UserPlaceholderAwareUrlResolvingStrategy.USER_PLACEHOLDER);
        UserPlaceholderAwareUrlResolvingStrategy strategy =
                new UserPlaceholderAwareUrlResolvingStrategy(url, authService);

        assertEquals("/foo/resume", strategy.resolveUrl(null));
    }

}
