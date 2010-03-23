package org.synyx.minos.calendar.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.calendar.dao.AppointmentDao;
import org.synyx.minos.calendar.dao.CalendarDao;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.calendar.domain.Appointments;
import org.synyx.minos.calendar.domain.Calendar;
import org.synyx.minos.core.domain.TimePeriod;
import org.synyx.minos.core.domain.User;



/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private AppointmentDao appointmentDao;
    private CalendarDao calendarDao;


    /**
     * Setter to inject an {@link AppointmentDao}.
     * 
     * @param appointmentDao the appointmentDao to set
     */
    @Required
    public void setAppointmentDao(AppointmentDao appointmentDao) {

        this.appointmentDao = appointmentDao;
    }


    /**
     * @param calendarDao the calendarDao to set
     */
    public void setCalendarDao(CalendarDao calendarDao) {

        this.calendarDao = calendarDao;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.calendar.service.CalendarService#save(com.synyx.minos
     * .calendar.domain.Appointment)
     */
    public void save(Appointment appointment) {

        appointmentDao.save(appointment);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.calendar.service.CalendarService#getAppointment(java.
     * lang.Long)
     */
    public Appointment getAppointment(Long id) {

        return appointmentDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.calendar.service.CalendarService#getAppointments()
     */
    @Transactional(readOnly = true)
    public Appointments getAppointments() {

        return new Appointments(appointmentDao.readAll());
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.calendar.service.CalendarService#getAppointments(com.
     * synyx.minos.core.domain.User)
     */
    @Transactional(readOnly = true)
    public Appointments getAppointments(User user) {

        return new Appointments(appointmentDao.findByUser(user));
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.calendar.service.CalendarService#getAppointments(org.
     * joda.time.DateTime, org.joda.time.DateTime)
     */
    @Transactional(readOnly = true)
    public Appointments getAppointments(DateTime start, DateTime end) {

        List<Appointment> appointments =
                appointmentDao.findByStartAndEnd(start.toDate(), end.toDate());

        return new Appointments(appointments);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.calendar.service.CalendarService#getAppointments(org.
     * joda.time.DateTime, org.joda.time.DateTime, java.util.Set)
     */
    @Transactional(readOnly = true)
    public Appointments getAppointments(DateTime start, DateTime end,
            List<Calendar> calendars) {

        List<Appointment> appointments =
                appointmentDao.findByStartAndEnd(start.toDate(), end.toDate());
        Appointments result = new Appointments();

        for (Appointment appointment : appointments) {

            if (appointment.inCalendar(calendars)) {
                result.add(appointment);
            }
        }

        return result;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.calendar.service.CalendarService#getAppointments(com.
     * synyx.minos.calendar.domain.TimePeriod)
     */
    public Appointments getAppointments(TimePeriod period) {

        return new Appointments(appointmentDao.findByStartAndEnd(period
                .getStart().toDate(), period.getEnd().toDate()));
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.calendar.service.CalendarService#getCalendars()
     */
    public List<Calendar> getCalendars() {

        return calendarDao.readAll();
    }
}
