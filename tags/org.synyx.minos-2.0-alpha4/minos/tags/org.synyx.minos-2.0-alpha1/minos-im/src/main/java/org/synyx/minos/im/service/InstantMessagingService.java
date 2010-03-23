package org.synyx.minos.im.service;

import java.util.List;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.Modules;
import org.synyx.minos.im.domain.InstantMessage;



/**
 * Service interface for instant messaging facilities.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Module(Modules.IM)
public interface InstantMessagingService {

    /**
     * Sends the given {@link InstantMessage}. Invoking this method on the
     * message has to result in the receiver having the given message in its
     * inbox as well as the sender having it in it outbox.
     * 
     * @param message
     */
    void send(InstantMessage message);


    /**
     * Returns all messages of the currently authenticated user. If no
     * authenticated users can be found, implementations simply have to return
     * an empty {@link List}.
     * 
     * @return
     */
    List<InstantMessage> getIncomingMessages();


    /**
     * Returns all messages the given {@link User} has received.
     * 
     * @param user
     * @return
     */
    List<InstantMessage> getIncomingMessages(User user);


    /**
     * Returns a page of messages the given {@link User} has received.
     * 
     * @param user
     * @param pageable
     * @return
     */
    Page<InstantMessage> getIncomingMessages(User user, Pageable pageable);


    /**
     * Returns all messages the given {@link User} has sent.
     * 
     * @return
     */
    List<InstantMessage> getOutgoingMessages();


    /**
     * Returns all messages the given {@link User} has sent.
     * 
     * @param user
     * @return
     */
    List<InstantMessage> getOutgoingMessages(User user);


    /**
     * Returns a page of messages the given {@link User} has sent.
     * 
     * @param user
     * @param pageable
     * @return
     */
    Page<InstantMessage> getOutgoingMessages(User user, Pageable pageable);


    /**
     * Returns the {@link InstantMessage} with the given id.
     * 
     * @param id
     * @return
     */
    InstantMessage getInstantMessage(Long id);


    /**
     * Deletes the {@link InstantMessage} with the given id.
     * 
     * @param id
     */
    void delete(Long id);


    /**
     * Deletes the given {@link InstantMessage}.
     * 
     * @param message
     */
    void delete(InstantMessage message);


    /**
     * Returns all messages available in the system.
     * 
     * @return
     */
    List<InstantMessage> getAllMessages();

}
