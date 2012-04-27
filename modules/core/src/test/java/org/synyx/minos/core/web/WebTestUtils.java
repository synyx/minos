package org.synyx.minos.core.web;

import junit.framework.Assert;

import org.apache.commons.lang.ArrayUtils;

import org.springframework.ui.Model;

import org.springframework.util.StringUtils;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import org.synyx.minos.core.Core;

import org.synyx.tagsupport.Message;

import java.util.List;


/**
 * Utility class for web layer tests. Provides sophisticated means to validate {@code ModelMap}s
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public abstract class WebTestUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private WebTestUtils() {
    }

    /**
     * Asserts that the given model contains an object of the given type under the given key.
     *
     * @param  model
     * @param  key
     * @param  type
     *
     * @return  the object if found
     */
    @SuppressWarnings("unchecked")
    public static <T> T assertContains(Model model, String key, Class<T> type) {

        Assert.assertTrue(String.format("Model does not contain %s!", key), model.containsAttribute(key));

        Object value = model.asMap().get(key);
        Assert.assertTrue(String.format("Value %s " + "found for key %s is not an instance of %s but rather %s!", value,
                key, type.getName(), value.getClass().getName()), type.isInstance(value));

        return (T) value;
    }


    /**
     * Asserts that the given model owns an error message.
     *
     * @param  model
     *
     * @return  the message if found
     */
    public static Message assertErrorMessage(Model model) {

        return assertMessage(model, true);
    }


    /**
     * Asserts that the given model contains a success message.
     *
     * @param  model
     *
     * @return
     */
    public static Message assertSuccessMessage(Model model) {

        return assertMessage(model, false);
    }


    /**
     * Asserts that the given model contains a message.
     *
     * @param  model
     *
     * @return
     */
    private static Message assertMessage(Model model) {

        Assert.assertTrue(String.format("No value found for message key %s!", Core.MESSAGE),
            model.containsAttribute(Core.MESSAGE));

        Object messageCandidate = model.asMap().get(Core.MESSAGE);

        Assert.assertTrue(String.format("Value found under %s is not an instance of %s!", Core.MESSAGE,
                Message.class.getName()), messageCandidate instanceof Message);

        return (Message) model.asMap().get(Core.MESSAGE);
    }


    /**
     * Asserts that the given model has a message of the given error type.
     *
     * @param  model
     * @param  error  true, if you want to check for error message, false if you want to check for a success message
     *
     * @return
     */
    private static Message assertMessage(Model model, boolean error) {

        Message message = assertMessage(model);
        Assert.assertEquals(error, message.isErrorMessage());

        return message;
    }


    /**
     * Asserts that the model contains an error message and carries the given arguments.
     *
     * @param  model
     * @param  arguments
     *
     * @return
     */
    public static Message assertErrorMessageWithArguments(Model model, Object... arguments) {

        return assertMessageWithArguments(model, true, arguments);
    }


    /**
     * Asserts that the given model contains a message with the given arguments.
     *
     * @param  model
     * @param  error  if you want to ensure a error message or not
     * @param  arguments
     *
     * @return
     */
    private static Message assertMessageWithArguments(Model model, boolean error, Object... arguments) {

        Message message = assertMessage(model, error);

        assertArguments(message, arguments);

        return message;
    }


    /**
     * Asserts that the given model contains a success message with the given arguments.
     *
     * @param  model
     * @param  arguments
     *
     * @return
     */
    public static Message assertSuccessMessageWithArguments(Model model, Object... arguments) {

        return assertMessageWithArguments(model, false, arguments);
    }


    /**
     * Asserts that the given message has the given arguments.
     *
     * @param  message
     * @param  arguments
     */
    public static void assertArguments(Message message, Object... arguments) {

        for (Object argument : arguments) {
            Assert.assertTrue(String.format("Argument %s not found in [%s]!", argument,
                    StringUtils.arrayToCommaDelimitedString(message.getArguments())),
                ArrayUtils.contains(message.getArguments(), argument));
        }
    }


    /**
     * Asserts that an {@code Errors} instance contains a field error for the given field name. The field error has to
     * be equiped with the given error code if it is not {@literal null}.
     *
     * @param  errors
     * @param  fieldName
     * @param  errorCode
     */
    public static void assertContainsFieldErrorWithCode(Errors errors, String fieldName, String errorCode) {

        Assert.assertTrue(errors.hasFieldErrors(fieldName));

        FieldError fieldError = errors.getFieldError(fieldName);

        if (null != errorCode) {
            Assert.assertTrue(fieldError.getCode().equals(errorCode));
        }
    }


    /**
     * Asserts that the given {@link Errors} instance contains a field error for the given field.
     *
     * @param  errors
     * @param  fieldName
     */
    public static void assertContainsFieldError(Errors errors, String fieldName) {

        assertContainsFieldErrorWithCode(errors, fieldName, null);
    }


    /**
     * Utility method to assert that the given {@link Errors} instance carries the provided error code as global error.
     *
     * @param  errors
     * @param  errorCode
     */
    public static void assertContainsObjectErrorWithCode(Errors errors, String errorCode) {

        Assert.assertTrue(errors.hasErrors());

        List<ObjectError> objectErrors = errors.getGlobalErrors();

        for (ObjectError error : objectErrors) {
            // Find required code
            if (ArrayUtils.contains(error.getCodes(), errorCode)) {
                return;
            }
        }

        Assert.fail();
    }
}
