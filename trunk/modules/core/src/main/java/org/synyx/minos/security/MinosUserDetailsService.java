package org.synyx.minos.security;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.UserDao;



/**
 * Security adapter for Spring Security Framework. Allows the security framework
 * to authenticate against Minos <code>UserDao</code>. See documentation of
 * Spring Security for details.
 * 
 * @author Oliver Gierke
 * @see UserDetailsService
 */
@Transactional(readOnly = true)
public class MinosUserDetailsService implements UserDetailsService {

    private UserDao userDao;


    /**
     * Setter to inject <code>UserDao</code>.
     * 
     * @param userDao the userDao to set
     */
    @Required
    public void setUserDao(UserDao userDao) {

        this.userDao = userDao;
    }


    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.security.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        // Retrieve user
        User user = userDao.findByUsername(username);

        if (null == user) {
            throw new UsernameNotFoundException("User not found!", username);
        }

        return new MinosUserDetails(user);
    }
}