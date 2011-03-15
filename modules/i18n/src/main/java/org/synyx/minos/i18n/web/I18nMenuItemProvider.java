package org.synyx.minos.i18n.web;

import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.i18n.I18nPermissions;

import java.util.Arrays;
import java.util.List;


/**
 * {@link org.synyx.minos.core.web.menu.MenuItemProvider} for I18n module.
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class I18nMenuItemProvider extends AbstractMenuItemProvider {

    private static final String MENU_I18N = "MENU_I18N";
    private static final String MENU_I18N_BASENAMES = "MENU_I18N_BASENAMES";


    @Override
    protected List<MenuItem> initMenuItems() {

        MenuItem mainMenu = MenuItem.create(MENU_I18N).withKeyBase("i18n.menu").withPosition(1000).withUrl(
                I18nController.URL_MAIN).withPermission(I18nPermissions.I18N_LIST_MESSAGES).build();

        MenuItem baseNames = MenuItem.create(MENU_I18N_BASENAMES).withKeyBase("i18n.basenames.menu").withPosition(1)
            .withUrl(I18nController.URL_BASENAMES).withParent(mainMenu).withPermission(I18nPermissions.I18N_LIST_BASES)
            .build();

        return Arrays.asList(mainMenu, baseNames);
    }
}
