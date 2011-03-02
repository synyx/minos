package org.synyx.minos.i18n.dao;

import org.synyx.hades.dao.GenericDao;

import org.synyx.minos.i18n.domain.AvailableMessage;

import java.util.List;


/**
 * Dao interface for {@link AvailableMessage}
 *
 * @author Alexander Menz - menz@synyx.de
 */
public interface AvailableMessageDao extends GenericDao<AvailableMessage, Long> {

    /**
     * Returns all {@link AvailableMessage}s for a given basename.
     *
     * @param basename the basename to return {@link AvailableMessage}s for.
     * @return all {@link AvailableMessage}s within the given basename
     */
    List<AvailableMessage> findByBasename(String basename);


    /**
     * Returns all {@link AvailableMessage}s that match a given basename and key. This returns a {@link List} of
     * {@link AvailableMessage}s because some databases might restrict matches not case-sensitive which may lead to
     * returning keys "aA" and "aa" if searched for "AA".
     *
     * @param basename the basename to receive {@link AvailableMessage}s
     * @param key the key to receive {@link AvailableMessage}s
     * @return all {@link AvailableMessage}s matching the basename and key.
     */
    List<AvailableMessage> findByBasenameAndKey(String basename, String key);
}
