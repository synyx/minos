package org.synyx.minos.core.web.menu;

import org.synyx.hera.core.Plugin;

import java.util.List;


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
    List<MenuItem> getMenuItems();
}
