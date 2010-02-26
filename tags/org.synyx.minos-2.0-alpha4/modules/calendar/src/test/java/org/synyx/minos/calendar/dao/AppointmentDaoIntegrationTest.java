package org.synyx.minos.calendar.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.calendar.domain.Calendar;
import org.synyx.minos.core.domain.TimePeriod;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.test.AbstractDaoIntegrationTest;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Integration test for {@link AppointmentDaoImpl}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class AppointmentDaoIntegrationTest extends AbstractDaoIntegrationTest {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private UserDao userDao;

    private User user;

    private Collection<Calendar> calendars;
    private Calendar firstCalendar;
    private Calendar secondCalendar;


    @Before
    public void setUp() {

        user = new User("o.gierke", "gierke@synyx.de", "password");
        userDao.save(user);

        firstCalendar = new Calendar("Calendar 1");
        calendarDao.save(firstCalendar);

        secondCalendar = new Calendar("Calendar 2");
        calendarDao.save(secondCalendar);

        calendars = new HashSet<Calendar>();
        calendars.add(firstCalendar);
        calendars.add(secondCalendar);

    }


    @Test
    public void testAppointmentCreation() {

        Appointment appointment = oneHourFromNow();

        appointmentDao.save(appointment);
    }


    @Test
    public void testFindByUserFindsOrganizer() {

        Appointment appointment = oneHourFromNow();
        appointment.setOrganizer(user);

        appointmentDao.save(appointment);
        assertNumberOfAppointmentsForUser(1);
    }


    @Test
    public void testFindByUserFindsParticipant() {

        Appointment appointment = oneHourFromNow();
        appointment.addParticipant(user);

        appointmentDao.save(appointment);
        assertNumberOfAppointmentsForUser(1);
    }


    @Test
    public void testFindByUserFindsDistinctParticipant() {

        Appointment appointment = oneHourFromNow();
        appointment.setOrganizer(user);
        appointment.addParticipant(user);

        appointmentDao.save(appointment);
        assertNumberOfAppointmentsForUser(1);
    }


    @Test
    public void assertFindAppointmentsInAGivenTime() throws Exception {

        // Construct an appointment overlapping the beginning of this month
        Appointment leakInto =
                new Appointment(new TimePeriod(new DateTime().minusMonths(1),
                        new DateTime()), "Leaks into this month!");

        // Construct an appointment overlapping the end of this month
        Appointment leakOut =
                new Appointment(new TimePeriod().extend(Months.ONE),
                        "Leaks into next month!");

        DateTime monthStart = TimePeriod.allMonth().getStart();
        DateTime monthEnd = TimePeriod.allMonth().getEnd();

        Appointment containing =
                new Appointment(new TimePeriod(monthStart.minusDays(1),
                        monthEnd.plusDays(1)), "Containing appointment");

        Appointment contained =
                new Appointment(new TimePeriod(monthStart.plusDays(1), monthEnd
                        .minusDays(1)), "Cointained appointment");

        // Construct an appointment starting right at the start of the next
        // month
        Appointment startAtEnd =
                new Appointment(new TimePeriod(monthEnd, Days.ONE),
                        "Starting first of next month!");

        Appointment endAtStart =
                new Appointment(new TimePeriod(monthStart.minusDays(1),
                        monthStart), "Ending at start of the month!");

        appointmentDao.save(Arrays.asList(leakInto, leakOut, containing,
                contained, startAtEnd, endAtStart));

        List<Appointment> appointments =
                appointmentDao.findByStartAndEnd(monthStart.toDate(), monthEnd
                        .toDate());

        assertEquals(4, appointments.size());
        assertTrue(appointments.containsAll(Arrays.asList(leakInto, leakOut,
                containing, contained)));
    }


    /**
     * Asserts that we find exactly the given number of {@link Appointment}s for
     * the user.
     * 
     * @param count
     */
    private void assertNumberOfAppointmentsForUser(int count) {

        List<Appointment> appointments = appointmentDao.findByUser(user);

        Assert.assertNotNull(appointments);
        Assert.assertEquals(count, appointments.size());
    }


    /**
     * Creates an {@link Appointment} that last one hour from now on.
     * 
     * @return
     */
    private Appointment oneHourFromNow() {

        return new Appointment(new TimePeriod().extend(Hours.ONE));
    }
}
