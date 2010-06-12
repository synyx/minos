package org.synyx.minos.core.web.menu;

import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.User;


/**
 * Strategy that simply returns the url from a property.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class UserPlaceholderAwareUrlResolvingStrategy implements UrlResolvingStrategy {

    public static final String USER_PLACEHOLDER = "{username}";
    private String url;
    private AuthenticationService authService;


    public UserPlaceholderAwareUrlResolvingStrategy(String url, AuthenticationService authService) {

        this.authService = authService;
        this.url = url;
    }


    public String getUrl() {

        return url;
    }


    public void setUrl(String url) {

        this.url = url;
    }


    @Override
    public String resolveUrl(MenuItem item) {

        User user = getUser();
        String username = null == user ? "" : user.getUsername();
        return url.replace(USER_PLACEHOLDER, username).replace("//", "/");

    }


    private User getUser() {

        return authService.getCurrentUser();
    }

}
