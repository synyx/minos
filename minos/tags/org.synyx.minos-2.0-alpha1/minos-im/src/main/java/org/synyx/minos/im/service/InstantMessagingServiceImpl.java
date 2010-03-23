package org.synyx.minos.im.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.authentication.AuthenticationService;
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

    private AuthenticationService authenticationService;
    private InstantMessageDao instantMessageDao;


    /**
     * Setter to inject an {@link AuthenticationService} that allows transparent
     * access to the currently authenticated user.
     * 
     * @param authenticationService the authenticationService to set
     */
    public void setAuthenticationService(
            AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }


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
     * com.synyx.minos.im.service.InstantMessagingService#getIncomingMessages()
     */
    public List<InstantMessage> getIncomingMessages() {

        User user = getCurrentUser();

        return null == user ? new ArrayList<InstantMessage>()
                : getIncomingMessages(user);
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

        InstantMessage example = new InstantMessage();
        example.setReceipient(user);
        example.setInInbox(true);

        return instantMessageDao.readByExample(pageable, example);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#getOutgoingMessages()
     */
    public List<InstantMessage> getOutgoingMessages() {

        User user = getCurrentUser();

        return null == user ? new ArrayList<InstantMessage>()
                : getOutgoingMessages(user);
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

        InstantMessage example = new InstantMessage();
        example.setSender(user);
        example.setInOutbox(true);

        return instantMessageDao.readByExample(example);
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

        InstantMessage example = new InstantMessage();
        example.setSender(user);

        return instantMessageDao.readByExample(pageable, example);
    }


    /**
     * Returns the current user if an {@link AuthenticationService} was
     * configured or {@code null} if no {@link AuthenticationService} was
     * configured. {@code Null} can also be returned if there is no user
     * authenticated currently.
     * 
     * @return
     */
    private User getCurrentUser() {

        return null == authenticationService ? null : authenticationService
                .getCurrentUser();
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
     * com.synyx.minos.im.service.InstantMessagingService#delete(java.lang.Long)
     */
    public void delete(Long id) {

        if (!instantMessageDao.exists(id)) {
            throw new IllegalArgumentException(String.format(
                    "No message with id %s found!", id));
        }

        delete(instantMessageDao.readByPrimaryKey(id));
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.im.service.InstantMessagingService#delete(com.synyx.minos
     * .im.domain.InstantMessage)
     */
    public void delete(InstantMessage message) {

        instantMessageDao.delete(message);
    }
}
