package org.synyx.minos.core.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.synyx.minos.core.domain.User;


/**
 * Implements convenience methods that are typically used in concrete
 * {@link AuthenticationService}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractAuthenticationService implements
        AuthenticationService {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.core.authentication.AuthenticationService#isCurrentUser
     * (org.synyx.minos.core.domain.User)
     */
    @Override
    public boolean isCurrentUser(User user) {

        User currentUser = getCurrentUser();
        return null != currentUser && currentUser.equals(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.core.authentication.AuthenticationService#isCurrentUser
     * (java.lang.String)
     */
    @Override
    public boolean isCurrentUser(String username) {

        User currentUser = getCurrentUser();
        return null != currentUser
                && currentUser.getUsername().equals(username);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.authentication.AuthenticationService#hasPermission
     * (java.util.Collection)
     */
    @Override
    public boolean hasAnyPermission(Collection<String> permissions) {

        return hasPermissions(permissions);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.core.authentication.AuthenticationService#hasAllPermissions
     * (java.util.Collection)
     */
    @Override
    public boolean hasAllPermissions(Collection<String> permissions) {

        if (null == permissions || permissions.isEmpty()) {
            return false;
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
     * Returns whether the currently authenticated {@link User} has any of the
     * given permissions.
     * 
     * @param permissions
     * @return
     */
    protected abstract boolean hasPermissions(Collection<String> permissions);
}
