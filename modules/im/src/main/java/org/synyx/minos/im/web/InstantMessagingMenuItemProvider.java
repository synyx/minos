package org.synyx.minos.im.web;

import static org.synyx.minos.im.web.ImUrls.*;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.MenuItem;



/**
 * Menu declarations for Instant Messaging module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class InstantMessagingMenuItemProvider extends AbstractMenuItemProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.web.menu.AbstractMenuItemProvider#initMenuItems()
     */
    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem imMenu = new MenuItem("im.menu", 5, MODULE);

        return Arrays.asList(imMenu);
    }
}
