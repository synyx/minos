package org.synyx.minos.umt.web;

/**
 * URLs to be used for UMT web and REST controllers
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public final class UmtUrls {

    public static final String MODULE = "/umt";

    public static final String LIST_USERS = MODULE + "/users";
    public static final String EDIT_USER = MODULE + "/users/{id}";
    public static final String USER_FORM = LIST_USERS + "/form";

    public static final String LIST_ROLES = MODULE + "/roles";
    public static final String EDIT_ROLE = MODULE + "/roles/{id}";
    public static final String ROLE_FORM = LIST_ROLES + "/form";


    /**
     * Private constructor to prevent instantiation.
     */
    private UmtUrls() {

    }
}
