package org.synyx.minos.skillz.web.validation;

import static org.synyx.minos.core.web.WebTestUtils.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.synyx.minos.skillz.domain.MatrixTemplate;


/**
 * Unit test for {@code MatrixTemplateValidator}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MatrixTemplateValidatorUnitTest {

    private MatrixTemplateValidator validator;
    private Errors errors;
    private MatrixTemplate matrixTemplate;


    @Before
    public void setUp() {

        validator = new MatrixTemplateValidator();

        matrixTemplate = new MatrixTemplate("matrixTemplate");

    }


    @Test
    public void passesValidProject() {

        prepareErrorsAndExecute();

        Assert.assertFalse("Found errors: " + errors, errors.hasErrors());
    }


    @Test
    public void rejectEmptyProjectName() {

        // change name
        matrixTemplate.setName("");

        prepareErrorsAndExecute();

        Assert.assertTrue("Found errors: " + errors, errors.hasErrors());
        assertContainsFieldErrorWithCode(errors, "name", MatrixTemplateValidator.MATRIX_TEMPLATE_NAME_EMPTY);
    }


    /**
     * Creates a new {@code Errors} instance that binds the {@code MatrixTemplate} instance and triggers validation.
     */
    private void prepareErrorsAndExecute() {

        errors = new BeanPropertyBindingResult(matrixTemplate, "project");
        validator.validate(matrixTemplate, errors);
    }

}
