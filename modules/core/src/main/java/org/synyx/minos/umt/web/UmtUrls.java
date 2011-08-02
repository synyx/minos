package org.synyx.minos.umt.web;

/**
 * URLs to be used for UMT web and REST controllers
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public final class UmtUrls {

    public static final String MYACCOUNT = "/myaccount";

    public static final String MODULE = "/umt";

    public static final String USERS = MODULE + "/users";
    public static final String USER = MODULE + "/users/{id}";
    public static final String USER_DELETE = MODULE + "/users/{id}/delete";
    public static final String USER_DELETE_QUESTION = MODULE + "/delete";
    public static final String USER_FORM = USERS + "/form";

    public static final String ROLES = MODULE + "/roles";
    public static final String ROLE = MODULE + "/roles/{id}";
    public static final String ROLE_DELETE = ROLE + "/delete";
    public static final String ROLE_DELETE_QUESTION = MODULE + "/roledelete";

    public static final String ROLE_FORM = ROLES + "/form";

    /** Private constructor to prevent instantiation. */
    private UmtUrls() {
    }
}
