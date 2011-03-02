package org.synyx.minos.core.web.menu;

import java.util.Collections;
import java.util.List;


/**
 * Abstract base class to ease declaring {@link MenuItem}s via a {@link MenuItemProvider}. Subclasses usually implement
 * {@link #initMenuItems()} to provide the necessary items.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractMenuItemProvider implements MenuItemProvider {

    private List<MenuItem> menuItems;

    /*
     * (non-Javadoc)
     *
     * @see org.synyx.hera.core.Plugin#supports(java.lang.Object)
     */
    @Override
    public boolean supports(String delimiter) {

        return true;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.core.web.menu.MenuItemProvider#getMenuItems()
     */
    public List<MenuItem> getMenuItems() {

        if (null == menuItems) {
            menuItems = Collections.emptyList();

            List<MenuItem> result = initMenuItems();

            menuItems = null == result ? menuItems : result;
        }

        return menuItems;
    }


    /**
     * Initialize {@link MenuItem}s here. This method will be called on the first access of
     * {@link MenuItemProvider#getMenuItems()}.
     *
     * @return
     */
    protected abstract List<MenuItem> initMenuItems();
}
