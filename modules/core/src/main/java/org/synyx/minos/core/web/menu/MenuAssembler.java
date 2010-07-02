package org.synyx.minos.core.web.menu;

import java.util.Map;


/**
 * Assembles a menue. Gets all {@link MenuItem}s collected from {@link MenuItemProvider}s and filtered by
 * {@link MenuItemFilter}s and should build the final menu-structure.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MenuAssembler {

    /**
     * Returns a {@link Map} of Menues keyed by a {@link String} representing their name
     * 
     * @param itemCopy all {@link MenuItem}s available
     * @return
     */
    Map<String, Menu> assembleMenues(MenuItems items);
}
