package org.synyx.minos.umt.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.test.AbstractModuleIntegrationTest;
import org.synyx.minos.umt.dao.RoleDao;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Integration test validating correct work of <code>UserManagementImpl</code>, particularly testing security issues.
 * 
 * @author Oliver Gierke
 */
@Transactional
public class UserManagementIntegrationTest extends AbstractModuleIntegrationTest {

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private User user;
    private Role userRole;
    private Role adminRole;

    private static final String PASSWORD = "password";


    /**
     * Creates a simple user role and a user with that role in the database. Sets up an unsaved admin role.
     */
    @Before
    public void setUp() {

        SecurityContextHolder.getContext().setAuthentication(null);

        userRole = roleDao.findUserRole();
        adminRole = roleDao.findAdminRole();

        user = new User("username", "email@address.de", PASSWORD);
        user.addRole(userRole);
        userDao.save(user);

        user.setPassword(authenticationService.getEncryptedPasswordFor(user));
        userDao.saveAndFlush(user);
    }


    /**
     * Deauthenticates the user again.
     */
    @After
    public void tearDown() {

        SecurityContextHolder.getContext().setAuthentication(null);
    }


    /**
     * Tests, that invocations of user management methods are prevented if noboby is authenticated.
     */
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    @Ignore
    public void testSecurityInterceptorPreventsNonAuthenticatedAccess() {

        userManagement.save(user);
    }


    /**
     * Tests, that that calling {@code UserManagement#changePassword(User, String)} is allowed after authenticating the
     * user.
     */
    @Test
    @Ignore
    public void testSecurityInterceptorAllowsAuthenticatedAccess() {

        authenticateUser();

        userManagement.getUser(user.getUsername());
    }


    /**
     * Tests, that invocation of {@code UserManagement#save(Role)} with the dummy user (which has the role {@code
     * ROLE_USER}) raises an {@code AccessDeniedException}.
     */
    @Test(expected = AccessDeniedException.class)
    @Ignore
    public void testPreventsRoleCreationBySimpleUser() {

        authenticateUser();

        userManagement.save(adminRole);
    }


    /**
     * Asserts, that triggering a manipulating operation on the {@code UserManagement} component attaches auditing
     * information to the entity.
     */
    @Test
    @Ignore
    public void testFiresAdviceOnDaoSave() {

        authenticateUser();

        userManagement.save(user);

        Assert.assertEquals(user, user.getLastModifiedBy());

        Assert.assertNotNull(user.getCreatedDate());
        Assert.assertNotNull(user.getLastModifiedDate());
    }


    /**
     * Triggers authentication of the dummy user.
     */
    private void authenticateUser() {

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), PASSWORD);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
