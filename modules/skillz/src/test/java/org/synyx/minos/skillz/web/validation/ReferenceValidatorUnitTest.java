package org.synyx.minos.skillz.web.validation;

import static org.synyx.minos.core.web.WebTestUtils.*;

import org.joda.time.DateMidnight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.synyx.minos.skillz.domain.Activity;
import org.synyx.minos.skillz.domain.Project;


/**
 * Unit test for {@code ReferenceValidator}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ReferenceValidatorUnitTest {

    private ReferenceValidator validator;
    private Errors errors;
    private Activity reference;


    @Before
    public void setUp() {

        validator = new ReferenceValidator();

        reference = new Activity(new Project("name"), new DateMidnight());
    }


    @Test
    public void passesValidReference() {

        prepareErrorsAndExecute();

        Assert.assertFalse("Found errors: " + errors, errors.hasErrors());
    }


    @Test
    public void rejectEmptyProject() {

        reference.setProject(null);

        prepareErrorsAndExecute();

        Assert.assertTrue("Found errors: " + errors, errors.hasErrors());
        assertContainsFieldErrorWithCode(errors, "project", ReferenceValidator.REFERENCE_PROJECT_EMPTY);
    }


    /**
     * Creates a new {@code Errors} instance that binds the {@code Activity} instance and triggers validation.
     */
    private void prepareErrorsAndExecute() {

        errors = new BeanPropertyBindingResult(reference, "project");
        validator.validate(reference, errors);
    }

}
