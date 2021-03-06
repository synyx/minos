package org.synyx.minos.core.security;

import org.synyx.minos.core.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Implements convenience methods that are typically used in concrete {@link AuthenticationService}s.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractAuthenticationService implements AuthenticationService {

    private List<String> permissions;

    /**
     * Inject all {@link PermissionAware}s to assemble all available {@link Permission}s from.
     *
     * @param permissionDeclarators the permissionDeclarators to set
     */
    public void setModulePermissions(List<PermissionAware> modulePermissions) {

        this.permissions = new ArrayList<String>();

        for (PermissionAware declarator : modulePermissions) {
            if (this.equals(declarator)) {
                continue;
            }

            this.permissions.addAll(declarator.getPermissions());
        }
    }


    @Override
    public boolean isCurrentUser(User user) {

        User currentUser = getCurrentUser();

        return null != currentUser && currentUser.equals(user);
    }


    @Override
    public boolean isCurrentUser(String username) {

        User currentUser = getCurrentUser();

        return null != currentUser && currentUser.getUsername().equals(username);
    }


    @Override
    public Collection<String> getPermissions() {

        return permissions;
    }


    @Override
    public boolean hasAnyPermission(Collection<String> permissions) {

        return hasPermissions(permissions);
    }


    @Override
    public boolean hasAllPermissions(Collection<String> permissions) {

        if (null == permissions || permissions.isEmpty()) {
            return true;
        }

        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                return false;
            }
        }

        return true;
    }


    private boolean hasPermission(String permission) {

        return hasAnyPermission(Arrays.asList(permission));
    }


    /**
     * Returns whether the currently authenticated {@link User} has any of the given permissions.
     *
     * @param permissions
     * @return
     */
    protected abstract boolean hasPermissions(Collection<String> permissions);
}
