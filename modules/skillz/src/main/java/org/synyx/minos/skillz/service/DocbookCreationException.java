package org.synyx.minos.skillz.service;

/**
 * Exception being thrown on errors during Docbook creation.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public class DocbookCreationException extends RuntimeException {

    private static final long serialVersionUID = 7277257291861113801L;

    public DocbookCreationException(String message, Throwable cause) {

        super(message, cause);
    }
}
