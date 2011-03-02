package org.synyx.minos.skillz.web.validation;

import org.apache.commons.lang.StringUtils;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.synyx.minos.skillz.domain.Level;


/**
 * Validator for {@code Level}. Checks the following criteria:
 * <ul>
 * <li>Level name is not empty</li>
 * </ul>
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public class LevelValidator implements Validator {

    public static final String LEVEL_NAME_EMPTY = "skillz.level.name.error.empty";

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return Level.class.equals(clazz);
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {

        Level level = (Level) target;

        if (StringUtils.isBlank(level.getName())) {
            errors.rejectValue("name", LEVEL_NAME_EMPTY);
        }
    }
}
