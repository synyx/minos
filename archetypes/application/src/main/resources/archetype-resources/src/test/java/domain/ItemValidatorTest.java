#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.springframework.validation.Errors;
import ${package}.domain.Item;
import ${package}.domain.ItemValidator;


public class ItemValidatorTest {

    private static final String TOO_LONG =
        "A very long text that should definitely be longer than anything that would be acceptable by the validator since this is how we want our items to be: short, descriptive and without clutter at all";
    private static final String CONTAINS_XXX = "This item contains the word XXX";

    private ItemValidator validator;
    private Errors errors;

    @Before
    public void setUp() throws Exception {

        validator = new ItemValidator();
        errors = mock(Errors.class);
    }


    @Test
    public void shouldReportErrorOnEmpty() {

        Item item = new Item();
        item.setDescription("");
        validator.validate(item, errors);
        verify(errors, times(1)).rejectValue("description", "errors.empty", null, null);
    }


    @Test
    public void shouldReportErrorOnTooLong() {

        Item item = new Item();
        item.setDescription(TOO_LONG);
        validator.validate(item, errors);
        verify(errors, times(1)).rejectValue("description", "errors.toolong");
    }


    @Test
    public void shouldReportErrorOnContainingXXX() {

        Item item = new Item();
        item.setDescription(CONTAINS_XXX);
        validator.validate(item, errors);
        verify(errors, times(1)).rejectValue("description", "errors.contains");
    }
}
