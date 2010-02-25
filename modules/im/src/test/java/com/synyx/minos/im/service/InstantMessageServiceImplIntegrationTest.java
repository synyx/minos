package com.synyx.minos.im.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.im.domain.InstantMessage;
import org.synyx.minos.im.service.InstantMessagingService;
import org.synyx.minos.im.service.InstantMessagingServiceImpl;
import org.synyx.minos.test.AbstractModuleIntegrationTest;
import org.synyx.minos.umt.dao.UserDao;



/**
 * Integration test for {@link InstantMessagingServiceImpl}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class InstantMessageServiceImplIntegrationTest extends
        AbstractModuleIntegrationTest {

    @Autowired
    private InstantMessagingService service;

    @Autowired
    private UserDao userDao;

    private User sender;
    private User receipient;


    @Before
    public void setUp() {

        sender = new User("foobar", "foo@bar.de", "password");
        userDao.save(sender);

        receipient = new User("barfoos", "bar@foo.de", "password");
        userDao.save(receipient);
    }


    @Test
    public void assertCorrectSend() {

        InstantMessage message = new InstantMessage();
        message.setSender(sender);
        message.setReceipient(receipient);

        service.send(message);

        assertTrue(service.getIncomingMessages(receipient).contains(message));
        assertTrue(service.getOutgoingMessages(sender).contains(message));
    }
}
