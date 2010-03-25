package org.synyx.minos.umt.dao;

import java.util.List;

import org.synyx.hades.dao.ExtendedGenericDao;
import org.synyx.hades.dao.Query;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;


/**
 * Interface for data access object for <code>User</code>.
 * 
 * @author Oliver Gierke
 */
public interface UserDao extends ExtendedGenericDao<User, Long> {

    /**
     * Returns a user by its name.
     * 
     * @param username
     * @return
     */
    public User findByUsername(String username);


    /**
     * Returns a user by its email.
     * 
     * @param emailAddress String containing email of user
     * @return User instance, which has passed email, null if nothing was found
     */
    public User findByEmailAddress(String emailAddress);


    /**
     * Returns a list of all users of a given role.
     * 
     * @param role the role to get all users for.
     * @return list of all users of the given role.
     */
    @Query("select u from User u join u.roles r where r = ?")
    public List<User> findByRole(Role role);
}
