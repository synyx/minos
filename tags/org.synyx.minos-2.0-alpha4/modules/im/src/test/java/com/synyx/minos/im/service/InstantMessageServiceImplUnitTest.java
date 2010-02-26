package com.synyx.minos.im.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.im.dao.InstantMessageDao;
import org.synyx.minos.im.domain.InstantMessage;
import org.synyx.minos.im.service.InstantMessagingServiceImpl;


/**
 * Unit test for {@link InstantMessagingServiceImpl}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class InstantMessageServiceImplUnitTest {

    private InstantMessagingServiceImpl service;

    @Mock
    private InstantMessageDao instantMessageDao;


    @Before
    public void setUp() {

        service = new InstantMessagingServiceImpl();
        service.setInstantMessageDao(instantMessageDao);
    }


    /**
     * Asserts that a sended message has a send date.
     */
    @Test
    public void setsSendDateOnSending() {

        InstantMessage message = new InstantMessage();

        service.send(message);

        assertNotNull(message.getSendDate());
    }
}
