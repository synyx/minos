package org.synyx.minos.core.web.menu.filter;

import org.synyx.minos.core.authentication.AuthenticationService;
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


    /*
     * (non-Javadoc)
     * 
     * @see com.google.common.base.Predicate#apply(java.lang.Object)
     */
    @Override
    public boolean apply(MenuItem item) {

        return authenticationService.hasAllPermissions(item.getPermissions());
    }
}
