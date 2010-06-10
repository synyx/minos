package org.synyx.minos.core.web.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.util.Assert;


/**
 * Class to define a {@link MenuItem}. Allows definition of basic properties like title, description and so on.
 * {@link MenuItem}s can be ordered using the {@code position} property. Title and description will be resolved via
 * {@link java.util.ResourceBundle}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MenuItem implements Comparable<MenuItem> {

    public static final String USER_PLACEHOLDER = "{username}";

    private static final String TITLE_POSTFIX = ".title";
    private static final String DESCRIPTION_POSTFIX = ".description";

    private String id;

    private UrlResolvingStrategy urlStrategy;
    private String title;
    private String desciption;
    private Integer position;
    // TODO: what is this needed for?
    private MenuItem parent;
    private List<MenuItem> subMenues = new ArrayList<MenuItem>();
    private List<String> permissions = new ArrayList<String>();


    private MenuItem(String id) {

        this.id = id;
    }


    /**
     * Returns the URL the {@link MenuItem} shall link to. Replaces eventually contained placeholders for the user with
     * the given {@link User}s username. Cleans up the URL if no {@link User} is given.
     * 
     * @param user the user to create the menu for. Can be {@literal null} if no user is authenticated
     * @return the url
     */
    public String getUrl(User user) {

        String url = urlStrategy.resolveUrl(user, this);
        if (url == null) {
            return null;
        }

        String username = null == user ? "" : user.getUsername();
        return url.replace(USER_PLACEHOLDER, username).replace("//", "/");
    }


    /**
     * Returns the title of the {@link MenuItem}. Will be resolved against a resource bundle.
     * 
     * @return the title
     */
    public String getTitle() {

        return title;
    }


    /**
     * Returns the description of the {@link MenuItem}.
     * 
     * @return the desciption
     */
    public String getDesciption() {

        return desciption;
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
     * Returns whether the {@link MenuItem} is a top-level one.
     * 
     * @return
     */
    public boolean isTopLevel() {

        return null == parent;
    }


    /**
     * Returns the permission required to access this {@link MenuItem}. Includes permissions from parent menu items,
     * too.
     * 
     * @return the permissions
     */
    public List<String> getPermissions() {

        return Collections.unmodifiableList(permissions);
    }


    /**
     * Returns whether the {@link MenuItem} has submenues.
     * 
     * @return
     */
    public boolean hasSubMenues() {

        return 0 != subMenues.size();
    }


    /**
     * Returns all submenues.
     * 
     * @return
     */
    public List<MenuItem> getSubMenues() {

        return subMenues;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(MenuItem item) {

        return this.position.compareTo(item.getPosition());
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(title);
        builder.append(", URL: ").append(getUrl(null));
        builder.append(", Position: ").append(position);
        builder.append(", Permissions: ").append(StringUtils.collectionToCommaDelimitedString(getPermissions()));

        return builder.toString();
    }


    /**
     * Returns a deep copy of this. This means that a copy of this with copies of all subMenueitems (and so on) is
     * returned.
     * 
     * @return a deep copy of this {@link MenuItem}
     */
    public MenuItem deepCopy() {

        MenuItemBuilder builder =
                create(id).withDescription(title).withTitle(title).withPosition(position).withUrlStrategy(urlStrategy)
                        .withPermissions(permissions);

        if (subMenues != null) {
            List<MenuItem> subCopy = new ArrayList<MenuItem>();

            for (MenuItem sub : subMenues) {
                subCopy.add(sub.deepCopy());
            }
            builder.withSubmenues(subCopy);
        }

        return builder.build();
    }


    @Override
    public int hashCode() {

        return id.hashCode();
        // final int prime = 31;
        // int result = 1;
        // result = prime * result + desciption.hashCode();
        // result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        // result = prime * result + position;
        // result = prime * result + title.hashCode();
        // return result;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MenuItem other = (MenuItem) obj;

        return other.id.equals(id);
        //

        // if (!desciption.equals(other.desciption))
        // return false;
        //
        // if (!title.equals(other.title))
        // return false;
        //
        // if (parent == null) {
        // if (other.parent != null)
        // return false;
        // } else if (!parent.equals(other.parent))
        // return false;
        // if (position != other.position)
        // return false;
        //
        // return true;
    }


    public static MenuItemBuilder create(String id) {

        return new MenuItemBuilder(id);
    }

    public static class MenuItemBuilder {

        private MenuItem menuItem;


        /**
         * @param id
         */
        public MenuItemBuilder(String id) {

            menuItem = new MenuItem(id);
        }


        public MenuItem build() {

            checkStrategy();
            if (menuItem.subMenues != null) {
                Collections.sort(menuItem.subMenues);
            }
            return menuItem;

        }


        private void checkStrategy() {

            if (menuItem.urlStrategy != null) {
                return;
            }
            if (menuItem.hasSubMenues()) {
                menuItem.urlStrategy = new FirstSubMenuUrlResolvingStrategy();
            } else {
                throw new IllegalStateException(
                        "No UrlResolvingStrategy not given. Could not autodetect one (you must supply a strategy, a url or submenues).");
            }

        }


        public MenuItemBuilder withTitle(String title) {

            Assert.notNull(title);
            menuItem.title = title;
            return this;
        }


        public MenuItemBuilder withDescription(String description) {

            Assert.notNull(description);
            menuItem.desciption = description;
            return this;
        }


        public MenuItemBuilder withPosition(Integer position) {

            Assert.notNull(position);
            menuItem.position = position;
            return this;
        }


        public MenuItemBuilder withSubmenues(List<MenuItem> subMenues) {

            Assert.notNull(subMenues);
            menuItem.subMenues = subMenues;

            for (MenuItem item : subMenues) {
                item.parent = menuItem;
            }
            return this;
        }


        public MenuItemBuilder withSubmenues(MenuItem... subMenues) {

            Assert.notNull(subMenues);
            return withSubmenues(Arrays.asList(subMenues));
        }


        public MenuItemBuilder withSubmenu(MenuItem subMenu) {

            Assert.notNull(subMenu);
            menuItem.subMenues.add(subMenu);
            subMenu.parent = menuItem;
            return this;
        }


        public MenuItemBuilder withPermissions(List<String> permissions) {

            Assert.notNull(permissions);
            menuItem.permissions = permissions;
            return this;
        }


        public MenuItemBuilder withPermission(String permission) {

            menuItem.permissions.add(permission);
            return this;
        }


        public MenuItemBuilder withKeyBase(String keyBase) {

            Assert.notNull(keyBase);
            menuItem.title = keyBase + TITLE_POSTFIX;
            menuItem.desciption = keyBase + DESCRIPTION_POSTFIX;
            return this;
        }


        public MenuItemBuilder withUrlStrategy(UrlResolvingStrategy strategy) {

            Assert.notNull(strategy);
            menuItem.urlStrategy = strategy;
            return this;
        }


        public MenuItemBuilder withUrl(String url) {

            Assert.notNull(url);
            menuItem.urlStrategy = new SimpleUrlResolvingStrategy(url);
            return this;
        }

    }
}