package org.synyx.minos.skillz;

/**
 * Constants class to capture skill module permissions.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class SkillzPermissions {

    public static final String SKILLZ_ADMINISTRATION = "SKILLZ_ADMINISTRATION";
    public static final String SKILLZ_USER = "SKILLZ_USER";

    /**
     * Private constructor to prevent instantation and subclassing.
     */
    private SkillzPermissions() {
    }
}
