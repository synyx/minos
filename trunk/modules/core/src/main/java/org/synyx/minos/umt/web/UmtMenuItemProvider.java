package org.synyx.minos.umt.web;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.FirstSubMenuUrlResolver;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.MenuItem.MenuItemBuilder;
import org.synyx.minos.core.web.menu.UrlResolver;
import org.synyx.minos.umt.UmtPermissions;

import java.util.Arrays;
import java.util.List;


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

    public MenuItemBuilder getMainMenuItem() {

        UrlResolver umtItemStrategy = new FirstSubMenuUrlResolver();

        return MenuItem.create(MENU_UMT).withKeyBase("umt.menu").withPosition(10000).withUrlResolver(umtItemStrategy);
    }


    public MenuItemBuilder getLogoutMenuItem() {

        return MenuItem.create(MENU_UMT_LOGOUT).withKeyBase("core.menu.logout").withPosition(1000000).withUrl(
                "/logout");
    }


    public MenuItemBuilder getUserManageMenuItem() {

        return getUserManageMenuItem(getMainMenuItem().build());
    }


    public MenuItemBuilder getUserManageMenuItem(MenuItem parent) {

        return MenuItem.create(MENU_UMT_USERS).withKeyBase("umt.menu.users").withPosition(100).withUrl(UmtUrls.USERS)
            .withPermission(UmtPermissions.UMT_ADMIN).withParent(parent);
    }


    public MenuItemBuilder getRoleManageMenuItem() {

        return getRoleManageMenuItem(getMainMenuItem().build());
    }


    public MenuItemBuilder getRoleManageMenuItem(MenuItem parent) {

        return MenuItem.create(MENU_UMT_ROLES).withKeyBase("umt.menu.roles").withPosition(200).withUrl(UmtUrls.ROLES)
            .withPermission(UmtPermissions.UMT_ADMIN).withParent(parent);
    }


    public MenuItemBuilder getMyAccountMenuItem() {

        return getMyAccountMenuItem(getMainMenuItem().build());
    }


    public MenuItemBuilder getMyAccountMenuItem(MenuItem parent) {

        return MenuItem.create(MENU_UMT_MYACCOUNT).withKeyBase("umt.myaccount").withPosition(300).withUrl(
                UmtUrls.MYACCOUNT).withParent(parent);
    }


    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem umtItem = getMainMenuItem().build();

        MenuItem logoutItem = getLogoutMenuItem().build();

        MenuItem usersItem = getUserManageMenuItem(umtItem).build();

        MenuItem rolesItem = getRoleManageMenuItem(umtItem).build();

        MenuItem myAccountItem = getMyAccountMenuItem(umtItem).build();

        return Arrays.asList(umtItem, logoutItem, usersItem, rolesItem, myAccountItem);
    }
}
