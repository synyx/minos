/**
 * 
 */
package org.synyx.minos.core.web.menu;

import java.util.List;

import org.synyx.hera.core.Plugin;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface MenuItemFilter extends Plugin<String> {

    /**
     * Filters menuItems from the given list. This is expected to be done recursive if needed. The given List can be
     * manipulated. This method is expected to handle null-values
     * 
     * @param items the {@link MenuItem}s to filter or null
     * @return
     */
    List<MenuItem> filterMenuItems(List<MenuItem> items);

}
