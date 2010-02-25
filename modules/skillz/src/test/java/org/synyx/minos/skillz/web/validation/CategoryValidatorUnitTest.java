package org.synyx.minos.skillz.web.validation;

import static org.synyx.minos.core.web.WebTestUtils.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.synyx.minos.skillz.domain.Category;


/**
 * Unit test for {@code CategoryValidator}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryValidatorUnitTest {

    private CategoryValidator validator;
    private Errors errors;
    private Category category;


    @Before
    public void setUp() {

        validator = new CategoryValidator();

        category = new Category("category");

    }


    @Test
    public void passesValidProject() {

        prepareErrorsAndExecute();

        Assert.assertFalse("Found errors: " + errors, errors.hasErrors());
    }


    @Test
    public void rejectEmptyProjectName() {

        // change name
        category.setName("");

        prepareErrorsAndExecute();

        Assert.assertTrue("Found errors: " + errors, errors.hasErrors());
        assertContainsFieldErrorWithCode(errors, "name",
                CategoryValidator.CATEGORY_NAME_EMPTY);
    }


    /**
     * Creates a new {@code Errors} instance that binds the {@code Category}
     * instance and triggers validation.
     */
    private void prepareErrorsAndExecute() {

        errors = new BeanPropertyBindingResult(category, "project");
        validator.validate(category, errors);
    }

}
