package org.synyx.minos.calendar;

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

        Appointment appointment = new Appointment(description);
        appointment.setCalendar(calendar);

        return dao.save(appointment);
    }
}
