package org.synyx.minos.im.dao;

import java.util.List;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.im.domain.InstantMessage;


/**
 * DAO interface for {@link InstantMessage}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface InstantMessageDao extends GenericDao<InstantMessage, Long> {

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


    @Query("select m from InstantMessage m where m.receipient = ?1 and m.inInbox = true")
    Page<InstantMessage> findInboxMessagesFor(User user, Pageable pageable);


    @Query("select m from InstantMessage m where m.sender = ?1 and m.inOutbox = true")
    List<InstantMessage> findOutboxMessagesFor(User user);


    @Query("select m from InstantMessage m where m.sender = ?1 and m.inOutbox = true")
    Page<InstantMessage> findOutboxMessagesFor(User user, Pageable pageable);
}
