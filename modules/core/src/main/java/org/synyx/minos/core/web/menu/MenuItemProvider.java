package org.synyx.minos.core.web.menu;

import java.util.List;


/**
 * Interface to allow modules to declare {@link MenuItem}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface MenuItemProvider {

    /**
     * Returns the {@link MenuItem}s to be shown.
     * 
     * @return
     */
    public List<MenuItem> getMenuItems();
}
