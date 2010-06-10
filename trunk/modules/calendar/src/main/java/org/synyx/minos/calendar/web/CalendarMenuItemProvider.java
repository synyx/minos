package org.synyx.minos.calendar.web;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.MenuItem;


/**
 * {@link java.awt.MenuItem}s for calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class CalendarMenuItemProvider extends AbstractMenuItemProvider {

    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.web.menu.AbstractMenuItemProvider#initMenuItems()
     */
    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem subItem =
                MenuItem.create("MENU_CALENDAR_APPOINTMENTS").withKeyBase("calendar.menu.appointment").withPosition(0)
                        .withUrl(CalendarUrls.APPOINTMENTS).build();
        MenuItem mainItem =
                MenuItem.create("MENU_CALENDAR_MAIN").withKeyBase("calendar.menu").withPosition(100).withUrl(
                        CalendarUrls.MODULE).withSubmenu(subItem).build();

        return Arrays.asList(mainItem);
    }
}
