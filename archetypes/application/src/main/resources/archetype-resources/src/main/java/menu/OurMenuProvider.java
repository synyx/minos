#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.menu;

import ${package}.web.ItemController;
import org.synyx.minos.core.web.menu.AbstractMenuItemProvider;
import org.synyx.minos.core.web.menu.FirstSubMenuUrlResolver;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.MenuItem.MenuItemBuilder;

import java.util.ArrayList;
import java.util.List;


/*
 * This class is responsible for creating entries for the built-in menu system.
 * You usually extends AbstractMenuItemProvider and implement the single abstract
 * method initMenuItems() to provide the module system with the menu items you need
 * displayed. The order of the list returned is not used in the rendering of the items,
 * instead MenuItems declare a position parameter.
 */
public class OurMenuProvider extends AbstractMenuItemProvider {

    public static final String MENU_ITEMS_KEYBASE = "menu.items";

    private int posCounter = 10;

    @Override
    protected List<MenuItem> initMenuItems() {

        List<MenuItem> menu = new ArrayList<MenuItem>();
        MenuItem root = buildMenuRootForItems();
        menu.add(root);
        buildMenuForItems(menu, root);

        return menu;
    }


    private void buildMenuForItems(List<MenuItem> menu, MenuItem root) {

        menu.add(buildSubMenuItem("list", ItemController.BASE_URL, root));
        menu.add(buildSubMenuItem("create", ItemController.FORM_URL, root));
    }


    private MenuItem buildMenuRootForItems() {

        MenuItemBuilder menuItemBuilder = MenuItem.create("menu.items");
        menuItemBuilder.withKeyBase(MENU_ITEMS_KEYBASE);
        menuItemBuilder.withPosition(200);
        menuItemBuilder.withUrlResolver(new FirstSubMenuUrlResolver());

        return menuItemBuilder.build();
    }


    private MenuItem buildSubMenuItem(String id, String url, MenuItem parent) {

        MenuItemBuilder menuItemBuilder = MenuItem.create(id);
        menuItemBuilder.withKeyBase(MENU_ITEMS_KEYBASE + "." + id);
        menuItemBuilder.withUrl(url);
        menuItemBuilder.withParent(parent);
        menuItemBuilder.withPosition(posCounter);
        posCounter += 5;

        return menuItemBuilder.build();
    }
}
