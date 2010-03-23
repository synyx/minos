package org.synyx.minos.core.web.validation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.synyx.minos.core.web.WebTestUtils;
import org.synyx.minos.core.web.validation.EmailValidator;



/**
 * Unit test for {@code EmailValidator}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EmailValidatorUnitTest {

    private static final String[] EMPTY_EMAIL_ADDRESSES = new String[] { "" };

    private static final String[] INVALID_EMAIL_VALUES = new String[] { "foo@",
            "bar.de" };

    private EmailValidator validator;
    private Errors errors;


    @Before
    public void setUp() {

        validator = new EmailValidator();
    }


    /**
     * Tests that the validator rejects user with <code>null</code> emails or
     * empty strings.
     */
    @Test
    public void rejectsUserWithoutEmailAddress() {

        assertEmailValuesTriggerErrors(EMPTY_EMAIL_ADDRESSES,
                EmailValidator.EMAIL_EMPTY);
    }


    /**
     * Checks that the validator detects invalid email addresses. The following
     * will be checked for: {@value #INVALID_EMAIL_VALUES}
     */
    @Test
    public void rejectsInvalidEmailAddresses() {

        assertEmailValuesTriggerErrors(INVALID_EMAIL_VALUES,
                EmailValidator.EMAIL_INVALID);
    }


    /**
     * Asserts, that a given list of values for the <code>emailAddress</code>
     * property raises a field error with the given error code.
     * 
     * @param emailValues
     * @param errorCode
     */
    private void assertEmailValuesTriggerErrors(String[] emailValues,
            String errorCode) {

        for (String value : emailValues) {

            errors = new BeanPropertyBindingResult(value, "emailAddress");
            validator.validate(value, errors);

            WebTestUtils.assertContainsObjectErrorWithCode(errors, errorCode);
        }
    }
}
