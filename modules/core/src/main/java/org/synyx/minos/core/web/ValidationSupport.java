package org.synyx.minos.core.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Support class to validate objects of a given type.
 *
 * @param  <T>  the type to validate
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public abstract class ValidationSupport<T> {

    private static final Log log = LogFactory.getLog(ValidationSupport.class);

    private Validator validator;

    /**
     * Setter to inject a validator.
     *
     * @param  validator  the validator to set
     */
    public void setValidator(Validator validator) {

        this.validator = validator;
    }


    /**
     * Returns if the given object is valid. Invokes the configured validator to decide. Skips validation, if no
     * validator is set.
     *
     * @param  target  an object to validate
     * @param  errors  a Spring errors object
     *
     * @return  whether the target validates against the given validator instance
     */
    protected boolean isValid(T target, Errors errors) {

        if (null == validator) {
            log.info("No validator set! Skipping validation...");

            return true;
        }

        validator.validate(target, errors);

        return !errors.hasErrors();
    }
}
