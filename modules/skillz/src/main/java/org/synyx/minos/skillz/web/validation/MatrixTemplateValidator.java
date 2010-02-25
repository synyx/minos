package org.synyx.minos.skillz.web.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.skillz.domain.MatrixTemplate;


/**
 * Validator for {@code MatrixTemplate}. Checks the following criteria:
 * <ul>
 * <li>Matrix template name is not empty</li>
 * </ul>
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class MatrixTemplateValidator implements Validator {

    public static final String MATRIX_TEMPLATE_NAME_EMPTY =
            "skillz.template.name.error.empty";


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return MatrixTemplate.class.equals(clazz);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {

        MatrixTemplate matrixTemplate = (MatrixTemplate) target;

        if (StringUtils.isBlank(matrixTemplate.getName())) {
            errors.rejectValue("name", MATRIX_TEMPLATE_NAME_EMPTY);
        }
    }

}
