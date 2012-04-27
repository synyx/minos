package org.synyx.minos.core.web.menu.filter;

import org.synyx.minos.core.security.AuthenticationService;
import org.synyx.minos.util.Assert;

import org.synyx.tagsupport.tags.menu.Menu;
import org.synyx.tagsupport.tags.menu.MenuItemFilter;


/**
 * {@link MenuItemFilter} that that considers the permissions registered with the
 * {@link org.synyx.tagsupport.tags.menu.MenuItem}.
 *
 * @author  Marc Kannegiesser - kannegiesser@synyx.de
 * @author  Oliver Gierke
 */
public class PermissionMenuItemFilter implements MenuItemFilter {

    private final AuthenticationService authenticationService;

    public PermissionMenuItemFilter(AuthenticationService authenticationService) {

        Assert.notNull(authenticationService);
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean apply(Menu input) {

        return authenticationService.hasAllPermissions(input.getPermissions());
    }
}
