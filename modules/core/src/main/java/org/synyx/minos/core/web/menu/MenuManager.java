/**
 * 
 */
package org.synyx.minos.core.web.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * {@link MenuProvider} implementation that uses {@link MenuItem} provided by {@link MenuItemProvider}s defined in the
 * context, filteres them using {@link MenuItemFilter}s defined in the context and assembles the {@link Menu}s using a
 * {@link MenuAssembler}-Implementation.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MenuManager implements MenuProvider {

    public static final MenuAssembler DEFAULT_MENUASSEMBLER = new SimpleMenuAssembler();

    private List<MenuItemProvider> menuItemProviders;
    private List<MenuItemFilter> menuItemFilters;

    private List<MenuItem> menuItems;

    private MenuAssembler menuAssembler = DEFAULT_MENUASSEMBLER;


    public void loadMenuItems() {

        menuItems = new ArrayList<MenuItem>();

        for (MenuItemProvider provider : menuItemProviders) {

            for (MenuItem menuItem : provider.getMenuItems()) {

                menuItems.add(menuItem);
            }
        }

        Collections.sort(menuItems);

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.MenuProvider#getMenu(java.lang.String, org.synyx.minos.core.domain.User)
     */
    @Override
    public Menu getMenu(String id) {

        synchronized (this) {
            if (menuItems == null) {
                loadMenuItems();
            }
        }

        List<MenuItem> items = cloneItems(menuItems);
        items = filterMenueItems(items);

        Map<String, Menu> menues = buildMenues(items);

        Menu menu = menues.get(id);
        if (menu == null) {
            List<MenuItem> empty = Collections.emptyList();
            menu = new Menu(empty);
        }

        return menu;
    }


    protected List<MenuItem> cloneItems(List<MenuItem> items) {

        // create a deep-copy of each item to be able to change the tree later
        // (remove items the current user is not allowed to see)
        List<MenuItem> itemCopy = new ArrayList<MenuItem>();

        for (MenuItem item : items) {
            itemCopy.add(item.deepCopy());
        }

        return itemCopy;

    }


    protected Map<String, Menu> buildMenues(List<MenuItem> itemCopy) {

        return getMenuAssembler().assembleMenues(new Menu(itemCopy));

    }


    protected List<MenuItem> filterMenueItems(List<MenuItem> items) {

        for (MenuItemFilter filter : menuItemFilters) {
            items = filter.filterMenuItems(items);
        }
        return items;

    }


    /**
     * @param menuItemProviders the menuItemProviders to set
     */
    public void setMenuItemProviders(List<MenuItemProvider> menuItemProviders) {

        this.menuItemProviders = menuItemProviders;
    }


    /**
     * @return the menuItemProviders
     */
    public List<MenuItemProvider> getMenuItemProviders() {

        return menuItemProviders;
    }


    /**
     * @param menuItemFilters the menuItemFilters to set
     */
    public void setMenuItemFilters(List<MenuItemFilter> menuItemFilters) {

        this.menuItemFilters = menuItemFilters;
    }


    /**
     * @return the menuItemFilters
     */
    public List<MenuItemFilter> getMenuItemFilters() {

        return menuItemFilters;
    }


    /**
     * @param menuAssembler the menuAssembler to set
     */
    public void setMenuAssembler(MenuAssembler menuAssembler) {

        this.menuAssembler = menuAssembler;
    }


    /**
     * @return the menuAssembler
     */
    public MenuAssembler getMenuAssembler() {

        return menuAssembler;
    }

}
