package org.synyx.minos.skillz.web.validation;

import org.apache.commons.lang.StringUtils;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.synyx.minos.skillz.domain.Category;


/**
 * Validator for {@code Category}. Checks the following criteria:
 * <ul>
 * <li>Category name is not empty</li>
 * </ul>
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public class CategoryValidator implements Validator {

    public static final String CATEGORY_NAME_EMPTY = "skillz.category.name.error.empty";

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return Category.class.equals(clazz);
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {

        Category category = (Category) target;

        if (StringUtils.isBlank(category.getName())) {
            errors.rejectValue("name", CATEGORY_NAME_EMPTY);
        }
    }
}
