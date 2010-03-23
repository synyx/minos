package org.synyx.minos.umt.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.Modules;


/**
 * Interface for user managment module.
 * 
 * @author Oliver Gierke
 */
@Module(Modules.UMT)
@RolesAllowed("ROLE_USER")
public interface UserManagement {

    /**
     * Creates a new user. Generates a new password for her if she has none.
     * 
     * @param user
     */
    void save(User user);


    /**
     * Saves a role.
     * 
     * @param role
     */
    @RolesAllowed("ROLE_ADMIN")
    void save(Role role);


    /**
     * Deletes a user.
     * 
     * @param id
     * @throws UserNotFoundException if an ivalid id or {@code null}) was given
     */
    @RolesAllowed("ROLE_ADMIN")
    void deleteUser(Long id) throws UserNotFoundException;


    /**
     * Returns all users.
     * 
     * @return
     */
    List<User> getUsers();


    /**
     * Returns a page of users.
     * 
     * @param pageable
     * @return
     */
    Page<User> getUsers(Pageable pageable);


    /**
     * Returns a user identified by its id.
     * 
     * @param id
     * @return the user with the given id or {@code null} if no user can be
     *         found.
     */
    User getUser(Long id);


    /**
     * Returns the user with the given username or {@code null} if none found.
     * 
     * @param username
     * @return
     */
    User getUser(String username);


    /**
     * Returns the user with passed email
     * 
     * @param email String containing email of user
     * @return {@link User} instance with passed email, null if no user exists
     *         with passed email
     */
    User getUserByEmail(String email);


    /**
     * Returns whether a {@code User} with the given id already exists.
     * 
     * @param id
     * @return
     */
    boolean exists(Long id);


    /**
     * Returns all roles.
     * 
     * @return
     */
    List<Role> getRoles();


    /**
     * Returns a role with the given id.
     * 
     * @param id
     */
    Role getRole(Long id);


    /**
     * Deletes the given role.
     * 
     * @param role
     */
    void deleteRole(Role role);
}
