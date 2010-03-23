package org.synyx.minos.skillz.service;

/**
 * Exception being thrown on errors during ZIP creation.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class ZipCreationException extends RuntimeException {

    private static final long serialVersionUID = -7336055662073463278L;


    public ZipCreationException(String message, Throwable cause) {

        super(message, cause);
    }

}
