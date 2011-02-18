#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.items.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class TodoItemValidator implements Validator {

    public boolean supports(Class<?> clazz) {

        return TodoItem.class.equals(clazz);
    }


    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "errors.empty");

        TodoItem item = (TodoItem) target;
        String description = item.getDescription();

        if (description.toLowerCase().contains("todo")) {
            errors.rejectValue("description", "errors.contains");
        }

        if (description.length() > 140) {
            errors.rejectValue("description", "errors.toolong");
        }
    }
}
