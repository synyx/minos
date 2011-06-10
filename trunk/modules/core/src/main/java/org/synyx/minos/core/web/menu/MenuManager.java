package org.synyx.minos.core.web.menu;

import org.springframework.beans.factory.InitializingBean;

import org.synyx.minos.util.Assert;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * {@link MenuProvider} implementation that uses {@link MenuItem}s provided by {@link MenuItemProvider}s defined in the
 * context, filters them using {@link MenuItemFilter}s defined in the context and assembles the {@link MenuItems} using
 * a {@link MenuAssembler}-Implementation.
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
     * Configures a {@link MenuAssembler}. Defaults to {@link #DEFAULT_MENUASSEMBLER}.
     *
     * @param menuAssembler the menuAssembler to set
     */
    public void setMenuAssembler(MenuAssembler menuAssembler) {

        this.menuAssembler = menuAssembler == null ? DEFAULT_MENUASSEMBLER : menuAssembler;
    }


    @Override
    public MenuItems getMenu(String id) {

        MenuItems items = buildMenu(menuItems);
        MenuItems menuTree = items.filter(menuItemFilters);

        Map<String, MenuItems> menus = buildMenus(menuTree);

        MenuItems menu = menus.get(id);

        if (menu == null) {
            menu = MenuItems.EMPTY;
        }

        return menu;
    }


    /**
     * Converts the flat hierarchy of {@link MenuItems} into a Tree of {@link MenuItems} by resolving the parent-child
     * relation.
     *
     * @param menuItems
     * @return
     */
    private MenuItems buildMenu(List<MenuItem> menuItems) {

        Map<String, List<Menu>> childMap = new HashMap<String, List<Menu>>();

        for (MenuItem item : menuItems) {
            List<Menu> children = childMap.get(item.getPath());

            if (children == null) {
                children = new ArrayList<Menu>();
            } else {
                Collections.sort(children);
            }

            Menu menu = Menu.create(item, new MenuItems(children));

            List<Menu> items = childMap.get(item.getParentPath());

            if (items == null) {
                items = new ArrayList<Menu>();
                childMap.put(item.getParentPath(), items);
            }

            items.add(menu);
        }

        List<Menu> root = childMap.get("");

        if (root == null) {
            root = new ArrayList<Menu>();
        } else {
            Collections.sort(root);
        }

        return new MenuItems(root);
    }


    /**
     * Build the actual {@link MenuItems}es from the given {@link MenuItems}. Default implementation will delegate this
     * to configured {@link MenuAssembler}.
     * <p>
     * TODO: should we wrap the Map into a Menus class to avoid null checks for non existent id lookups?
     * </p>
     *
     * @param items
     * @return
     */
    protected Map<String, MenuItems> buildMenus(MenuItems items) {

        return menuAssembler.assembleMenus(items);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        for (MenuItemProvider provider : menuItemProviders) {
            menuItems.addAll(provider.getMenuItems());
        }

        Collections.sort(menuItems, new MenuItemComparator());
    }

    private static class MenuItemComparator implements Comparator<MenuItem>, Serializable {

        private static final long serialVersionUID = -1034641285923829020L;

        @Override
        public int compare(MenuItem o1, MenuItem o2) {

            int value = o2.getPath().length() - o1.getPath().length();

            if (value == 0) {
                return o1.compareTo(o2);
            }

            return value;
        }
    }
}
