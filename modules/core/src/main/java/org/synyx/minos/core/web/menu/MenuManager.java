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

        MenuItems items = new MenuItems(menuItems).filter(menuItemFilters);
        Map<String, Menu> menues = buildMenues(items);

        Menu menu = menues.get(id);
        if (menu == null) {
            List<MenuItem> empty = Collections.emptyList();
            menu = new Menu(empty);
        }

        return menu;
    }


    /**
     * Build the actual {@link Menu}es from the given {@link MenuItems}. Default implmentation will delegate this to
     * configured {@link MenuAssembler}.
     * <p>
     * TODO: should we wrap the Map into a Menues class to avoid null checks for non existent id lookups?
     * 
     * @param items
     * @return
     */
    protected Map<String, Menu> buildMenues(MenuItems items) {

        return menuAssembler.assembleMenues(items);
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
