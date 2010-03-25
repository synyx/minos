package org.synyx.minos.core.domain;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.domain.Overlapable;
import org.synyx.minos.core.domain.OverlapableList;
import org.synyx.minos.core.domain.TimePeriod;


/**
 * Unit test for {@link OverlapableList}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class OverlapableListUnitTest {

    private OverlapableList<TimePeriod, DateTime> appointments;


    /**
     * Sets up an {@link OverlapableList} with three {@link Appointment}s.
     * <ul>
     * <li>18:00 - 19:00</li>
     * <li>19:00 - 20:00</li>
     * <li>19:30 - 20:30</li>
     * <ul>
     */
    @Before
    public void setUp() {

        appointments = OverlapableList.create();

        appointments.add(oneHourAppointment(18, 0));
        appointments.add(oneHourAppointment(19, 0));
        appointments.add(oneHourAppointment(19, 30));
    }


    /**
     * Asserts that the list correctly detects overlaps.
     */
    @Test
    public void testAssertFindsAppointment() {

        Assert.assertTrue(appointments.hasOverlaps());
    }


    /**
     * Asserts that the list
     */
    @Test
    public void assertFindsOverlaps() {

        OverlapableList<TimePeriod, DateTime> result =
                appointments.getOverlapsFor(oneHourAppointment(19, 0));

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());

        Assert.assertSame(result.get(0), appointments.get(1));
        Assert.assertSame(result.get(1), appointments.get(2));
    }


    /**
     * Asserts that the list does not falsely take the given {@link Overlapable}
     * into account if it comes from the list itself.
     */
    @Test
    public void assertHonoursSelfReferencesOnGetOverlapsFor() {

        OverlapableList<TimePeriod, DateTime> result =
                appointments.getOverlapsFor(appointments.get(1));

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }


    /**
     * Creates an one hour appointment beginning at the given time.
     * 
     * @param hour
     * @param minute
     * @return
     */
    private TimePeriod oneHourAppointment(int hour, int minute) {

        DateTime start = new DateTime(2008, 2, 1, hour, minute, 0, 0);
        DateTime end = start.plusHours(1);

        return new TimePeriod(start, end);
    }
}
