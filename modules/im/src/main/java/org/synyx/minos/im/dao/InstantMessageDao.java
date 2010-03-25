package org.synyx.minos.im.dao;

import java.util.List;

import org.synyx.hades.dao.ExtendedGenericDao;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.im.domain.InstantMessage;



/**
 * DAO interface for {@link InstantMessage}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface InstantMessageDao extends
        ExtendedGenericDao<InstantMessage, Long> {

    /**
     * Returns all {@link InstantMessage}es of the given {@code receipient}.
     * 
     * @param receipient
     * @return
     */
    List<InstantMessage> findByReceipient(User receipient);


    /**
     * Returns all messages the given {@code sender} received.
     * 
     * @param sender
     * @return
     */
    List<InstantMessage> findBySender(User sender);
}
