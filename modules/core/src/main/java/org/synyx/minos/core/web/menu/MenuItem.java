package org.synyx.minos.core.web.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;
import org.synyx.minos.core.domain.User;


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

    private final UrlResolvingStrategy urlStrategy;
    private final String title;
    private final String desciption;
    private final int position;
    private MenuItem parent;
    private List<MenuItem> subMenues;
    private List<String> permissions;


    /**
     * Creates a new {@link MenuItem} with the given {@code url} as target using the given {@code keyBase} to define
     * title and description. {@link #TITLE_POSTFIX} and {@link #DESCRIPTION_POSTFIX} will be appended accordingly and
     * default to {@value #TITLE_POSTFIX} as well as {@value #DESCRIPTION_POSTFIX}.
     * 
     * @param keyBase
     * @param position
     * @param url
     */
    public MenuItem(String keyBase, int position, String url) {

        this(keyBase, position, url, null, new MenuItem[] {});
    }


    /**
     * Creates a new {@link MenuItem} with the given {@code UrlResolvingStrategy} as target using the given {@code
     * keyBase} to define title and description. {@link #TITLE_POSTFIX} and {@link #DESCRIPTION_POSTFIX} will be
     * appended accordingly and default to {@value #TITLE_POSTFIX} as well as {@value #DESCRIPTION_POSTFIX}.
     * 
     * @param keyBase
     * @param position
     * @param urlStrategy
     */
    public MenuItem(String keyBase, int position, UrlResolvingStrategy urlStrategy) {

        this(keyBase, position, null, urlStrategy, new MenuItem[] {});
    }


    public MenuItem(String keyBase, int position, MenuItem... items) {

        this(keyBase, position, null, null, items);
    }


    /**
     * Creates a new {@link MenuItem} with the given {@code url} as target using the given {@code keyBase} to define
     * title and description. Uses the given {@code items} as sub menu items.
     * 
     * @param keyBase
     * @param position
     * @param url
     * @param items
     * @see #MenuItem(String, int, String)
     */
    public MenuItem(String keyBase, int position, String url, MenuItem... items) {

        this(keyBase, position, url, null, items);
    }


    /**
     * Creates a new {@link MenuItem} with the given {@code UrlResolvingStrategy} as target using the given {@code
     * keyBase} to define title and description. Uses the given {@code items} as sub menu items.
     * 
     * @param keyBase
     * @param position
     * @param urlStrategy
     * @param items
     * @see #MenuItem(String, int, String)
     */
    public MenuItem(String keyBase, int position, UrlResolvingStrategy urlStrategy, MenuItem... items) {

        this(keyBase, position, null, urlStrategy, items);
    }


    /**
     * Creates a new {@link MenuItem} without submenues.
     * 
     * @param title
     * @param desciption
     * @param position
     * @param url
     */
    public MenuItem(String title, String desciption, int position, String url) {

        this(title, desciption, position, url, null, new MenuItem[] {});

    }


    /**
     * Creates a new {@link MenuItem} without submenues.
     * 
     * @param title
     * @param desciption
     * @param position
     * @param urlStrategy
     */
    public MenuItem(String title, String desciption, int position, UrlResolvingStrategy urlStrategy) {

        this(title, desciption, position, null, urlStrategy, new MenuItem[] {});

    }


    /**
     * Creates a new {@link MenuItem} with the given {@link MenuItem}s as submenues.
     * 
     * @param title
     * @param description
     * @param position
     * @param url
     * @param items
     */
    public MenuItem(String title, String description, int position, String url, MenuItem... items) {

        this(title, description, position, url, null, items);
    }


    /**
     * Creates a new {@link MenuItem} with the given {@link MenuItem}s as submenues.
     * 
     * @param title
     * @param description
     * @param position
     * @param urlStrategy
     * @param items
     */
    public MenuItem(String title, String description, int position, UrlResolvingStrategy urlStrategy, MenuItem... items) {

        this(title, description, position, null, urlStrategy, items);
    }


    private MenuItem(String keyBase, int position, String url, UrlResolvingStrategy urlStrategy, MenuItem... items) {

        this(keyBase + TITLE_POSTFIX, keyBase + DESCRIPTION_POSTFIX, position, url, urlStrategy, items);
    }


    private MenuItem(String title, String description, int position, String url, UrlResolvingStrategy urlStrategy,
            MenuItem... items) {

        this.title = title;
        this.desciption = description;
        this.position = position;
        this.permissions = new ArrayList<String>();

        UrlResolvingStrategy strategy = urlStrategy;
        // if no strategy was given explicit we try to detect it
        if (urlStrategy == null) {
            if (url != null) {
                strategy = new SimpleUrlResolvingStrategy(url);
            } else if (items != null && items.length > 0) {
                strategy = new FirstSubMenuUrlResolvingStrategy();
            }
        }

        this.urlStrategy = strategy;

        if (this.urlStrategy == null) {
            throw new IllegalArgumentException(
                    "No UrlResolvingStrategy not given. Could not autodetect one (you must supply a strategy, a url or submenues).");
        }

        this.subMenues = new ArrayList<MenuItem>();

        if (items != null) {
            this.subMenues.addAll(Arrays.asList(items));

            for (MenuItem item : subMenues) {
                item.parent = this;
            }

            Collections.sort(this.subMenues);

        }
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
     * Adds some permissions to the {@link MenuItem} that determines who is allowed to see/access it.
     * 
     * @param permission
     * @return
     */
    public MenuItem add(String... permission) {

        this.permissions.addAll(Arrays.asList(permission));
        return this;
    }


    /**
     * Returns the permission required to access this {@link MenuItem}. Includes permissions from parent menu items,
     * too.
     * 
     * @return the permissions
     */
    public List<String> getPermissions() {

        if (isTopLevel()) {
            return permissions;
        }

        List<String> result = new ArrayList<String>();
        result.addAll(parent.getPermissions());

        for (String permission : permissions) {
            if (!result.contains(permission)) {
                result.add(permission);
            }
        }

        return Collections.unmodifiableList(result);
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


    /**
     * Adds the given {@link MenuItem}s as sub menu items.
     * 
     * @param subMenuItem
     * @return
     */
    public MenuItem withSub(MenuItem... subMenuItems) {

        for (MenuItem subMenuItem : subMenuItems) {

            subMenuItem.parent = this;
            this.subMenues.add(subMenuItem);
        }

        Collections.sort(this.subMenues);

        return this;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(MenuItem item) {

        return this.position - item.getPosition();
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

        MenuItem copy = new MenuItem(title, desciption, position, urlStrategy);

        if (subMenues != null) {

            MenuItem[] subCopy = new MenuItem[subMenues.size()];

            for (int i = 0; i < subMenues.size(); i++) {
                subCopy[i] = subMenues.get(i).deepCopy();
            }
            copy.withSub(subCopy);
        }

        copy.add(permissions.toArray(new String[0]));

        return copy;
    }


    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + desciption.hashCode();
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + position;
        result = prime * result + title.hashCode();
        return result;
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

        if (!desciption.equals(other.desciption))
            return false;

        if (!title.equals(other.title))
            return false;

        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        if (position != other.position)
            return false;

        return true;
    }

}
