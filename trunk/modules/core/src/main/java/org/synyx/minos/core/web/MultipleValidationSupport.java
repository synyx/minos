package org.synyx.minos.core.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Abstract base class usable for classes supporting validation via multiple validators.
 *
 * @author Alexander Menz - menz@synyx.de
 */
public abstract class MultipleValidationSupport {

    private static final Log LOG = LogFactory.getLog(MultipleValidationSupport.class);

    private List<Validator> validators = new ArrayList<Validator>();
    private Map<Class<?>, Validator> validatorCacheMap = new HashMap<Class<?>, Validator>();

    public void setValidators(List<Validator> validators) {

        this.validators = validators;
    }


    public boolean isValid(Object target, Errors errors) {

        Validator validator = determineValidatorForObject(target);

        if (null == validator) {
            return true;
        }

        validator.validate(target, errors);

        return !errors.hasErrors();
    }


    private Validator determineValidatorForObject(Object target) {

        // try to quick retrieve via cache map
        Validator validator = validatorCacheMap.get(target.getClass());

        if (validator != null) {
            return validator;
        }

        // try to find in list of validators - cache by class if found
        for (Validator validatorEntry : validators) {
            if (validatorEntry.supports(target.getClass())) {
                validatorCacheMap.put(target.getClass(), validatorEntry);

                return validatorEntry;
            }
        }

        LOG.info("No validator found for class: " + target.getClass().getName());

        return null;
    }
}
