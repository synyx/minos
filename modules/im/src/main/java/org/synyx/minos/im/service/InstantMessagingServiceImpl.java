package org.synyx.minos.im.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.im.dao.InstantMessageDao;
import org.synyx.minos.im.domain.InstantMessage;
import org.synyx.minos.util.Assert;


/**
 * {@link InstantMessagingService} implementation.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class InstantMessagingServiceImpl implements InstantMessagingService {

    private InstantMessageDao instantMessageDao;


    /**
     * Setter to inject an {@link InstantMessageDao}.
     * 
     * @param instantMessageDao the instantMessageDao to set
     */
    public void setInstantMessageDao(InstantMessageDao instantMessageDao) {

        this.instantMessageDao = instantMessageDao;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#send(com.synyx.minos
     * .im.domain.InstantMessage)
     */
    public void send(InstantMessage message) {

        message.send();

        instantMessageDao.save(message);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#getIncomingMessages
     * (com.synyx.minos.core.domain.User)
     */
    public List<InstantMessage> getIncomingMessages(User user) {

        Assert.notNull(user, "User must not be null!");

        return instantMessageDao.findByReceipient(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#getIncomingMessages
     * (com.synyx.minos.core.domain.User, org.synyx.hades.domain.page.Pageable)
     */
    public Page<InstantMessage> getIncomingMessages(User user, Pageable pageable) {

        Assert.notNull(user, "User must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");

        return instantMessageDao.findInboxMessagesFor(user, pageable);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#getOutgoingMessages
     * (com.synyx.minos.core.domain.User)
     */
    public List<InstantMessage> getOutgoingMessages(User user) {

        Assert.notNull(user, "User must not be null!");

        return instantMessageDao.findOutboxMessagesFor(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#getOutgoingMessages
     * (com.synyx.minos.core.domain.User, org.synyx.hades.domain.page.Pageable)
     */
    public Page<InstantMessage> getOutgoingMessages(User user, Pageable pageable) {

        Assert.notNull(user, "User must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");

        return instantMessageDao.findOutboxMessagesFor(user, pageable);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#getIncomingMessage
     * (java.lang.Long)
     */
    public InstantMessage getInstantMessage(Long id) {

        return instantMessageDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.im.service.InstantMessagingService#getAllMessages()
     */
    public List<InstantMessage> getAllMessages() {

        return instantMessageDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#delete(com.synyx.minos
     * .im.domain.InstantMessage)
     */
    public void delete(InstantMessage message) {

        Long id = message.getId();

        if (!instantMessageDao.exists(id)) {
            throw new IllegalArgumentException(String.format(
                    "No message with id %s found!", id));
        }

        instantMessageDao.delete(message);
    }
}
