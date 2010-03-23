package org.synyx.minos.core.web.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.core.domain.EmailAddress;


/**
 * Basic validator implementation to validate email addresses. This class should
 * be mainly used via delegation from domain object validators as it rejects the
 * current path's field. As the validator can not really assume the field name,
 * clients have to push the property name to the nested path stack of the
 * provided {@code Errors} instance.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EmailValidator implements Validator {

    // Message keys for internationalization
    public static final String EMAIL_INVALID = "core.emailAddress.invalid";
    public static final String EMAIL_EMPTY = "core.emailAddress.empty";

    private boolean rejectEmptyEmailAddresses = true;
    private String errorPrefix;


    /**
     * Creates a new {@link EmailValidator} without an error prefix.
     * 
     * @see #setErrorPrefix(String)
     */
    public EmailValidator() {

    }


    /**
     * Creates an {@link EmailValidator} with the given error prefix.
     * 
     * @see #setErrorPrefix(String)
     * @param errorPrefix
     */
    public EmailValidator(String errorPrefix) {

        setErrorPrefix(errorPrefix);
    }


    /**
     * Configures whether the validator should reject empty email addresses.
     * This mainly corresponds to the decision if an email address is mandatory
     * or not. Defaults to {@code true}.
     * 
     * @param rejectEmptyEmailAddresses
     */
    public void setRejectEmptyEmailAddresses(boolean rejectEmptyEmailAddresses) {

        this.rejectEmptyEmailAddresses = rejectEmptyEmailAddresses;
    }


    /**
     * Configures a prefix to be prepended before the error code. Defaults to an
     * empty string meaning no prefix is used. One commonly uses this to create
     * error codes logically grouped with the parent object.
     * 
     * @param errorPrefix the errorPrefix to set
     */
    public void setErrorPrefix(String errorPrefix) {

        this.errorPrefix =
                StringUtils.isBlank(errorPrefix) ? null : errorPrefix;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class<?> clazz) {

        return String.class.equals(clazz);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {

        String emailAddress = (String) target;

        // Check for emtpy mails if configured and return as format checking
        // would not make sense if no email address at all found
        if (rejectEmptyEmailAddresses && StringUtils.isBlank(emailAddress)) {
            errors.rejectValue("", eventuallyPrefix(EMAIL_EMPTY));
            return;
        }

        if (!emailAddress.matches(EmailAddress.EMAIL_REGEX)) {
            errors.rejectValue("", eventuallyPrefix(EMAIL_INVALID));
        }
    }


    /**
     * Prepends the prefix to the given error code if one is configured.
     * 
     * @param errorCode
     * @return
     */
    private String eventuallyPrefix(String errorCode) {

        return null == errorPrefix ? errorCode : String.format("%s.%s",
                errorPrefix, errorCode);
    }
}
