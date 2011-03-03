package org.synyx.minos.core.web.menu;

import java.util.Arrays;
import java.util.List;


/**
 * Basic configurable {@link MenuItemProvider}.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SimpleMenuItemProvider implements MenuItemProvider {

    private List<MenuItem> menuItems;

    /**
     * Set a single {@link MenuItem}.
     *
     * @param menuItem a menu item
     */
    public void setMenuItem(MenuItem menuItem) {

        this.menuItems = Arrays.asList(menuItem);
    }


    /**
     * Set multiple {@link MenuItem}s.
     *
     * @param menuItems the menuItems to set
     */
    public void setMenuItems(List<MenuItem> menuItems) {

        this.menuItems = menuItems;
    }


    public List<MenuItem> getMenuItems() {

        return menuItems;
    }


    @Override
    public boolean supports(String delimiter) {

        return true;
    }
}
