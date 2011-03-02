package org.synyx.minos.skillz.dao;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Modifying;
import org.synyx.hades.dao.Query;

import org.synyx.minos.skillz.domain.Level;

import java.util.List;


/**
 * DAO interface to handle {@link Level} entities.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface LevelDao extends GenericDao<Level, Long> {

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


    /**
     * Returns all {@link Level}s with lower ordinal.
     *
     * @param ordinal
     * @return
     */
    @Query("select l from Level l where l.ordinal > ? order by l.ordinal asc")
    List<Level> findLowerLevel(int ordinal);


    /**
     * Returns all {@link Level}s with upper ordinal.
     *
     * @param ordinal
     * @return
     */
    @Query("select l from Level l where l.ordinal < ? order by l.ordinal desc")
    List<Level> findUpperLevel(int ordinal);


    /**
     * Returns the {@link Level} by the given name.
     *
     * @param name
     * @return
     */
    @Query("select l from Level l where l.name = ?")
    Level findByName(String name);
}
