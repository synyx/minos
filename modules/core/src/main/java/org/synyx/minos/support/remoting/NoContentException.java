package org.synyx.minos.support.remoting;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception being thrown if required content can not be found in the request.
 * 
 * @author Oliver Gierke
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoContentException extends RuntimeException {

    private static final long serialVersionUID = -7520019358100565126L;

}
