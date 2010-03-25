package org.synyx.minos.calendar.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.synyx.minos.core.domain.OverlapableList;
import org.synyx.minos.core.domain.TimePeriod;



/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class Appointments extends OverlapableList<Appointment, DateTime> {

    public Appointments() {

        super();
    }


    public Appointments(List<Appointment> appointments) {

        super(appointments);
    }


    /**
     * Returns all appointments for the given day.
     * 
     * @param day
     * @return
     */
    public Appointments getAppointmentsFor(DateTime day) {

        TimePeriod period = new TimePeriod(day);
        Appointments result = new Appointments();

        for (Appointment appointment : overlapables) {

            if (appointment.overlaps(period)) {
                result.add(appointment);
            }
        }

        return result;
    }


    public Set<Calendar> getCalendars() {

        Set<Calendar> calendars = new HashSet<Calendar>();

        for (Appointment appointment : overlapables) {
            calendars.add(appointment.getCalendar());
        }

        return calendars;
    }
}
