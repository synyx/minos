package org.synyx.minos.calendar.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.calendar.CalendarTestUtils;
import org.synyx.minos.calendar.dao.AppointmentDao;
import org.synyx.minos.calendar.dao.CalendarDao;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.calendar.domain.Appointments;
import org.synyx.minos.calendar.domain.Calendar;
import org.synyx.minos.calendar.service.CalendarService;
import org.synyx.minos.test.AbstractModuleIntegrationTest;



/**
 * Integration test for {@link CalendarService} implementations.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class CalendarServiceIntegrationTest extends
        AbstractModuleIntegrationTest {

    @Autowired
    private CalendarService service;

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private AppointmentDao appointmentDao;

    private Calendar firstCalendar;
    private Calendar secondCalendar;

    private List<Calendar> calendars;


    /**
     * Creates two appointments and calendars and assigns the first appointment
     * to the first calendar and so on.
     */
    @Before
    public void setUp() {

        firstCalendar =
                CalendarTestUtils.createAndSave("First calendar", calendarDao);
        secondCalendar =
                CalendarTestUtils.createAndSave("Second calendar", calendarDao);

        calendars = Arrays.asList(firstCalendar, secondCalendar);
    }


    /**
     * Asserts the service finds a single {@link Appointment} if asked for
     * appointments from the first calendar and two if asked for appointments
     * for both calendars.
     * 
     * @throws Exception
     */
    @Test
    public void assertFindsAppointmentsForCalendars() throws Exception {

        Appointment appointment =
                CalendarTestUtils.createAndSave("First appointment",
                        firstCalendar, appointmentDao);

        Appointments result =
                service.getAppointments(appointment.getStart(), appointment
                        .getEnd(), calendars);

        assertEquals(1, result.size());
        assertTrue(result.contains(appointment));
    }
}
