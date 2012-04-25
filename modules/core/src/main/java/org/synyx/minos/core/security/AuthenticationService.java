package org.synyx.minos.core.security;

import org.synyx.minos.core.domain.Password;
import org.synyx.minos.core.domain.User;

import java.util.Collection;
import org.synyx.binding.user.CurrentUserProvider;


/**
 * Interface abstracting access to authentication information.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface AuthenticationService extends PermissionAware, CurrentUserProvider<User> {

    /**
     * Returns whether the {@link User} with the given username is currently authenticated.
     *
     * @param username
     * @return
     */
    boolean isCurrentUser(String username);


    /**
     * Returns whether the given {@link User} is currently authenticated.
     *
     * @param user
     * @return
     */
    boolean isCurrentUser(User user);


    /**
     * Returns if the current {@link User} as any of the given permissions.
     *
     * @param permissions
     * @return
     */
    boolean hasAnyPermission(Collection<String> permissions);


    /**
     * Returns whether the current {@link User} has all the permissions given.
     *
     * @param permissions
     * @return
     */
    boolean hasAllPermissions(Collection<String> permissions);


    /**
     * Returns a possibly encrypted password for the given {@link User}. Encryption dependes on the security
     * configuration of the underlying implementation and might return the plain password of the user as well. Does not
     * apply the possibly encrypted password to the given User instance
     *
     * @param user
     * @return
     */
    Password getEncryptedPasswordFor(User user);
}
