package org.synyx.minos.umt.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;


/**
 * Interface for user managment module.
 * 
 * @author Oliver Gierke
 */
@RolesAllowed("ROLE_USER")
public interface UserManagement {

    /**
     * Creates a new user. Generates a new password for her if she has none.
     * 
     * 
     * @param user
     * @param forcePasswordEncryption if true the password of the user will be encrypted in any case
     */
    void save(User user, boolean forcePasswordEncryption);


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
     * Deletes the given {@link User}.
     * 
     * @param id
     * @throws UserNotFoundException if the given {@link User} was not found or
     *             {@literal null} was given
     */
    @RolesAllowed("ROLE_ADMIN")
    void delete(User user) throws UserNotFoundException;


    /**
     * Returns all {@link User}s.
     * 
     * @return
     */
    List<User> getUsers();


    /**
     * Returns a {@link Page} of {@link User}s.
     * 
     * @param pageable
     * @return
     */
    Page<User> getUsers(Pageable pageable);


    /**
     * Returns a {@link User} with the given id.
     * 
     * @param id
     * @return the user with the given id or {@literal null} if no user can be
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
     * Returns all {@link Role}s.
     * 
     * @return
     */
    List<Role> getRoles();


    /**
     * Returns a {@link Role} with the given id.
     * 
     * @param id
     */
    Role getRole(Long id);


    /**
     * Returns the {@link Role} with the given name.
     * 
     * @param name
     * @return
     */
    Role getRole(String name);


    /**
     * Deletes the given role.
     * 
     * @param role
     */
    void deleteRole(Role role);
}
