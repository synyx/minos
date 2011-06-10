package org.synyx.minos.core.web.menu;

import java.util.HashMap;
import java.util.Map;


/**
 * {@link MenuAssembler} implementation that simply puts the given {@link MenuItems} to a single Menu named MAIN
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class SimpleMenuAssembler implements MenuAssembler {

    @Override
    public Map<String, MenuItems> assembleMenus(MenuItems items) {

        Map<String, MenuItems> menus = new HashMap<String, MenuItems>();
        menus.put("MAIN", items);

        return menus;
    }
}
