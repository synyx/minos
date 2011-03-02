package org.synyx.minos.umt.dao;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;

import java.util.List;


/**
 * Interface for data access object for <code>User</code>.
 *
 * @author Oliver Gierke
 */
public interface UserDao extends GenericDao<User, Long> {

    /**
     * Returns a user by its name.
     *
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
     * Returns users by their email.
     *
     * @param emailAddress String containing email of user
     * @return all User instances, which has passed email, empty {@link List} if none was found
     */
    List<User> findByEmailAddress(String emailAddress);


    /**
     * Returns a list of all users of a given role.
     *
     * @param role the role to get all users for.
     * @return list of all users of the given role.
     */
    @Query("select u from User u join u.roles r where r = ?")
    List<User> findByRole(Role role);


    /**
     * Returns a {@link Page} of (all) users of a given role.
     *
     * @param role the role to get all users for.
     * @return list of all users of the given role.
     */
    @Query("select u from User u join u.roles r where r = ?")
    Page<User> findByRole(Pageable pageable, Role role);
}
