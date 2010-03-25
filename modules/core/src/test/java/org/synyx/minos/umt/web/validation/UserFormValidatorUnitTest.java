package org.synyx.minos.umt.web.validation;

import static org.mockito.Mockito.*;
import static org.synyx.minos.TestConstants.*;
import static org.synyx.minos.core.web.WebTestUtils.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.web.UserForm;


/**
 * Unit test for {@code UserValidator}.
 * 
 * @author Oliver Gierke
 */
@RunWith(MockitoJUnitRunner.class)
public class UserFormValidatorUnitTest {

    private UserFormValidator validator;
    @Mock
    private UserManagement userManagement;

    private Errors errors;

    private UserForm userForm;


    /**
     * Sets up mocks, configures the validator and creates a defualt {@code
     * UserForm} instance.
     */
    @Before
    public void setUp() {

        validator = new UserFormValidator();
        validator.setUserManagement(userManagement);

        userForm = BeanUtils.instantiateClass(UserForm.class);
        userForm.setId(0L);
        userForm.setUsername("username");
        userForm.setFirstname("Firstname");
        userForm.setLastname("Lastname");
        userForm.setEmailAddress("email@address.com");
        userForm.setNewPassword("password");
        userForm.setRepeatedPassword("password");
        userForm.setRoles(Arrays.asList(new Role("ADMIN")));
    }


    /**
     * Tests that the validator does not raise errors on a valid user instance.
     */
    @Test
    public void passesValidUser() {

        prepareErrorsAndExecute();

        verify(userManagement).getUserByEmail(userForm.getEmailAddress());
        verify(userManagement).getUser(userForm.getUsername());
        Assert.assertFalse("Found errors: " + errors, errors.hasErrors());
    }


    /**
     * Asserts that the validator rejects users without a username.
     */
    @Test
    public void rejectsUserWithoutUsername() {

        // Patch user invalid
        userForm.setUsername(null);

        prepareErrorsAndExecute();

        verify(userManagement).getUserByEmail(userForm.getEmailAddress());
        verify(userManagement).getUser(userForm.getUsername());
        assertContainsFieldErrorWithCode(errors, "username",
                UserFormValidator.USERNAME_EMPTY);
    }


    /**
     * Checks that a new user always has to have a password.
     */
    @Test
    public void rejectsNewUserWithoutPassword() {

        userForm.setId(null);
        userForm.setNewPassword(null);

        prepareErrorsAndExecute();

        verify(userManagement).getUserByEmail(userForm.getEmailAddress());
        verify(userManagement).getUser(userForm.getUsername());
        assertContainsFieldErrorWithCode(errors, "newPassword",
                UserFormValidator.PASSWORD_EMPTY);
    }


    /**
     * Checks that a new user always has to have a password.
     */
    @Test
    public void rejects_new_user_with_other_users_email() {

        userForm.setId(null);
        when(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .thenReturn(new User("dlinsin", userForm.getEmailAddress(), ""));

        prepareErrorsAndExecute();

        verify(userManagement).getUserByEmail(userForm.getEmailAddress());
        verify(userManagement).getUser(userForm.getUsername());
        assertContainsFieldErrorWithCode(errors, "emailAddress",
                UserFormValidator.EMAIL_ALREADY_EXISTS);
    }


    @Test
    public void reject_existing_user_with_other_users_email() {

        when(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .thenReturn(new User("dlinsin", userForm.getEmailAddress(), ""));

        prepareErrorsAndExecute();

        verify(userManagement).getUser(userForm.getUsername());
        assertContainsFieldErrorWithCode(errors, "emailAddress",
                UserFormValidator.EMAIL_ALREADY_EXISTS);
    }


    /**
     * Checks that not matching passwords are rejected.
     */
    @Test
    public void rejectsNotMatchingPasswords() {

        userForm.setRepeatedPassword("drowssap");

        prepareErrorsAndExecute();

        verify(userManagement).getUserByEmail(userForm.getEmailAddress());
        verify(userManagement).getUser(userForm.getUsername());
        assertContainsFieldErrorWithCode(errors, "repeatedPassword",
                UserFormValidator.PASSWORDS_DO_NOT_MATCH);
    }


    /**
     * Checks, that a new user with an existing username is rejected.
     */
    @Test
    public void rejectsNewUsersWithExistingUsername() {

        // Mark user new
        userForm.setId(null);

        // Expect lookup for username
        when(userManagement.getUser(userForm.getUsername())).thenReturn(USER);

        prepareErrorsAndExecute();

        verify(userManagement).getUserByEmail(userForm.getEmailAddress());
        assertContainsFieldErrorWithCode(errors, "username",
                UserFormValidator.USERNAME_ALREADY_EXISTS);
    }


    @Test
    public void rejectsFormAsUserName() throws Exception {

        for (String name : Arrays.asList("form", "FORM")) {

            userForm.setId(null);
            userForm.setUsername(name);

            prepareErrorsAndExecute();

            assertContainsFieldErrorWithCode(errors, "username",
                    UserFormValidator.USERNAME_ALREADY_EXISTS);
        }

        verify(userManagement, times(2)).getUserByEmail(
                userForm.getEmailAddress());
        verify(userManagement).getUser(userForm.getUsername());
    }


    @Test
    public void rejectsNoRoles() throws Exception {

        userForm.setRoles(new ArrayList<Role>());
        prepareErrorsAndExecute();

        verify(userManagement).getUserByEmail(userForm.getEmailAddress());
        verify(userManagement).getUser(userForm.getUsername());
        assertContainsFieldErrorWithCode(errors, "roles",
                UserFormValidator.ROLES_EMPTY);
    }


    /**
     * Creates a new {@code Errors} instance that binds the {@code UserForm}
     * instance and triggers validation.
     */
    private void prepareErrorsAndExecute() {

        errors = new BeanPropertyBindingResult(userForm, "user");
        validator.validate(userForm, errors);
    }
}
