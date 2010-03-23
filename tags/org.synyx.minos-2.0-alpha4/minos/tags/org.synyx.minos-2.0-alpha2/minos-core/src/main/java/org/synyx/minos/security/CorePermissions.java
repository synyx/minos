package org.synyx.minos.security;

import java.util.Arrays;
import java.util.List;


/**
 * Constants class for permissions of the core module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class CorePermissions {

    public static final String UMT_ADMIN = "UMT_ADMIN";

    public static final String NOPERMISSION = "NOPERMISSION";

    public static final List<String> ALL =
            Arrays.asList(UMT_ADMIN, NOPERMISSION);


    /**
     * Private constructor to prevent instantiation and subclassing.
     */
    private CorePermissions() {

    }
}
