package org.synyx.minos.cmt.web;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.MenuItemProvider;


/**
 * {@link MenuItem}s for contact management module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class CmtMenuItemProvider implements MenuItemProvider {

    private final List<MenuItem> menuItem;


    /**
     * Creates a new {@link CmtMenuItemProvider}.
     */
    public CmtMenuItemProvider() {

        MenuItem contactsItem =
                new MenuItem("cmt.menu.contacts", 0, CmtUrls.LIST_CONTACTS);

        MenuItem cmtItem =
                new MenuItem("cmt.menu", Integer.MAX_VALUE,
                        CmtUrls.LIST_CONTACTS, contactsItem);

        this.menuItem = Arrays.asList(cmtItem);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.web.menu.MenuItemProvider#getMenuItem()
     */
    public List<MenuItem> getMenuItems() {

        return menuItem;
    }
}
