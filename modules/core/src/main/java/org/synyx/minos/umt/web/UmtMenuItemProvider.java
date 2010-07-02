package org.synyx.minos.umt.web;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.PreferredSubMenuItemUrlResolver;
import org.synyx.minos.core.web.menu.UrlResolver;
import org.synyx.minos.umt.UmtPermissions;


/**
 * {@link MenuItem}s for user management module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UmtMenuItemProvider extends AbstractMenuItemProvider {

    public static final String MENU_UMT = "MENU_UMT";
    public static final String MENU_UMT_LOGOUT = "MENU_UMT_LOGOUT";
    public static final String MENU_UMT_ROLES = "MENU_UMT_ROLES";
    public static final String MENU_UMT_USERS = "MENU_UMT_USERS";
    public static final String MENU_UMT_MYACCOUNT = "MENU_UMT_MYACCOUNT";


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.web.menu.AbstractMenuItemProvider#initMenuItems()
     */
    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem logoutItem =
                MenuItem.create(MENU_UMT_LOGOUT).withKeyBase("core.menu.logout").withPosition(1000000).withUrl(
                        "/logout").build();

        MenuItem usersItem =
                MenuItem.create(MENU_UMT_USERS).withKeyBase("umt.menu.users").withPosition(100).withUrl(UmtUrls.USERS)
                        .withPermission(UmtPermissions.UMT_ADMIN).build();

        MenuItem rolesItem =
                MenuItem.create(MENU_UMT_ROLES).withKeyBase("umt.menu.roles").withPosition(200).withUrl(UmtUrls.ROLES)
                        .withPermission(UmtPermissions.UMT_ADMIN).build();

        MenuItem myAccountItem =
                MenuItem.create(MENU_UMT_MYACCOUNT).withKeyBase("umt.myaccount").withPosition(300).withUrl(
                        UmtUrls.MYACCOUNT).build();

        // the menu should have the url of usersItem if possible or else the
        // myAccountItem
        UrlResolver umtItemStrategy = new PreferredSubMenuItemUrlResolver(usersItem, myAccountItem);
        MenuItem umtItem =
                MenuItem.create(MENU_UMT).withKeyBase("umt.menu").withPosition(10000).withUrlResolver(umtItemStrategy)
                        .withSubmenues(usersItem, rolesItem, myAccountItem).build();

        return Arrays.asList(umtItem, logoutItem);
    }

}
