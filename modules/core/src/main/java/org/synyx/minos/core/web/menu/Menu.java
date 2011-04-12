package org.synyx.minos.core.web.menu;

import com.google.common.base.Predicate;
import static com.google.common.collect.Iterables.filter;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Abstraction of a {@link Menu}. That is a tree of {@link MenuItem}s.
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public final class Menu implements Comparable<Menu> {

    private final String id;
    private String path;

    private UrlResolver urlStrategy;
    private String title;
    private String description;
    private Integer position = 0;

    private MenuItems subMenus;
    private List<String> permissions = new ArrayList<String>();

    private Menu(String id) {

        this.id = id;
    }

    public String getId() {

        return id;
    }


    public String getPath() {

        return path;
    }


    /**
     * Returns the URL the {@link MenuTreeItem} shall link to.
     *
     * @return the url
     */
    public String getUrl() {

        return urlStrategy.resolveUrl(this);
    }


    /**
     * Returns the title of the {@link MenuTreeItem}. Will be resolved against a resource bundle.
     *
     * @return the title
     */
    public String getTitle() {

        return title;
    }


    /**
     * Returns the description of the {@link MenuTreeItem}.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }


    /**
     * Returns the position of the menu item. 0 means first one.
     *
     * @return the position
     */
    public int getPosition() {

        return position;
    }


    /**
     * Returns the permission required to access this {@link MenuTreeItem}. Includes permissions from parent menu items,
     * too.
     *
     * @return the permissions
     */
    public List<String> getPermissions() {

        return Collections.unmodifiableList(permissions);
    }


    /**
     * Returns whether the {@link MenuTreeItem} has submenus.
     *
     * @return
     */
    public boolean hasSubMenus() {

        return !subMenus.isEmpty();
    }


    public MenuItems getSubMenus() {

        return subMenus;
    }


    @Override
    public int compareTo(Menu other) {

        return this.position.compareTo(other.getPosition());
    }


    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(title);
        builder.append(", URL: ").append(getUrl());
        builder.append(", Position: ").append(position);
        builder.append(", Permissions: ").append(StringUtils.collectionToCommaDelimitedString(getPermissions()));

        return builder.toString();
    }


    /**
     * Returns a deep copy of this. This means that a copy of this with copies of all submenu items (and so on) is
     * returned.
     *
     * @return a deep copy of this {@link Menu}
     */
    public Menu deepCopy(Predicate<Menu> subMenuItemFilters) {

        Menu menu = new Menu(getId());
        menu.description = getDescription();
        menu.title = getTitle();
        menu.position = getPosition();
        menu.urlStrategy = getUrlStrategy();
        menu.permissions = new ArrayList<String>();

        for (String permission : getPermissions()) {
            menu.permissions.add(permission);
        }

        List<Menu> subs = new ArrayList<Menu>();

        if (hasSubMenus()) {
            // Only clone sub menu items that satisfy the filter
            for (Menu sub : filter(subMenus, subMenuItemFilters)) {
                subs.add(sub.deepCopy(subMenuItemFilters));
            }
        }

        menu.subMenus = new MenuItems(subs);

        return menu;
    }


    @Override
    public int hashCode() {

        return path.hashCode();
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Menu other = (Menu) obj;

        return other.path.equals(path);
    }


    /**
     * Returns whether we have the given {@link MenuTreeItem} somewhere in our trees of sub {@link MenuTreeItem}s.
     *
     * @param menu
     * @return
     */
    public boolean hasSubMenuItem(Menu menu) {

        return hasSubMenus() ? getSubMenus().contains(menu) : false;
    }


    /**
     * Returns whether the {@link MenuTreeItem} shall be considered as active for the given URL.
     *
     * @param url
     * @return
     */
    public boolean isActiveFor(String url) {

        String urlForMenu = urlStrategy.resolveUrl(this);

        if (urlForMenu != null && url.startsWith(urlForMenu)) {
            return true;
        }

        if (!hasSubMenus()) {
            return false;
        }

        for (Menu sub : getSubMenus()) {
            if (sub.isActiveFor(url)) {
                return true;
            }
        }

        return false;
    }


    public UrlResolver getUrlStrategy() {

        return urlStrategy;
    }


    /**
     * Creates a new {@link Menu} with the given {@link MenuItem} and the given {@link MenuItems} as its children.
     *
     * @param item
     * @param children
     * @return
     */
    public static Menu create(MenuItem item, MenuItems children) {

        Menu menu = new Menu(item.getId());
        menu.description = item.getDescription();
        menu.title = item.getTitle();
        menu.position = item.getPosition();
        menu.urlStrategy = item.getUrlResolver();
        menu.path = item.getPath();

        menu.permissions = new ArrayList<String>();

        for (String permission : item.getPermissions()) {
            menu.permissions.add(permission);
        }

        menu.subMenus = children;

        return menu;
    }


    /**
     * Creates a new {@link Menu} with the given {@link MenuItem} and no children.
     *
     * @param item
     * @return
     */
    public static Menu create(MenuItem item) {

        List<Menu> menuItems = Collections.emptyList();

        return create(item, new MenuItems(menuItems));
    }
}
