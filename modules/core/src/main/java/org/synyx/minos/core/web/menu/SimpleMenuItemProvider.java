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
     * @param menuItem
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


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.web.menu.MenuItemProvider#getMenuItems()
     */
    public List<MenuItem> getMenuItems() {

        return menuItems;
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
}
