package org.synyx.minos.core.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Security adapter for Spring Security Framework. Allows the security framework to authenticate against Minos <code>
 * UserDao</code>. See documentation of Spring Security for details.
 *
 * @author  Oliver Gierke
 * @see  UserDetailsService
 */
@Transactional(readOnly = true)
public class MinosUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    /**
     * Creates a new {@link MinosUserDetailsService}.
     *
     * @param  userDao  the {@link UserDao} instance to fetch users from
     */
    public MinosUserDetailsService(UserDao userDao) {

        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        // Retrieve user
        User user = userDao.findByUsername(username);

        if (null == user) {
            throw new UsernameNotFoundException("User not found!", username);
        }

        return new MinosUserDetails(user);
    }
}
