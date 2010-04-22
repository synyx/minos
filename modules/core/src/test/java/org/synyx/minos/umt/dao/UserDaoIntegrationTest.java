package org.synyx.minos.umt.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.test.AbstractDaoIntegrationTest;


/**
 * Integration test for <code>UserDao</code>.
 * 
 * @author Oliver Gierke
 */
@Transactional
public class UserDaoIntegrationTest extends AbstractDaoIntegrationTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private User user;


    @Before
    public void setUp() {

        user = new User("myusername", "email@address.de", "password");
        userDao.save(user);
    }


    /**
     * Tests, that <code>findByUsername</code> calls the query <code>User.findByUsername</code> and returns the
     * reference user.
     */
    @Test
    public void testFindByUsernameQuery() {

        User refUser = userDao.findByUsername(user.getUsername());
        Assert.assertEquals(user, refUser);
    }


    /**
     * Tests, that saving a user with a non-existing role is not allowed.
     */
    @Test(expected = DataAccessException.class)
    public void testDoesNotCreateNonExistingRoles() {

        Role role = new Role("FOOBAR");
        user.addRole(role);

        userDao.saveAndFlush(user);
    }


    /**
     * Tests, that <code>findByEmailAddress</code> calls the query <code>User.findByEmailAddress</code> and returns the
     * reference user.
     */
    @Test
    public void find_by_emailAddress() {

        List<User> refUser = userDao.findByEmailAddress(user.getEmailAddress());
        Assert.assertTrue(refUser.contains(user));
    }


    @Test
    public void findsUsersByRoleCorrectly() throws Exception {

        Role adminRole = roleDao.save(new Role(Role.ADMIN_NAME));
        Role userRole = roleDao.save(new Role(Role.USER_NAME));

        user.addRole(userRole);

        User admin = new User("admin", "admin@synyx.org", "password");
        admin.addRole(adminRole);
        admin = userDao.save(admin);

        List<User> users = userDao.findByRole(userRole);
        assertTrue(users.contains(user));
        assertFalse(users.contains(admin));

        List<User> admins = userDao.findByRole(adminRole);
        assertTrue(admins.contains(admin));
        assertFalse(admins.contains(user));
    }
}
