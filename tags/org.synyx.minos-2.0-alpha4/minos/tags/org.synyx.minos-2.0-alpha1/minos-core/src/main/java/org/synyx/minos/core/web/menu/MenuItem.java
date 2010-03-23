package org.synyx.minos.core.web.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.synyx.minos.core.domain.User;



/**
 * Class to define a {@link MenuItem}. Allows definition of basic properties
 * like title, description and so on. {@link MenuItem}s can be ordered using the
 * {@code position} property. Title and description will be resolved via
 * {@link java.util.ResourceBundle}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MenuItem implements Comparable<MenuItem> {

    public static final String USER_PLACEHOLDER = "{username}";

    private static final String TITLE_POSTFIX = ".title";
    private static final String DESCRIPTION_POSTFIX = ".description";

    private String url;
    private String title;
    private String desciption;
    private int position;
    private MenuItem parent;
    private List<MenuItem> subMenues;
    private List<String> permissions;


    /**
     * Creates a new {@link MenuItem} with the given {@code url} as target using
     * the given {@code keyBase} to define title and description.
     * {@link #TITLE_POSTFIX} and {@link #DESCRIPTION_POSTFIX} will be appended
     * accordingly and default to {@value #TITLE_POSTFIX} as well as
     * {@value #DESCRIPTION_POSTFIX}.
     * @param keyBase
     * @param position
     * @param url
     */
    public MenuItem(String keyBase, int position, String url) {

        this(keyBase, position, url, new MenuItem[] {});
    }


    public MenuItem(String keyBase, int position, MenuItem... items) {

        this(keyBase, position, null, items);
    }


    /**
     * Creates a new {@link MenuItem} with the given {@code url} as target using
     * the given {@code keyBase} to define title and description. Uses the given
     * {@code items} as sub menu items.
     * @param keyBase
     * @param position
     * @param url
     * @param items
     * 
     * @see #MenuItem(String, int, String)
     */
    public MenuItem(String keyBase, int position, String url, MenuItem... items) {

        this(keyBase + TITLE_POSTFIX, keyBase + DESCRIPTION_POSTFIX, position,
                url, items);
    }


    /**
     * Creates a new {@link MenuItem} without submenues.
     * @param title
     * @param desciption
     * @param position
     * @param url
     */
    public MenuItem(String title, String desciption, int position, String url) {

        this.url = url;
        this.title = title;
        this.desciption = desciption;
        this.position = position;
        this.subMenues = new ArrayList<MenuItem>();

        this.permissions = new ArrayList<String>();
    }


    /**
     * Creates a new {@link MenuItem} with the given {@link MenuItem}s as
     * submenues.
     * @param title
     * @param description
     * @param position
     * @param url
     * @param items
     */
    public MenuItem(String title, String description, int position, String url,
            MenuItem... items) {

        this(title, description, position, url);

        boolean hasUrl = StringUtils.hasText(url);
        boolean hasSubMenuItem = items != null && items.length > 0;

        Assert.isTrue(hasUrl || hasSubMenuItem);

        this.subMenues.addAll(Arrays.asList(items));

        for (MenuItem item : subMenues) {
            item.parent = this;
        }

        Collections.sort(this.subMenues);
    }


    /**
     * Returns the URL the {@link MenuItem} shall link to. Replaces eventually
     * contained placeholders for the user with the given {@link User}s
     * username. Cleans up the URL if no {@link User} is given.
     * 
     * @param user the user to create the menu for. Can be {@literal null} if no
     *            user is authenticated
     * @return the url
     */
    public String getUrl(User user) {

        String username = null == user ? "" : user.getUsername();
        return null == url ? subMenues.get(0).getUrl(user) : url.replace(
                USER_PLACEHOLDER, username).replace("//", "/");
    }


    /**
     * Returns the title of the {@link MenuItem}. Will be resolved against a
     * resource bundle.
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
     * Adds some permissions to the {@link MenuItem} that determines who is
     * allowed to see/access it.
     * 
     * @param permission
     * @return
     */
    public MenuItem add(String... permission) {

        this.permissions.addAll(Arrays.asList(permission));
        return this;
    }


    /**
     * Returns the permission required to access this {@link MenuItem}. Includes
     * permissions from parent menu items, too.
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
     * Adds the given {@link MenuItem} as sub menu item.
     * 
     * @param subMenuItem
     * @return
     */
    public MenuItem withSub(MenuItem subMenuItem) {

        subMenuItem.parent = this;
        this.subMenues.add(subMenuItem);
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
        builder.append(", URL: ").append(url);
        builder.append(", Position: ").append(position);
        builder.append(", Permissions: ").append(
                StringUtils.collectionToCommaDelimitedString(getPermissions()));

        return builder.toString();
    }
}
