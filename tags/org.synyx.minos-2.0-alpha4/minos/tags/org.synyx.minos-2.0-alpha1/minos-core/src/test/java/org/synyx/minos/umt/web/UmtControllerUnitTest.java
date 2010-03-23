package org.synyx.minos.umt.web;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.synyx.minos.TestConstants.*;
import static org.synyx.minos.core.web.WebTestUtils.*;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.PageImpl;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.service.UserNotFoundException;


/**
 * Unit test for {@code UmtController}. TODO: extract message assertions into
 * utility class
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UmtControllerUnitTest {

    // Primitive constants
    private static final Long ID = 5L;
    private static final String USERNAME = "username";

    // The controller to test
    private UmtController controller;

    // Dependencies and mocks
    private UserManagement userManagement;
    private Validator validator;
    private Errors errors;

    // Basic data
    private Model model;
    private SessionStatus sessionStatus;
    private UserForm userForm;


    /**
     * Initializes mocks and prepopulates the controller to test.
     */
    @Before
    public void setUp() {

        userManagement = createNiceMock(UserManagement.class);

        // Create basic validator mock setup
        validator = createNiceMock(Validator.class);
        expect(validator.supports(UserForm.class)).andReturn(true).anyTimes();

        // Create mock for errors
        errors = createNiceMock(Errors.class);

        // Setup controller
        controller = new UmtController();
        controller.setUserManagement(userManagement);
        controller.setValidator(validator);

        // Create basic form instance
        userForm = new UserForm();
        userForm.setId(ID);
        userForm.setUsername(USERNAME);

        // Prepare data structures needed
        model = new ExtendedModelMap();
        sessionStatus = new SimpleSessionStatus();
    }


    /**
     * Returns all mocks.
     * 
     * @return
     */
    public Object[] getMocks() {

        return new Object[] { userManagement, validator, errors };
    }


    /**
     * Asserts that the request for the users binds the user list to the model
     * that is returned by the user management service.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void putsUsersIntoModelOnRequestToUserList() {

        Page<User> referenceUsers = new PageImpl<User>(Collections.EMPTY_LIST);

        // Prepare user management
        expect(userManagement.getUsers(null)).andReturn(referenceUsers);
        replay(userManagement);

        controller.getUsers(null, model, USER);

        verify(userManagement);

        assertThat(model.asMap().get(UmtController.USERS_KEY),
                is(notNullValue()));
    }


    /**
     * Asserts, that a request to delete a user without giving an id results in
     * a redirect request to the list users page as well as produces an error
     * message.
     */
    @Test
    public void rejectsMissingUserIdOnDelete() {

        String viewName = controller.deleteUser(null, model, null);

        assertRedirectsToUserList(viewName);
        assertErrorMessage(model);
    }


    /**
     * Asserts that a successful deletion is confirmed by a success message
     * containing the username.
     * 
     * @throws UserNotFoundException
     */
    @Test
    public void confirmsSuccessfulDeletion() throws UserNotFoundException {

        expect(userManagement.getUser(ID)).andReturn(USER).anyTimes();
        userManagement.deleteUser(ID);
        expectLastCall().once();
        replay(userManagement);

        String viewName = controller.deleteUser(ID, model, null);

        assertRedirectsToUserList(viewName);
        assertSuccessMessageWithArguments(model, ID);
        verify(userManagement);
    }


    /**
     * Asserts that the controller prepopulates a blank user form for the edit
     * form if no id is given.
     */
    @Test
    public void createsBlankFormIfNoIdGiven() {

        String viewName = controller.showEmptyForm(model);

        Assert.assertEquals("umt/user", viewName);

        UserForm user =
                assertContains(model, UmtController.USER_KEY, UserForm.class);
        Assert.assertTrue(user.isNew());
    }


    /**
     * Asserts that the controller rejects invalid usernames and redirects to
     * the user list instead.
     */
    @Test
    public void rejectsInvalidUsernameForEdit() {

        expect(userManagement.getUser(ID)).andReturn(null);
        replay(userManagement);

        String viewName = controller.setupForm(ID, model);

        assertErrorMessageWithArguments(model, ID);
        assertRedirectsToUserList(viewName);

        verify(userManagement);
    }


    /**
     * Asserts that the controller puts the user into the model that is returned
     * by the {@code UserManagement} on a request with a valid username.
     */
    @Test
    public void putsUserToEditIntoModel() {

        User user = new User(USERNAME, "email@address.com", "password");
        user.setId(ID);

        expect(userManagement.getUser(ID)).andReturn(user);
        replay(userManagement);

        String viewName = controller.setupForm(ID, model);

        // Assert correct view
        Assert.assertEquals("umt/user", viewName);

        // Assert model
        UserForm userForm =
                assertContains(model, UmtController.USER_KEY, UserForm.class);
        Assert.assertEquals(user, userForm.getDomainObject());

        verify(userManagement);
    }


    /**
     * Asserts that the controller rejects form submissions whose validation
     * results return errors.
     */
    @Test
    public void rejectsInvalidFormSubmissions() {

        expectValidationResult(false);
        replay(getMocks());

        String viewName =
                controller.doSubmit(userForm, errors, sessionStatus, model);

        Assert.assertEquals("umt/user", viewName);

        verify(getMocks());
    }


    /**
     * Asserts that the controller invokes {@code UserManagement#save(User)} if
     * a valid request was submitted.
     */
    @Test
    public void triggersSaveOnUserManagement() {

        expectValidationResult(true);

        User user = userForm.getDomainObject();

        userManagement.save(user);
        expectLastCall().once();

        replay(getMocks());

        String viewName =
                controller.doSubmit(userForm, errors, sessionStatus, model);

        Assert.assertEquals(UrlUtils.redirect("../users"), viewName);
        assertSuccessMessageWithArguments(model, userForm.getUsername());

        verify(getMocks());
    }


    /**
     * Asserts that the view targets the user list with a redirect.
     * 
     * @param viewName
     */
    private void assertRedirectsToUserList(String viewName) {

        Assert.assertEquals(UrlUtils.redirect("../users"), viewName);
    }


    /**
     * Expresses expectation of a given validation result.
     * 
     * @param result
     */
    private void expectValidationResult(boolean result) {

        expect(errors.hasErrors()).andReturn(!result);
    }
}
