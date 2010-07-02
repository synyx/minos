package org.synyx.minos.core.web.menu;

/**
 * Abstraction of a {@link Menu}. That is a tree of {@link MenuItem}s.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class Menu {

    private final MenuItems items;


    public Menu(MenuItem... items) {

        this(new MenuItems(items));
    }


    public Menu(MenuItems menuItems) {

        this.items = menuItems;
    }


    public MenuItems getItems() {

        return this.items;
    }


    public boolean hasMenuItem(String id) {

        return getMenuItem(id, items) != null;
    }


    private static MenuItem getMenuItem(String id, MenuItems currentItems) {

        if (currentItems == null) {
            return null;
        }

        for (MenuItem item : currentItems) {
            if (id.equals(item.getId())) {
                return item;
            }

            if (item.hasSubMenues()) {
                MenuItem subItem = getMenuItem(id, item.getSubMenues());
                if (subItem != null) {
                    return subItem;
                }

            }
        }

        return null;
    }
}
