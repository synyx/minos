package org.synyx.minos.skillz.web.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.skillz.domain.Activity;


/**
 * Validator for {@code Activity}. Checks the following criteria:
 * <ul>
 * <li>Activity project is set</li>
 * </ul>
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class ReferenceValidator implements Validator {

    /** error message for empty project. */
    public static final String REFERENCE_PROJECT_EMPTY = "skillz.reference.project.empty";


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return Activity.class.equals(clazz);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {

        Activity reference = (Activity) target;

        if (reference.getProject() == null) {
            errors.rejectValue("project", REFERENCE_PROJECT_EMPTY);
        }
    }

}
