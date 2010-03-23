package org.synyx.minos.core.domain;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;
import org.synyx.minos.core.domain.TimePeriod;


/**
 * Unit test for {@link TimePeriod}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class TimePeriodUnitTest {

    /**
     * Asserts equals works.
     * 
     * @throws Exception
     */
    @Test
    public void testEquals() throws Exception {

        TimePeriod first = TimePeriod.allDay();
        TimePeriod second = TimePeriod.allDay();

        assertEquals(first, first);
        assertEquals(second, second);
        assertEquals(first, second);
    }


    @Test
    public void detectsOverlaps() {

        TimePeriod first = oneHourPeriod(7, 0);
        TimePeriod second = oneHourPeriod(7, 30);

        assertTrue(first.overlaps(second));
        assertTrue(second.overlaps(first));

        TimePeriod third = xHourPeriod(6, 0, 3);

        assertTrue(first.overlaps(third));
        assertTrue(third.overlaps(first));
    }


    /**
     * Assert that a {@link TimePeriod} is not overlapping {@code null}.
     */
    @Test
    public void doesNotOverlapNull() {

        TimePeriod period = TimePeriod.allDay();

        assertFalse(period.overlaps(null));
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullsInSingleArgumentConstructor() {

        new TimePeriod((DateTime) null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullsInDoubleArgumentConstructor() {

        new TimePeriod((DateTime) null, null);
    }


    public void rejectsNullForAllDaySingle() {

        assertEquals(TimePeriod.allDay(), TimePeriod.allDay((DateTime) null));
    }


    public void rejectsNullForAllDayTwo() {

        assertEquals(TimePeriod.allDay(), TimePeriod.allDay(null, null));
    }


    /**
     * Creates an one hour {@link TimePeriod} beginning at the given time.
     * 
     * @param hour
     * @param minute
     * @return
     */
    private TimePeriod oneHourPeriod(int hour, int minute) {

        return xHourPeriod(hour, minute, 1);
    }


    private TimePeriod xHourPeriod(int hour, int minute, int hours) {

        DateTime start = new DateTime(2008, 2, 1, hour, minute, 0, 0);
        DateTime end = start.plusHours(hours);

        return new TimePeriod(start, end);
    }

}
