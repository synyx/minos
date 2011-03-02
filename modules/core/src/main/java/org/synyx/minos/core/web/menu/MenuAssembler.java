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
     * Returns a {@link Map} of {@link MenuItems} keyed by a {@link String} representing their name
     *
     * @param items all {@link MenuItem}s available
     * @return a {@link Map} of {@link MenuItems} keyed by a {@link String} represending their name
     */
    Map<String, MenuItems> assembleMenues(MenuItems items);
}
