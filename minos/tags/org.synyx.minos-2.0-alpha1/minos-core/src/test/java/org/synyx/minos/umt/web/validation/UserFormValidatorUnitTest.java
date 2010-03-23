package org.synyx.minos.umt.web.validation;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.synyx.minos.TestConstants.USER;
import static org.synyx.minos.core.web.WebTestUtils.assertContainsFieldErrorWithCode;

import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class UserFormValidatorUnitTest {

    private UserFormValidator validator;
    private UserManagement userManagement;

    private Errors errors;

    private UserForm userForm;


    /**
     * Sets up mocks, configures the validator and creates a defualt {@code
     * UserForm} instance.
     */
    @Before
    public void setUp() {

        userManagement = EasyMock.createNiceMock(UserManagement.class);

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

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(null);
        expect(userManagement.getUser(userForm.getUsername())).andReturn(null);
        replay(userManagement);

        prepareErrorsAndExecute();
        Assert.assertFalse("Found errors: " + errors, errors.hasErrors());
    }


    /**
     * Asserts that the validator rejects users without a username.
     */
    @Test
    public void rejectsUserWithoutUsername() {

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(null);
        expect(userManagement.getUser(userForm.getUsername())).andReturn(null);
        replay(userManagement);

        // Patch user invalid
        userForm.setUsername(null);

        prepareErrorsAndExecute();

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

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(null);
        expect(userManagement.getUser(userForm.getUsername())).andReturn(null);
        replay(userManagement);

        prepareErrorsAndExecute();

        assertContainsFieldErrorWithCode(errors, "newPassword",
                UserFormValidator.PASSWORD_EMPTY);

        verify(userManagement);
    }


    /**
     * Checks that a new user always has to have a password.
     */
    @Test
    public void rejects_new_user_with_other_users_email() {

        userForm.setId(null);

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(new User("dlinsin", userForm.getEmailAddress(), ""));
        expect(userManagement.getUser(userForm.getUsername())).andReturn(null);
        replay(userManagement);

        prepareErrorsAndExecute();

        assertContainsFieldErrorWithCode(errors, "emailAddress",
                UserFormValidator.EMAIL_ALREADY_EXISTS);

        verify(userManagement);
    }


    @Test
    public void reject_existing_user_with_other_users_email() {

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(new User("dlinsin", userForm.getEmailAddress(), ""));
        expect(userManagement.getUser(userForm.getUsername())).andReturn(null);
        replay(userManagement);
        prepareErrorsAndExecute();
        assertContainsFieldErrorWithCode(errors, "emailAddress",
                UserFormValidator.EMAIL_ALREADY_EXISTS);
        verify(userManagement);
    }


    /**
     * Checks that not matching passwords are rejected.
     */
    @Test
    public void rejectsNotMatchingPasswords() {

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(null);
        expect(userManagement.getUser(userForm.getUsername())).andReturn(null);
        replay(userManagement);

        userForm.setRepeatedPassword("drowssap");

        prepareErrorsAndExecute();

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

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(null);
        // Expect lookup for username
        EasyMock.expect(userManagement.getUser(userForm.getUsername()))
                .andReturn(USER).once();
        EasyMock.replay(userManagement);

        prepareErrorsAndExecute();

        assertContainsFieldErrorWithCode(errors, "username",
                UserFormValidator.USERNAME_ALREADY_EXISTS);

        EasyMock.verify(userManagement);
    }


    @Test
    public void rejectsFormAsUserName() throws Exception {

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(null);
        EasyMock.expect(userManagement.getUser(userForm.getUsername()))
                .andReturn(null).anyTimes();
        EasyMock.replay(userManagement);

        for (String name : Arrays.asList("form", "FORM")) {

            userForm.setId(null);
            userForm.setUsername(name);

            prepareErrorsAndExecute();

            assertContainsFieldErrorWithCode(errors, "username",
                    UserFormValidator.USERNAME_ALREADY_EXISTS);
        }

        EasyMock.verify(userManagement);
    }


    @Test
    public void rejectsNoRoles() throws Exception {

        expect(userManagement.getUserByEmail(userForm.getEmailAddress()))
                .andReturn(null);
        expect(userManagement.getUser(userForm.getUsername())).andReturn(null);
        replay(userManagement);

        userForm.setRoles(new ArrayList<Role>());
        prepareErrorsAndExecute();

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
