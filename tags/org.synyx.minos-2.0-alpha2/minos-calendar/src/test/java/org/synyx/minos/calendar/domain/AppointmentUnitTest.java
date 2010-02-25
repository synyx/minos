package org.synyx.minos.calendar.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.calendar.domain.Appointment;


/**
 * Unit test for {@link Appointment}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class AppointmentUnitTest {

    private Appointment reference;

    private DateTime fivePm;
    private DateTime sixPm;


    /**
     * Sets up a date at 5:00pm and one at 6:00pm as well as an
     * {@link Appointment} starting at 5 and ending at 6pm.
     */
    @Before
    public void setUp() {

        fivePm = new DateTime(2008, 2, 1, 17, 0, 0, 0);
        sixPm = new DateTime(2008, 2, 1, 18, 0, 0, 0);

        // Appointment from 17:00 to 18:00
        reference = new Appointment(fivePm, sixPm);
    }


    /**
     * Assert that appointment end times before start times are rejected.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPreventsEndBeforeStart() {

        new Appointment(sixPm, fivePm);
    }


    @Test
    public void testDetectsOverlaps() {

        // Assert overlaps itself
        assertOverlap(reference, reference);

        DateTime fourPm = new DateTime(2008, 2, 1, 16, 0, 0, 0);
        DateTime quaterPastFivePm = new DateTime(2008, 2, 1, 17, 15, 0, 0);
        DateTime quaterToSix = new DateTime(2008, 2, 1, 17, 45, 0, 0);

        // Appointment from 16:00 to 17:15
        Appointment appointment = new Appointment(fourPm, quaterPastFivePm);
        assertOverlap(appointment, reference);

        // Appointment from 17:15 to 17:45
        appointment = new Appointment(quaterPastFivePm, quaterToSix);
        assertOverlap(appointment, reference);

        appointment = new Appointment(fourPm, fivePm);
        Appointment anotherAppointment =
                new Appointment(quaterPastFivePm, quaterToSix);

        assertNoOverlap(appointment, anotherAppointment);
    }


    /**
     * Asserts that setting the {@link Appointment#setAllDay(boolean)} flag
     * resets the start and end date of the appointment correctly.
     */
    @Test
    public void allDayFlagSetsTimeBordersCorrectly() {

        Appointment appointment = new Appointment(fivePm, sixPm);
        appointment.setAllDay(true);

        Assert.assertEquals(new DateTime(2008, 2, 1, 0, 0, 0, 0), appointment
                .getStart());
        Assert.assertEquals(new DateTime(2008, 2, 2, 0, 0, 0, 0), appointment
                .getEnd());

        appointment.setAllDay(false);

        Assert.assertEquals(new DateTime(2008, 2, 1, 17, 0, 0, 0), appointment
                .getStart());
        Assert.assertEquals(new DateTime(2008, 2, 1, 18, 0, 0, 0), appointment
                .getEnd());
    }


    /**
     * Asserts that the two given {@code Appointment}s do not overlap.
     * 
     * @param first
     * @param second
     */
    private void assertNoOverlap(Appointment first, Appointment second) {

        Assert.assertFalse(first.overlaps(second));
        Assert.assertFalse(second.overlaps(first));
    }


    /**
     * Asserts that the first appointment overlaps the second one and vice
     * versa.
     * 
     * @param first
     * @param second
     */
    private void assertOverlap(Appointment first, Appointment second) {

        Assert.assertTrue(first.overlaps(second));
        Assert.assertTrue(second.overlaps(first));
    }
}
