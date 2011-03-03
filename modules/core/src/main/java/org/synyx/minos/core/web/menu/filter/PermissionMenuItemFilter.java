package org.synyx.minos.core.web.menu.filter;

import org.synyx.minos.core.security.AuthenticationService;
import org.synyx.minos.core.web.menu.Menu;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.MenuItemFilter;
import org.synyx.minos.util.Assert;


/**
 * {@link MenuItemFilter} that that considers the permissions registered with the {@link MenuItem}.
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
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
