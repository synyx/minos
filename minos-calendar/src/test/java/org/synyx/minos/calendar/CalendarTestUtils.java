package org.synyx.minos.calendar;

import org.joda.time.DateTime;
import org.synyx.minos.calendar.dao.AppointmentDao;
import org.synyx.minos.calendar.dao.CalendarDao;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.calendar.domain.Calendar;



/**
 * Utility methods to create tests for calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class CalendarTestUtils {

    /**
     * Creates a new {@link Appointment} that last one hour starting now.
     * 
     * @return
     */
    public static Appointment oneHourFromNow() {

        return new Appointment(new DateTime(), new DateTime().plusHours(1));
    }


    /**
     * Creates an {@link Appointment} with the given description that lasts one
     * hour starting now.
     * 
     * @param description
     * @return
     */
    public static Appointment oneHourFromNow(String description) {

        Appointment appointment = oneHourFromNow();
        appointment.setDescription(description);

        return appointment;
    }


    /**
     * Creates a {@link Calendar} with the given description and transparently
     * persists it.
     * 
     * @param description
     * @param dao
     * @return
     */
    public static Calendar createAndSave(String description, CalendarDao dao) {

        Calendar calendar = new Calendar(description);
        return dao.save(calendar);
    }


    /**
     * Creates an {@link Appointment} with the given description, assignes it to
     * the given calendar and transparently persists it.
     * 
     * @param description
     * @param calendar
     * @param dao
     * @return
     */
    public static Appointment createAndSave(String description,
            Calendar calendar, AppointmentDao dao) {

        Appointment appointment = oneHourFromNow(description);
        appointment.setCalendar(calendar);

        return dao.save(appointment);
    }
}
