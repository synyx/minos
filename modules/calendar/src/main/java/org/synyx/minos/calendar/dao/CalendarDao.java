package org.synyx.minos.calendar.dao;

import org.synyx.hades.dao.GenericDao;
import org.synyx.minos.calendar.domain.Calendar;



/**
 * Interface for a DAO storing and retrieving {@link Calendar}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface CalendarDao extends GenericDao<Calendar, Long> {

}
