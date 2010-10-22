/**
 * 
 */
package org.synyx.minos.core.web.menu;

import java.util.HashMap;
import java.util.Map;


/**
 * {@link MenuAssembler} implementation that simply puts the given {@link MenuItems} to a single Menu named MAIN
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
    public Map<String, MenuItems> assembleMenues(MenuItems items) {

        Map<String, MenuItems> menues = new HashMap<String, MenuItems>();
        menues.put("MAIN", items);
        return menues;
    }

}
