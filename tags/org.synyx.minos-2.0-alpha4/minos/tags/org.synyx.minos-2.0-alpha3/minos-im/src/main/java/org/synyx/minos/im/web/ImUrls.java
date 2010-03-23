package org.synyx.minos.im.web;

/**
 * Urls for instant messaging web and rest controllers.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class ImUrls {

    public static final String MODULE = "/im";
    public static final String MESSAGES = MODULE + "/messages";
    public static final String MESSAGE = MESSAGES + "/{id}";
    public static final String INBOX = MESSAGES + "/inbox";
    public static final String OUTBOX = MESSAGES + "/outbox";


    /**
     * Private constructor to prevent instantiation.
     */
    private ImUrls() {

    }
}
