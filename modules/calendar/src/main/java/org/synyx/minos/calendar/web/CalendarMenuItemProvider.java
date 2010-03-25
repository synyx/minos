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
     * @see
     * com.synyx.minos.core.web.menu.AbstractMenuItemProvider#initMenuItems()
     */
    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem subItem =
                new MenuItem("calendar.menu.appointment", 0,
                        CalendarUrls.APPOINTMENTS);

        MenuItem mainItem =
                new MenuItem("calendar.menu", 100, CalendarUrls.MODULE, subItem);

        return Arrays.asList(mainItem);
    }
}
