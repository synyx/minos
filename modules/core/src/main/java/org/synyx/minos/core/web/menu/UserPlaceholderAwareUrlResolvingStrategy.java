package org.synyx.minos.core.web.menu;

import org.apache.commons.lang.StringUtils;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;


/**
 * {@link UrlResolvingStrategy} that is able to detect a given placeholder in the configured URL and replaces it with
 * the current {@link User}s username.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class UserPlaceholderAwareUrlResolvingStrategy extends SimpleUrlResolvingStrategy {

    public static final String DEFAULT_PLACEHOLDER = "{username}";

    private final AuthenticationService authService;
    private String placeholder = DEFAULT_PLACEHOLDER;


    /**
     * Creates a new {@link UserPlaceholderAwareUrlResolvingStrategy}.
     * 
     * @param url
     * @param authService
     */
    public UserPlaceholderAwareUrlResolvingStrategy(String url, AuthenticationService authService) {

        super(url);
        Assert.notNull(authService);
        this.authService = authService;
    }


    /**
     * Configure the placeholder that should be replaced with the username. Defaults to {@value #DEFAULT_PLACEHOLDER}.
     * 
     * @param userPlaceholder the userPlaceholder to set
     */
    public void setPlaceholder(String userPlaceholder) {

        this.placeholder = StringUtils.isBlank(userPlaceholder) ? DEFAULT_PLACEHOLDER : userPlaceholder;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.SimpleUrlResolvingStrategy#resolveUrl(org.synyx.minos.core.web.menu.MenuItem)
     */
    @Override
    public String resolveUrl(MenuItem item) {

        String url = super.resolveUrl(item);

        User user = authService.getCurrentUser();
        String username = null == user ? "" : user.getUsername();

        return url.replace(placeholder, username).replace("//", "/");
    }
}
