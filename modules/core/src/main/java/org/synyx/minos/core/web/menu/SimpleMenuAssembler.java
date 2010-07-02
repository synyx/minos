/**
 * 
 */
package org.synyx.minos.core.web.menu;

import java.util.HashMap;
import java.util.Map;


/**
 * {@link MenuAssembler} implementation that simply puts the given {@link Menu} to a single Menu named MAIN
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class SimpleMenuAssembler implements MenuAssembler {

    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.MenuAssembler#assembleMenues(org.synyx.minos.core.web.menu.MenuItems)
     */
    @Override
    public Map<String, Menu> assembleMenues(MenuItems items) {

        Map<String, Menu> menues = new HashMap<String, Menu>();
        menues.put("MAIN", new Menu(items));
        return menues;
    }

}
