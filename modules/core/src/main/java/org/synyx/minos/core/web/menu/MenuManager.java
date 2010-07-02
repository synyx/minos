/**
 * 
 */
package org.synyx.minos.core.web.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.synyx.minos.util.Assert;


/**
 * {@link MenuProvider} implementation that uses {@link MenuItem} provided by {@link MenuItemProvider}s defined in the
 * context, filteres them using {@link MenuItemFilter}s defined in the context and assembles the {@link Menu}s using a
 * {@link MenuAssembler}-Implementation.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class MenuManager implements MenuProvider, InitializingBean {

    private static final MenuAssembler DEFAULT_MENUASSEMBLER = new SimpleMenuAssembler();

    private final List<MenuItemProvider> menuItemProviders;
    private final List<MenuItemFilter> menuItemFilters;
    private MenuAssembler menuAssembler = DEFAULT_MENUASSEMBLER;

    private List<MenuItem> menuItems = new ArrayList<MenuItem>();


    /**
     * Creates a new {@link MenuManager}.
     * 
     * @param menuItemProviders
     * @param menuItemFilters
     */
    public MenuManager(List<MenuItemProvider> menuItemProviders, List<MenuItemFilter> menuItemFilters) {

        Assert.notNull(menuItemProviders);
        Assert.notNull(menuItemFilters);

        this.menuItemProviders = menuItemProviders;
        this.menuItemFilters = menuItemFilters;
    }


    /**
     * Configures a {@link MenuAssembler}. Defaults to {@value #DEFAULT_MENUASSEMBLER}.
     * 
     * @param menuAssembler the menuAssembler to set
     */
    public void setMenuAssembler(MenuAssembler menuAssembler) {

        this.menuAssembler = menuAssembler == null ? DEFAULT_MENUASSEMBLER : menuAssembler;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.MenuProvider#getMenu(java.lang.String, org.synyx.minos.core.domain.User)
     */
    @Override
    public Menu getMenu(String id) {

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
     */

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        for (MenuItemProvider provider : menuItemProviders) {
            menuItems.addAll(provider.getMenuItems());
        }

        Collections.sort(menuItems);
    }
}
