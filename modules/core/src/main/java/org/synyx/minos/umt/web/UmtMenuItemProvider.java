package org.synyx.minos.umt.web;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.PreferredSubMenuItemUrlResolvingStrategy;
import org.synyx.minos.core.web.menu.UrlResolvingStrategy;
import org.synyx.minos.umt.UmtPermissions;


/**
 * {@link MenuItem}s for user management module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UmtMenuItemProvider extends AbstractMenuItemProvider {

    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.web.menu.AbstractMenuItemProvider#initMenuItems()
     */
    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem logoutItem = new MenuItem("core.menu.logout", Integer.MAX_VALUE - 42, "/logout");

        MenuItem rolesItem = new MenuItem("umt.menu.roles", 200, UmtUrls.ROLES).add(UmtPermissions.UMT_ADMIN);

        MenuItem usersItem = new MenuItem("umt.menu.users", 100, UmtUrls.USERS).add(UmtPermissions.UMT_ADMIN);

        MenuItem myAccountItem = new MenuItem("umt.myaccount", 300, UmtUrls.MYACCOUNT);

        // the menu should have the url of usersItem if possible or else the
        // myAccountItem
        UrlResolvingStrategy umtItemStrategy = new PreferredSubMenuItemUrlResolvingStrategy(usersItem, myAccountItem);
        MenuItem umtItem = new MenuItem("umt.menu", 10000, umtItemStrategy, myAccountItem, rolesItem, usersItem);

        return Arrays.asList(umtItem, logoutItem);
    }
}
