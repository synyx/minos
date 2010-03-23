package org.synyx.minos.calendar.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.core.domain.User;



/**
 * A {@code {@code Calendar} allows grouping of {@link Appointment}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Entity
public class Calendar extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -8352877388360951200L;

    @ManyToOne
    private User owner;

    private String description;
    private boolean isPublic;

    @OneToMany
    private Set<Appointment> appointments;


    public Calendar() {

        this.appointments = new HashSet<Appointment>();
    }


    public Calendar(String description) {

        this();
        this.description = description;
    }


    /**
     * Adds the given {@link Appointment}.
     * 
     * @param appointment
     */
    public void addAppointment(Appointment appointment) {

        this.appointments.add(appointment);
        appointment.setCalendar(this);
    }


    /**
     * Returns the owner of the calendar.
     * 
     * @return
     */
    public User getOwner() {

        return owner;
    }


    /**
     * Stes the owner of the calender.
     * 
     * @param owner
     */
    public void setOwner(User owner) {

        this.owner = owner;
    }


    /**
     * Returns the description of the calendar.
     * 
     * @return
     */
    public String getDescription() {

        return description;
    }


    /**
     * @param description
     */
    public void setDescription(String description) {

        this.description = description;
    }


    /**
     * Returns whether the calender is a public calendar. This means that it can
     * be seen by all users.
     * 
     * @return
     */
    public boolean isPublic() {

        return isPublic;
    }


    /**
     * Set whether the calender shall be publically available.
     * 
     * @param isPublic
     */
    public void setPublic(boolean isPublic) {

        this.isPublic = isPublic;
    }


    /**
     * Returns all appointments.
     * 
     * @return
     */
    public Set<Appointment> getAppointments() {

        return appointments;
    }


    /**
     * Sets the appointments of the calendar.
     * 
     * @param appointments
     */
    public void setAppointments(Set<Appointment> appointments) {

        this.appointments = appointments;
    }

}
