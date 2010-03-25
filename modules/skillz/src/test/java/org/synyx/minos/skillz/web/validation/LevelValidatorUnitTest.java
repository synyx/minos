package org.synyx.minos.skillz.web.validation;

import static org.synyx.minos.core.web.WebTestUtils.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.synyx.minos.skillz.domain.Level;


/**
 * Unit test for {@code LevelValidator}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class LevelValidatorUnitTest {

    private LevelValidator validator;
    private Errors errors;
    private Level level;


    @Before
    public void setUp() {

        validator = new LevelValidator();

        level = new Level("level", 1);

    }


    @Test
    public void passesValidProject() {

        prepareErrorsAndExecute();

        Assert.assertFalse("Found errors: " + errors, errors.hasErrors());
    }


    @Test
    public void rejectEmptyProjectName() {

        // change name
        level.setName("");

        prepareErrorsAndExecute();

        Assert.assertTrue("Found errors: " + errors, errors.hasErrors());
        assertContainsFieldErrorWithCode(errors, "name",
                LevelValidator.LEVEL_NAME_EMPTY);
    }


    /**
     * Creates a new {@code Errors} instance that binds the {@code Level}
     * instance and triggers validation.
     */
    private void prepareErrorsAndExecute() {

        errors = new BeanPropertyBindingResult(level, "project");
        validator.validate(level, errors);
    }

}
