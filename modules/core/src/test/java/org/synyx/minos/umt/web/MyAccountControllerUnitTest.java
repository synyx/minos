package org.synyx.minos.umt.web;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import org.springframework.util.Assert;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.security.AuthenticationService;
import org.synyx.minos.umt.dao.UserDao;
import org.synyx.minos.umt.service.UserAccountManagement;


/**
 * Unit test for {@code MyAccountController}.
 *
 * @author Stefan Kuhn - kuhn@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MyAccountControllerUnitTest {

    // Primitive constants
    private static final Long ID = 34L;

    private static final String USERNAME = "username";

    private static final String USEREMAIL = "useremail@synyx.de";

    // The controller to test
    private MyAccountController controller;

    // Dependencies and mocks
    @Mock
    private UserAccountManagement userAccountManagement;

    @Mock
    private Validator validator;

    @Mock
    private Errors errors;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserDao userDao;

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
        controller = new MyAccountController(userAccountManagement);
        controller.setValidator(validator);

        // Create basic form instance
        userForm = new UserForm();
        userForm.setId(ID);
        userForm.setUsername(USERNAME);

        // Prepare data structures needed
        model = new ExtendedModelMap();
        sessionStatus = new SimpleSessionStatus();
    }


    @Test
    public void testSaveMyAccount() throws Exception {

        User user1 = new User(USERNAME, USEREMAIL);
        user1.setId(35L);

        User user2 = new User("stefan.kuhn", "kuhn@synyx.de", "password");

        userDao.saveAndFlush(user1);
        userDao.saveAndFlush(user2);

        when(authenticationService.getCurrentUser()).thenReturn(user2);

        String returnValue1 = controller.saveMyAccount(userForm, errors, sessionStatus, user1, model);
        Assert.isTrue(returnValue1.equals("myaccount"));

        System.out.print(returnValue1);

        String returnValue2 = controller.saveMyAccount(userForm, errors, sessionStatus, userForm.getDomainObject(),
                model);

        Assert.isTrue(returnValue2.equals("redirect:/myaccount"));
    }
}
