package org.synyx.minos.umt.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.PageImpl;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.security.AuthenticationService;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.service.UserNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.TestConstants.*;
import static org.synyx.minos.core.web.WebTestUtils.*;


/**
 * Unit test for {@code UmtController}. TODO: extract message assertions into utility class
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class UmtControllerUnitTest {

    // Primitive constants
    private static final Long ID = 5L;
    private static final String USERNAME = "username";

    // The controller to test
    private UmtController controller;

    // Dependencies and mocks
    @Mock
    private UserManagement userManagement;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private Validator validator;
    @Mock
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

        // Basic validator mock setup
        when(validator.supports(UserForm.class)).thenReturn(true);

        // Setup controller
        controller = new UmtController(userManagement, authenticationService);
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
     * Asserts that the request for the users binds the user list to the model that is returned by the user management
     * service.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void putsUsersIntoModelOnRequestToUserList() {

        Page<User> referenceUsers = new PageImpl<User>(Collections.EMPTY_LIST);

        // Prepare user management
        when(userManagement.getUsers(null)).thenReturn(referenceUsers);

        controller.getUsers(null, model, null, USER);

        assertThat(model.asMap().get(UmtController.USERS_KEY), is(notNullValue()));
    }


    /**
     * Asserts that if a role is given getUsers executes the role-filter method on the usermanagement
     */
    @Test
    @SuppressWarnings("unchecked")
    public void executesRoleFilterMethodOnManagement() {

        Page<User> referenceUsers = new PageImpl<User>(Collections.EMPTY_LIST);

        Role role = new Role("somerole");
        // Prepare user management
        when(userManagement.getUsersByRole(role, null)).thenReturn(referenceUsers);

        controller.getUsers(null, model, role, USER);

        assertThat(model.asMap().get(UmtController.USERS_KEY), is(notNullValue()));
    }


    /**
     * Asserts, that a request to delete a user without giving an id results in a redirect request to the list users
     * page as well as produces an error message.
     */
    @Test
    public void rejectsMissingUserIdOnDelete() {

        String viewName = controller.deleteUser(null, model, null);

        assertRedirectsToUserList(viewName);
        assertErrorMessage(model);
    }


    /**
     * Asserts that a successful deletion is confirmed by a success message containing the username.
     * 
     * @throws UserNotFoundException
     */
    @Test
    public void confirmsSuccessfulDeletion() throws UserNotFoundException {

        userManagement.delete(USER);

        String viewName = controller.deleteUser(USER, model, null);

        assertRedirectsToUserList(viewName);
        assertSuccessMessageWithArguments(model, USER.getUsername());
    }

    /**
     * Asserts asking for deleting.
     *
     */
    @Test
    public void deleteQuestion() throws UserNotFoundException {

        String viewName = controller.deleteQuestion(USER, model, null);

        Assert.assertEquals(UmtUrls.USER_DELETE_QUESTION, viewName);
    }

    /**
     * Asserts not asking for deleting if current user tries to delete.
     *
     */
    @Test
    public void doNotDeleteQuestionWithSameUser() throws UserNotFoundException {

        String viewName = controller.deleteQuestion(USER, model, USER);
        assertRedirectsToUserList(viewName);
    }


    /**
     * Asserts that the controller prepopulates a blank user form for the edit form if no id is given.
     */
    @Test
    public void createsBlankFormIfNoIdGiven() {

        String viewName = controller.showEmptyForm(model);

        Assert.assertEquals("/umt/user", viewName);

        UserForm user = assertContains(model, UmtController.USER_KEY, UserForm.class);
        Assert.assertTrue(user.isNew());
    }


    /**
     * Asserts that the controller rejects invalid usernames and redirects to the user list instead.
     */
    @Test
    public void rejectsInvalidUsernameForEdit() {

        String viewName = controller.setupForm(null, model);

        assertErrorMessage(model);
        assertRedirectsToUserList(viewName);
    }


    /**
     * Asserts that the controller puts the user into the model that is returned by the {@code UserManagement} on a
     * request with a valid username.
     */
    @Test
    public void putsUserToEditIntoModel() {

        User user = new User(USERNAME, "email@address.com", "password");
        user.setId(ID);

        String viewName = controller.setupForm(user, model);

        // Assert correct view
        Assert.assertEquals("/umt/user", viewName);

        // Assert model
        UserForm userForm = assertContains(model, UmtController.USER_KEY, UserForm.class);
        Assert.assertEquals(user, userForm.getDomainObject());
    }


    /**
     * Asserts that the controller rejects form submissions whose validation results return errors.
     */
    @Test
    public void rejectsInvalidFormSubmissions() {

        expectValidationResult(false);

        String viewName = controller.saveUser(userForm, "", errors, sessionStatus, model);

        Assert.assertEquals("/umt/user", viewName);
    }


    /**
     * Asserts that the controller invokes {@code UserManagement#save(User)} if a valid request was submitted.
     */
    @Test
    public void triggersSaveOnUserManagement() {

        expectValidationResult(true);

        User user = userForm.getDomainObject();

        String viewName = controller.saveUser(userForm, "./users", errors, sessionStatus, model);

        Assert.assertEquals(UrlUtils.redirect("./users"), viewName);
        assertSuccessMessageWithArguments(model, userForm.getUsername());

        verify(userManagement, only()).save(user);
    }


    /**
     * Asserts that the view targets the user list with a redirect.
     * 
     * @param viewName
     */
    private void assertRedirectsToUserList(String viewName) {

        Assert.assertEquals(UrlUtils.redirect(UmtUrls.USERS), viewName);
    }


    /**
     * Expresses expectation of a given validation result.
     * 
     * @param result
     */
    private void expectValidationResult(boolean result) {

        when(errors.hasErrors()).thenReturn(!result);
    }

    @Test
    public void determinePermissions() throws Exception {
        Collection<String> permList = Arrays.asList("EDIT_USER", "CREATE_USER");
        when(authenticationService.getPermissions()).thenReturn(permList);

        Role role = new Role("MODERATOR");
        role.add("EDIT_USER");

        List<PermissionHolder> holder = Arrays.asList(new PermissionHolder("CREATE_USER", false), new PermissionHolder("EDIT_USER", true));
        List<PermissionHolder> result = controller.determinePermissions(role);
        assertEquals(holder.get(0).getChecked(), result.get(0).getChecked());
        assertEquals(holder.get(1).getChecked(), result.get(1).getChecked());
    }
}
