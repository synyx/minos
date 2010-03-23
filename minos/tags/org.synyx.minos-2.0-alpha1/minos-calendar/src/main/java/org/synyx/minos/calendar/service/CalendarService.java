package org.synyx.minos.calendar.service;

import java.util.List;

import org.joda.time.DateTime;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.calendar.domain.AppointmentList;
import org.synyx.minos.calendar.domain.Calendar;
import org.synyx.minos.core.domain.TimePeriod;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.Modules;



/**
 * Interface for calendar module. Allows management of {@link Appointment}s and
 * {@link Calendar}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Module(Modules.CALENDAR)
public interface CalendarService {

    /**
     * Saves an {@link Appointment}.
     * 
     * @param appointment
     */
    public void save(Appointment appointment);


    public Appointment getAppointment(Long id);


    /**
     * Returns all {@link Appointment}s.
     */
    public AppointmentList getAppointments();


    /**
     * Returns all {@link Appointment}s where the given {@link User} is either
     * organizer or participant.
     * 
     * @param user
     */
    public AppointmentList getAppointments(User user);


    /**
     * Returns all {@link Appointment}s in the given time frame.
     * 
     * @param start
     * @param end
     * @return
     */
    public AppointmentList getAppointments(DateTime start, DateTime end);


    /**
     * Returns all {@link Appointment}s in the given time frame.
     * 
     * @param period
     * @return
     */
    public AppointmentList getAppointments(TimePeriod period);


    /**
     * Returns all appointments in the given time that are connected to one of
     * the given {@link Calendar}s.
     * 
     * @param start
     * @param end
     * @param calendars
     * @return
     */
    public AppointmentList getAppointments(DateTime start, DateTime end,
            List<Calendar> calendars);


    /**
     * Returns all available {@link Calendars}.
     * 
     * @return
     */
    public List<Calendar> getCalendars();
}
