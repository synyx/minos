package org.synyx.minos.i18n.dao;

import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.minos.i18n.domain.AvailableMessage;


/**
 * Dao interface for {@link AvailableMessage}
 * 
 * @author Alexander Menz - menz@synyx.de
 */
public interface AvailableMessageDao extends GenericDao<AvailableMessage, Long> {

    List<AvailableMessage> findByBasename(String basename);


    AvailableMessage findByBasenameAndKey(String basename, String key);

}
