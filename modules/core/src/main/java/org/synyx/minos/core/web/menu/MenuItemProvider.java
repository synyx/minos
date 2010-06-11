package org.synyx.minos.core.web.menu;

import java.util.List;

import org.synyx.hera.core.Plugin;


/**
 * Interface to allow modules to declare {@link MenuItem}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface MenuItemProvider extends Plugin<String> {

    /**
     * Returns the {@link MenuItem}s to be shown.
     * 
     * @return
     */
    public List<MenuItem> getMenuItems();
}
