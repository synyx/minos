package org.synyx.minos.im.web;

/**
 * Urls for instant messaging web and rest controllers.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class ImUrls {

    public static final String MODULE = "/im";
    public static final String LIST_MESSAGES = MODULE + "/messages";
    public static final String INBOX = LIST_MESSAGES + "/inbox";
    public static final String OUTBOX = LIST_MESSAGES + "/outbox";


    /**
     * Private constructor to prevent instantiation.
     */
    private ImUrls() {

    }
}
