package org.synyx.minos.support.remoting;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception being thrown when a request can not be processed due to errors in
 * the provided payload.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = -1102068448213696734L;


    /**
     * Creates a new {@link InvalidRequestException}.
     * 
     * @param msg
     * @param ex
     */
    public InvalidRequestException(String msg, Throwable ex) {

        super(msg, ex);
    }


    /**
     * Creates a new {@link InvalidRequestException}.
     * 
     * @param msg
     */
    public InvalidRequestException(String msg) {

        super(msg);
    }
}
