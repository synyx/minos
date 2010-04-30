package org.synyx.minos.calendar.dao;

import java.util.Date;
import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.core.domain.User;


/**
 * Interface for finder methods for a DAO for {@link Appointment}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface AppointmentDao extends GenericDao<Appointment, Long> {

    /**
     * Returns all appointments that start and end in the given timeframe.
     * 
     * @param start
     * @param end
     * @return
     */
    @Query("select a from Appointment a where a.duration.end > ? and a.duration.start < ?")
    List<Appointment> findByStartAndEnd(Date start, Date end);


    /**
     * Returns all appointments for a given user. This will include
     * {@link Appointment}s that the user organizes as well as
     * {@link Appointment}s that the user participates in.
     * 
     * @param user
     * @return
     */
    @Query("select distinct a from Appointment a left outer join a.participants part where a.organizer = ?1 or part = ?1")
    List<Appointment> findByUser(User user);
}
