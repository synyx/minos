package org.synyx.minos.skillz.dao;

import org.synyx.hades.dao.ExtendedGenericDao;
import org.synyx.hades.dao.Modifying;
import org.synyx.hades.dao.Query;
import org.synyx.minos.skillz.domain.Level;



/**
 * DAO interface to handle {@link Level} entities.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface LevelDao extends ExtendedGenericDao<Level, Long> {

    /**
     * Returns the default {@link Level}.
     * 
     * @return
     */
    @Query("select l from Level l where l.isDefault = true")
    Level findDefault();


    /**
     * Removes the default flag from all {@link Level}s but the given one.
     * 
     * @param level
     */
    @Modifying
    @Query("update Level l set l.isDefault = false where l <> ?")
    void undefaultAllBut(Level level);
}
