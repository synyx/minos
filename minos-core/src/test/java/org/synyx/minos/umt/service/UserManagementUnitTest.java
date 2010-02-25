package org.synyx.minos.umt.service;

import static org.easymock.EasyMock.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.RoleDao;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Unit test for {@code UserManagementImpl}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UserManagementUnitTest {

    private UserManagementImpl userManagement;

    private UserDao userDao;
    private RoleDao roleDao;
    private AuthenticationService authenticationService;
    private PasswordCreator passwordCreator;

    private User user;
    private User oldUser;

    private String password;
    private String encryptedPassword;

    private String newPassword;
    private String encryptedNewPassword;


    @Before
    public void setUp() {

        userManagement = new UserManagementImpl();

        userDao = createNiceMock(UserDao.class);
        roleDao = createNiceMock(RoleDao.class);
        authenticationService = createNiceMock(AuthenticationService.class);
        passwordCreator = createNiceMock(PasswordCreator.class);

        userManagement.setUserDao(userDao);
        userManagement.setRoleDao(roleDao);
        userManagement.setPasswordCreator(passwordCreator);

        // Prepare dummy values
        password = "password";
        encryptedPassword = "drowssap";

        newPassword = "newpassword";
        encryptedNewPassword = "drowssapwen";

        user = createUser();
        oldUser = createUser();

        reset(getMocks());
    }


    /**
     * Creates a dummy {@code User} instance.
     * 
     * @return
     */
    private User createUser() {

        User user = new User("username", "email@address.de", null);

        return user;
    }


    /**
     * Returns all mocks.
     * 
     * @return
     */
    private Object[] getMocks() {

        return new Object[] { userDao, roleDao, authenticationService,
                passwordCreator };
    }


    /**
     * Asserts that new users get a password generated if none has been set
     * originally.
     */
    @Test
    public void testCreatesPasswordIfNoneSet() {

        expect(passwordCreator.generatePassword()).andReturn(encryptedPassword);
        replay(getMocks());

        userManagement.save(user);

        verify(getMocks());
        Assert.assertNotNull(user.getPassword());
    }


    /**
     * Tests, that the service encrypts password of new users.
     */
    @Test
    public void testEncryptsPasswordsOfNewUsers() {

        // Mark user as new having a password selected
        user.setPassword(password);

        assertNewPassword(encryptedNewPassword);
    }


    /**
     * Tests, that the service encrypts the new password of a user on password
     * change.
     */
    @Test
    public void testEncryptsNewPassword() {

        // Let user be "not new"
        user.setId(1L);
        user.setPassword(newPassword);

        oldUser.setPassword(encryptedPassword);

        expectUserLookup();
        assertNewPassword(encryptedNewPassword);
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsAttemptToDeleteCurrentlyLoggedInUser()
            throws UserNotFoundException {

        expect(authenticationService.isCurrentUser(user)).andReturn(true)
                .once();
        replay(authenticationService);

        userManagement.setAuthenticationService(authenticationService);
        userManagement.deleteUser(user.getId());
    }


    /**
     * Excpect the {@code EncryptionProvider} to be asked for encoding the
     * passwords.
     */
    private void expectEncryptionProviderToBeUsed(String password) {

        // Expect the encryption provider to return the encrypted password
        expect(
                authenticationService
                        .getEncryptedPasswordFor((User) anyObject()))
                .andReturn(password).anyTimes();

        userManagement.setAuthenticationService(authenticationService);
    }


    /**
     * Asserts, that saving the currently configured {@code User} instance
     * results in the user having the given password.
     * 
     * @param newPassword
     */
    private void assertNewPassword(String newPassword) {

        expectEncryptionProviderToBeUsed(encryptedNewPassword);
        replay(getMocks());

        userManagement.save(user);

        Assert.assertEquals(newPassword, user.getPassword());
        verify(getMocks());
    }


    /**
     * Expect a DAO lookup for the old state of the user.
     */
    private void expectUserLookup() {

        expect(userDao.readByPrimaryKey(user.getId())).andReturn(oldUser)
                .anyTimes();
    }
}