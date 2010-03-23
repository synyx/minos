package org.synyx.minos.calendar.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.joda.time.DateTime;
import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.Overlapable;
import org.synyx.minos.core.domain.TimePeriod;
import org.synyx.minos.core.domain.User;



/**
 * Appointment domain class.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
@NamedQueries( {
        @NamedQuery(name = "Appointment.findByUser", query = "select distinct a from Appointment a left outer join a.participants part where a.organizer = ?1 or part = ?1"),
        @NamedQuery(name = "Appointment.findByStartAndEnd", query = "select a from Appointment a where a.duration.end > ? and a.duration.start < ?") })
public class Appointment extends AbstractAuditable<User, Long> implements
        Comparable<Appointment>, Overlapable<DateTime> {

    private static final long serialVersionUID = -5499412724607526193L;

    private String description;
    private boolean isAllDay;

    @Embedded
    private TimePeriod duration;

    @ManyToOne
    private User organizer;

    @ManyToMany
    private Set<User> participants;

    @ManyToOne
    private Calendar calendar;


    /**
     * Empty constructor of {@code Appointment}. Needed by JPA.
     */
    public Appointment() {

        this.participants = new HashSet<User>();
        this.duration = new TimePeriod();
    }


    /**
     * Creates a new {@code Appointment}.
     * 
     * @param start
     * @param end
     */
    public Appointment(DateTime start, DateTime end) {

        this();

        if (end.isBefore(start)) {
            throw new IllegalArgumentException(
                    "Start date must not be befor end date!");
        }

        this.duration = new TimePeriod(start, end);
    }


    /**
     * Creates a new {@code Appointment} with given start and end date as well
     * as an organizer.
     * 
     * @param start
     * @param end
     * @param organizer
     */
    public Appointment(DateTime start, DateTime end, User organizer) {

        this(start, end);
        this.organizer = organizer;
    }


    /**
     * Creates a new {@code Appointment} with given start and end date as well
     * as an organizer and a set of participants.
     * 
     * @param start
     * @param end
     * @param organizer
     * @param participants
     */
    public Appointment(DateTime start, DateTime end, User organizer,
            Set<User> participants) {

        this(start, end, organizer);
        this.participants = participants;
    }


    /**
     * Creates a new {@link Appointment} with the given start and end date as
     * well a description for the appointment.
     * 
     * @param start
     * @param end
     * @param description
     */
    public Appointment(DateTime start, DateTime end, String description) {

        this(start, end);
        this.description = description;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.calendar.domain.Overlapable#getStart()
     */
    public DateTime getStart() {

        DateTime start = duration.getStart();

        return isAllDay ? start.toDateMidnight().toDateTime() : start;
    }


    public void setStart(DateTime start) {

        this.duration = duration.withStart(start);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.calendar.domain.Overlapable#getEnd()
     */
    public DateTime getEnd() {

        DateTime end = duration.getEnd();

        return isAllDay ? end.plusDays(1).toDateMidnight().toDateTime() : end;
    }


    public void setEnd(DateTime end) {

        this.duration = duration.withEnd(end);
    }


    /**
     * Returns the description.
     * 
     * @return the description
     */
    public String getDescription() {

        return description;
    }


    /**
     * Sets the description.
     * 
     * @param description
     */
    public void setDescription(String description) {

        this.description = description;
    }


    /**
     * Returns whether this is an appointment lasting the entire day.
     * 
     * @return
     */
    public boolean isAllDay() {

        return isAllDay;
    }


    /**
     * Sets whether this is an appointment lasting the entire day.
     * 
     * @param isAllDay
     */
    public void setAllDay(boolean isAllDay) {

        this.isAllDay = isAllDay;
    }


    /**
     * Returns the organizer.
     * 
     * @return the organizer
     */
    public User getOrganizer() {

        return organizer;
    }


    /**
     * Sets the organizer.
     * 
     * @param organizer
     */
    public void setOrganizer(User organizer) {

        this.organizer = organizer;
    }


    /**
     * Returns the participants.
     * 
     * @return the participants
     */
    public Set<User> getParticipants() {

        return participants;
    }


    /**
     * @param participants the participants to set
     */
    public void setParticipants(Set<User> participants) {

        this.participants = participants;
    }


    /**
     * @return the calendar
     */
    public Calendar getCalendar() {

        return calendar;
    }


    /**
     * @param calendar the calendar to set
     */
    public void setCalendar(Calendar calendar) {

        this.calendar = calendar;
        this.calendar.getAppointments().add(this);
    }


    /**
     * Returns whether the {@code Appointment} is connected to the given
     * calendar.
     * 
     * @param calendar
     * @return
     */
    public boolean inCalendar(Calendar calendar) {

        return this.calendar.equals(calendar);
    }


    /**
     * Returns if the {@code Appointment} is connected to one of the given
     * {@link Calendar}s.
     * 
     * @param calendars
     * @return
     */
    public boolean inCalendar(List<Calendar> calendars) {

        return calendars.contains(calendar);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.calendar.domain.Overlapable#overlaps(com.synyx.minos.
     * calendar.domain.Overlapable)
     */
    public boolean overlaps(Overlapable<DateTime> appointment) {

        boolean endsAfterOtherStarts = getEnd().isAfter(appointment.getStart());
        boolean startsBeforeOtherEnds =
                getStart().isBefore(appointment.getEnd());

        return startsBeforeOtherEnds && endsAfterOtherStarts;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.domain.AbstractEntity#toString()
     */
    @Override
    public String toString() {

        return super.toString() + " " + description + " from " + getStart()
                + " to " + getEnd();
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Appointment appointment) {

        return duration.compareTo(appointment.duration);
    }


    /**
     * Adds a participant.
     * 
     * @param user
     */
    public void addParticipant(User participant) {

        this.participants.add(participant);
    }
}
