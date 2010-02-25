package org.synyx.minos.calendar.dao;

import java.util.Date;
import java.util.List;

import org.synyx.hades.dao.ExtendedGenericDao;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.core.domain.User;



/**
 * Interface for finder methods for a DAO for {@link Appointment}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface AppointmentDao extends ExtendedGenericDao<Appointment, Long> { // ,

    // AppointmentDaoCustom {

    /**
     * Returns all appointments that start and end in the given timeframe.
     * 
     * @param start
     * @param end
     * @return
     */
    List<Appointment> findByStartAndEnd(Date start, Date end);


    /**
     * Returns all appointments for a given user. This will include
     * {@link Appointment}s that the user organizes as well as
     * {@link Appointment}s that the user participates in.
     * 
     * @param user
     * @return
     */
    public List<Appointment> findByUser(User user);
}
