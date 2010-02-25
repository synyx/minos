package org.synyx.minos.calendar.web;

/**
 * URL constants for calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class CalendarUrls {

    public static final String MODULE = "/calendar";
    public static final String MODULE_BASE = MODULE + "/**";

    public static final String LIST_APPOINTMENTS = MODULE + "/appointments";
    public static final String EDIT_APPOINTMENT = MODULE + "/appointment";
    public static final String DELETE_APPOINTMENT =
            EDIT_APPOINTMENT + "/delete";
}
