package org.synyx.minos.core.domain;

import org.joda.time.*;
import org.joda.time.base.BaseDateTime;
import org.synyx.minos.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;


/**
 * Encapsulation for a time period that carries a start and an end date. This class is mainly used to abstract the
 * JodaTime concept of an {@link Interval} and make it usable with JPA.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
@Embeddable
public class TimePeriod implements Overlapable<DateTime>, Comparable<TimePeriod> {

    // Needed because "start" might be a database keyword
    @Column(name = "period_start")
    private Date start;

    // Needed because "end" might be a database keyword
    @Column(name = "period_end")
    private Date end;

    /**
     * Creates an empty {@code TimePeriod} with start and end of now.
     */
    public TimePeriod() {

        this.start = new Date();
        this.end = start;
    }


    /**
     * Creates a new {@code TimePeriod} from the given start and end date.
     *
     * @param start
     * @param end
     */
    public TimePeriod(DateTime start, DateTime end) {

        Assert.notNull(start, "Start must not be null!");
        Assert.notNull(end, "End must not be null!");

        this.start = start.toDate();
        this.end = end.toDate();
    }


    public TimePeriod(DateMidnight start, DateMidnight end) {

        Assert.notNull(start, "Start must not be null!");
        Assert.notNull(end, "End must not be null!");

        this.start = start.toDate();
        this.end = end.toDate();
    }


    /**
     * Creates a new {@code TimePeriod} for the given date. The period will include the complete day.
     *
     * @param date
     */
    public TimePeriod(DateTime date) {

        Assert.notNull(date, "Date must not be null!");

        this.start = date.toDateMidnight().toDate();
        this.end = date.plusDays(1).toDateMidnight().toDate();
    }


    public TimePeriod(DateMidnight date) {

        this.start = date.toDate();
        this.end = date.plusDays(1).toDate();
    }


    /**
     * Creates a new {@link TimePeriod} lasting the duration of the given {@link ReadablePeriod}. Mostly used with
     * {@link Hours}, {@link Minutes} and so on.
     *
     * @param start
     * @param period
     */
    public TimePeriod(DateTime start, ReadablePeriod period) {

        this(start, start.plus(period));
    }

    /**
     * Returns a {@link TimePeriod} for the current entire day.
     *
     * @return
     */
    public static TimePeriod allDay() {

        return allDay(new DateTime());
    }


    /**
     * Returns a new {@code TimePeriod} that spans the entire given day.
     *
     * @param day
     * @return
     */
    public static TimePeriod allDay(DateTime day) {

        DateTime validDay = defaultForNull(day, new DateTime());

        return allDay(validDay, validDay);
    }


    public static TimePeriod allDay(DateMidnight day) {

        DateMidnight validDay = defaultForNull(day, new DateMidnight());

        return new TimePeriod(validDay, validDay.plusDays(1));
    }


    /**
     * Returns a new {@code TimePeriod} that spans all days from start date to (including) end date.
     *
     * @param start
     * @param end
     * @return
     */
    public static TimePeriod allDay(DateTime start, DateTime end) {

        Assert.notNull(start, "Start must not be null!");
        Assert.notNull(end, "End must not be null!");

        return new TimePeriod(start.toDateMidnight().toDateTime(), end.plusDays(1).toDateMidnight().toDateTime());
    }


    /**
     * Returns the period for he entire week of the current time.
     *
     * @return
     */
    public static TimePeriod allWeek() {

        return allWeek(new DateTime());
    }


    /**
     * Returns the period for the entire week of the given date.
     *
     * @param date
     * @return
     */
    public static TimePeriod allWeek(DateTime date) {

        return allWeek(defaultForNull(date, new DateTime()).toDateMidnight());
    }


    /**
     * Returns the period for the entire week of the given date.
     *
     * @param date
     * @return
     */
    public static TimePeriod allWeek(DateMidnight date) {

        DateMidnight validDate = defaultForNull(date, new DateMidnight());

        DateMidnight start = validDate.withDayOfWeek(1);
        DateMidnight end = start.plusWeeks(1);

        return new TimePeriod(start, end);
    }


    /**
     * Returns the period of the entire month of the current date.
     *
     * @return
     */
    public static TimePeriod allMonth() {

        return allMonth(new DateTime());
    }


    /**
     * Returns the period of the entire month of the given date.
     *
     * @param date
     * @return
     */
    public static TimePeriod allMonth(DateTime date) {

        return allMonth(defaultForNull(date, new DateTime()).toDateMidnight());
    }


    public static TimePeriod allMonth(DateMidnight date) {

        DateMidnight validDate = defaultForNull(date, new DateMidnight());

        DateMidnight start = validDate.withDayOfMonth(1);
        DateMidnight end = start.plusMonths(1);

        return new TimePeriod(start, end);
    }


    @Override
    public DateTime getEnd() {

        return new DateTime(end);
    }


    public TimePeriod withEnd(DateTime end) {

        return new TimePeriod(this.getStart(), end);
    }


    /**
     * Returns a {@link TimePeriod} that has it's end date given by the given {@link ReadablePeriod}.
     *
     * @param period
     * @return
     */
    public TimePeriod extend(ReadablePeriod period) {

        return new TimePeriod(this.getStart(), getEnd().plus(period));
    }


    @Override
    public DateTime getStart() {

        return new DateTime(start);
    }


    public TimePeriod withStart(DateTime start) {

        return new TimePeriod(start, this.getEnd());
    }


    /**
     * Returns a new {@code TimePeriod} that is expanded to teh entire day based on the current {@code TimePeriod}.
     */
    public TimePeriod toAllDay() {

        return TimePeriod.allDay(getStart(), getEnd());
    }


    @Override
    public boolean overlaps(Overlapable<DateTime> that) {

        if (null == that) {
            return false;
        }

        boolean endsAfterOtherStarts = getEnd().isAfter(that.getStart());
        boolean startsBeforeOtherEnds = getStart().isBefore(that.getEnd());

        return startsBeforeOtherEnds && endsAfterOtherStarts;
    }


    @Override
    public int compareTo(TimePeriod period) {

        Assert.notNull(period);

        return this.start.compareTo(period.start);
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TimePeriod)) {
            return false;
        }

        TimePeriod that = (TimePeriod) obj;

        boolean startEquals = this.start.equals(that.start);
        boolean endEquals = this.end.equals(that.end);

        return startEquals && endEquals;
    }


    @Override
    public int hashCode() {

        int result = 17;

        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();

        return result;
    }


    @Override
    public String toString() {

        return String.format("%s - %s", start, end);
    }


    private static <T extends BaseDateTime> T defaultForNull(T subject, T defaultValue) {

        return null == subject ? defaultValue : subject;
    }
}
