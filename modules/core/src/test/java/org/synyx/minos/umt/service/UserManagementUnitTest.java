package org.synyx.minos.umt.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.RoleDao;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Unit test for {@code UserManagementImpl}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagementUnitTest {

    private UserManagementImpl userManagement;

    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private PasswordCreator passwordCreator;

    private User user;
    private User oldUser;

    private String password;
    private String encryptedPassword;

    private String newPassword;
    private String encryptedNewPassword;


    @Before
    public void setUp() {

        userManagement =
                new UserManagementImpl(userDao, roleDao, authenticationService,
                        passwordCreator);

        // Prepare dummy values
        password = "password";
        encryptedPassword = "drowssap";

        newPassword = "newpassword";
        encryptedNewPassword = "drowssapwen";

        user = createUser();
        oldUser = createUser();
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
     * Asserts that new users get a password generated if none has been set
     * originally.
     */
    @Test
    public void testCreatesPasswordIfNoneSet() {

        when(authenticationService.getEncryptedPasswordFor(user)).thenReturn(
                encryptedPassword);

        userManagement.save(user);

        verify(passwordCreator, times(1)).generatePassword();
        assertNotNull(user.getPassword());
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

        when(authenticationService.isCurrentUser(user)).thenReturn(true);

        userManagement.delete(user);
    }


    @Test
    public void preventsNotPermittedValues() throws Exception {

        User notPermittedUser = new User("foobar", "email@address.de", null);
        user.setId(1L);
        notPermittedUser.setId(user.getId());
        notPermittedUser.addRole(new Role("foobar"));
        notPermittedUser.setActive(false);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        when(userDao.readByPrimaryKey(user.getId())).thenReturn(user);

        userManagement.saveUserAccount(notPermittedUser);

        verify(userDao).save(argument.capture());
        assertEquals(argument.getValue().getUsername(), "username");
        assertEquals(argument.getValue().getRoles().size(), 0);
        assertTrue(argument.getValue().isActive());
    }


    /**
     * Excpect the {@code EncryptionProvider} to be asked for encoding the
     * passwords.
     */
    private void expectEncryptionProviderToBeUsed(String password) {

        // Expect the encryption provider to return the encrypted password
        when(authenticationService.getEncryptedPasswordFor((User) anyObject()))
                .thenReturn(password);
    }


    /**
     * Asserts, that saving the currently configured {@code User} instance
     * results in the user having the given password.
     * 
     * @param newPassword
     */
    private void assertNewPassword(String newPassword) {

        expectEncryptionProviderToBeUsed(encryptedNewPassword);

        userManagement.save(user);

        Assert.assertEquals(newPassword, user.getPassword());
    }


    /**
     * Expect a DAO lookup for the old state of the user.
     */
    private void expectUserLookup() {

        when(userDao.readByPrimaryKey(user.getId())).thenReturn(oldUser);
    }

}