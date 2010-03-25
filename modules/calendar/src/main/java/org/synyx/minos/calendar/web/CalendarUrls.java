package org.synyx.minos.calendar.web;

/**
 * URL constants for calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class CalendarUrls {

    static final String MODE_PATHVAR = "mode";
    static final String APPOINTMENT_PATHVAR = "appointment";

    static final String YEAR_PATHVAR = "year";
    static final String MONTH_PATHVAR = "month";
    static final String DAY_PATHVAR = "day";

    public static final String MODULE = "/calendar";

    public static final String APPOINTMENTS = MODULE + "/appointments";
    public static final String APPOINTMENT =
            APPOINTMENTS + "/{" + APPOINTMENT_PATHVAR + ":\\d+}";

    public static final String APPOINTMENT_FORM = APPOINTMENTS + "/form";

    public static final String APPOINTMENTS_MODE =
            APPOINTMENTS + "/{" + MODE_PATHVAR + ":[a-zA-Z_]\\w*}";

    public static final String APPOINTMENTS_MONTH =
            APPOINTMENTS + "/{" + YEAR_PATHVAR + ":\\d+}" + "/{"
                    + MONTH_PATHVAR + ":\\d+}";

    public static final String APPOINTMENTS_DAY =
            APPOINTMENTS_MONTH + "/{" + DAY_PATHVAR + ":\\d+}";
}
