package org.synyx.minos.core.domain;

import org.synyx.hades.domain.auditing.AbstractAuditable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;


/**
 * Domain class for a role.
 *
 * @author  Oliver Gierke
 */
@Entity
public class Role extends AbstractAuditable<User, Long> implements Comparable<Role> {

    private static final long serialVersionUID = 71349340935123L;

    private static final String PREFIX = "ROLE_";

    /**
     * System role for administrators
     */
    public static final String ADMIN_NAME = "ADMIN";

    /**
     * System role for users
     */
    public static final String USER_NAME = "USER";

    private static final Collection<String> SYSTEM_ROLES = Arrays.asList(ADMIN_NAME, USER_NAME);

    private static final int SHORTVERSION_LENGTH = 64;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 2048, nullable = true)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> permissions;

    /** Empty constructor. */
    public Role() {

        this.permissions = new HashSet<String>();
    }


    /**
     * Constructor setting a {@link Role} name instantly.
     *
     * @param  name  the {@link Role} name
     */
    public Role(String name) {

        this();
        setName(name);
    }

    /**
     * Returns the {@link Role} name.
     *
     * @return  the {@link Role} name
     */
    public String getName() {

        return name;
    }


    /**
     * Sets the {@link Role}s name. Will prevent empty {@link String}s and {@literal null} values and turn the name into
     * uppercase letters.
     *
     * @param  name  the name to set
     */
    public void setName(String name) {

        this.name = name != null ? name.toUpperCase() : name;
    }


    /**
     * Returns all permissions assigned to this {@link Role}.
     *
     * @return  an unmodifiable set of all permissions assigned to this {@link Role}
     */
    @SuppressWarnings("unchecked")
    public Set<String> getPermissions() {

        return (Set<String>) (permissions == null ? Collections.emptySet() : Collections.unmodifiableSet(permissions));
    }


    /**
     * Sets the permissions tied to this {@link Role}.
     *
     * @param  permissions  the permissions to tie to this {@link Role}
     */
    public void setPermissions(Set<String> permissions) {

        this.permissions = permissions;
    }


    /**
     * Adds the given permissions to the {@link Role}.
     *
     * @param  permission  the permissions to add
     *
     * @return  the {@link Role} itself
     */
    public Role add(String... permission) {

        this.permissions.addAll(Arrays.asList(permission));

        return this;
    }


    /**
     * Removes all given permissions from that {@link Role}.
     *
     * @param  permission  the permissions to remove
     *
     * @return  the {@link Role} itself
     */
    public Role remove(String... permission) {

        this.permissions.removeAll(Arrays.asList(permission));

        return this;
    }


    @Override
    public String toString() {

        return PREFIX + getName();
    }


    /**
     * Returns whether the role is a system role. This will have further implications like not being allowed to be
     * deleted a.s.o.
     *
     * @return  whether the role is a system role
     */
    public boolean isSystemRole() {

        return SYSTEM_ROLES.contains(this.getName());
    }


    /**
     * Extracts the actual name from the given {@link Role} name.
     *
     * @param  prefixedName  a {@link Role} name, possibly in prefix form
     *
     * @return  the {@link Role} name, possibly stripped of the prefix
     */
    public static String stripPrefix(String prefixedName) {

        if (null == prefixedName || !prefixedName.startsWith(PREFIX)) {
            return prefixedName;
        }

        return prefixedName.substring(PREFIX.length());
    }


    @Override
    public int compareTo(Role o) {

        if (this.isSystemRole() && !o.isSystemRole()) {
            return -1;
        } else if (o.isSystemRole() && !this.isSystemRole()) {
            return 1;
        }

        return getName().compareTo(o.getName());
    }


    public void setDescription(String description) {

        this.description = description;
    }


    public String getDescription() {

        return description;
    }


    public String getDescriptionShortVersion() {

        if (description == null || description.length() <= SHORTVERSION_LENGTH) {
            return description;
        }

        return description.substring(0, SHORTVERSION_LENGTH - 3) + "...";
    }
}
