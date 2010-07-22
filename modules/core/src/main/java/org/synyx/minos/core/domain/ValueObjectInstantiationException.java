package org.synyx.minos.core.domain;

import org.springframework.beans.PropertyAccessException;
import org.springframework.util.StringUtils;
import org.synyx.minos.core.web.ValueObjectPropertyEditor;


/**
 * Excpetion being thrown if a value object cannot be instantiated by {@link ValueObjectPropertyEditor}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ValueObjectInstantiationException extends PropertyAccessException {

    private static final long serialVersionUID = 5220396084870639323L;
    private static final String ERROR_CODE = "valueObject";

    private final String errorCode;


    /**
     * Creates a new {@link ValueObjectInstantiationException}. If the {@code errorCode} is {@literal null} or empty the
     * default error code @ #ERROR_CODE} will be used.
     * 
     * @param message
     * @param cause
     * @param errorCode
     */
    public ValueObjectInstantiationException(String message, Throwable cause, String errorCode) {

        super(message, cause);
        this.errorCode = !StringUtils.hasText(errorCode) ? ERROR_CODE : errorCode;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.core.ErrorCoded#getErrorCode()
     */
    @Override
    public String getErrorCode() {

        return errorCode;
    }
}
