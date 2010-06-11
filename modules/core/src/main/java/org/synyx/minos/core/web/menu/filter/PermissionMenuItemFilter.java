/**
 * 
 */
package org.synyx.minos.core.web.menu.filter;

import java.util.ArrayList;
import java.util.List;

import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.MenuItemFilter;


/**
 * {@link MenuItemFilter} that removes all {@link MenuItem}s that the currently logged in user is not allowed to see
 * (recursive!)
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class PermissionMenuItemFilter implements MenuItemFilter {

    private AuthenticationService authenticationService;


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.MenuItemFilter#filterMenuItems(java.util.List,
     * org.synyx.minos.core.domain.User)
     */
    @Override
    public List<MenuItem> filterMenuItems(List<MenuItem> items) {

        if (items == null) {
            return null;
        }
        List<MenuItem> toRemove = new ArrayList<MenuItem>();
        for (MenuItem item : items) {

            if (isAllowedToSee(item)) {
                filterMenuItems(item.getSubMenues());
            } else {
                toRemove.add(item);
            }

        }
        items.removeAll(toRemove);
        return items;
    }


    private boolean isAllowedToSee(MenuItem item) {

        if (null == authenticationService) {
            throw new IllegalStateException("no authenticationservice is set");
        }

        return authenticationService.hasAllPermissions(item.getPermissions());
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hera.core.Plugin#supports(java.lang.Object)
     */
    @Override
    public boolean supports(String delimiter) {

        return true;
    }


    /**
     * @param authenticationService the authenticationService to set
     */
    public void setAuthenticationService(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

}
