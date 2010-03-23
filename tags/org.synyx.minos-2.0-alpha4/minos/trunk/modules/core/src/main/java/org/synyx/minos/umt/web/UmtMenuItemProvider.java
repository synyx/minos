package org.synyx.minos.umt.web;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.MenuItem;
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
     * @see
     * com.synyx.minos.core.web.menu.AbstractMenuItemProvider#initMenuItems()
     */
    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem logoutItem =
                new MenuItem("core.menu.logout", Integer.MAX_VALUE - 42,
                        "/logout");

        MenuItem rolesItem = new MenuItem("umt.menu.roles", 10, UmtUrls.ROLES);

        MenuItem usersItem = new MenuItem("umt.menu.users", 0, UmtUrls.USERS);

        MenuItem umtItem =
                new MenuItem("umt.menu", 10000, UmtUrls.MODULE, rolesItem,
                        usersItem).add(UmtPermissions.UMT_ADMIN);

        MenuItem myAddountItem =
                new MenuItem("umt.myaccount", 20000, UmtUrls.MYACCOUNT);

        return Arrays.asList(umtItem, myAddountItem, logoutItem);
    }

}
