package com.synyx.minos.im.domain;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.im.domain.InstantMessage;


/**
 * Unit test for {@link InstantMessage}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class InstantMessageUnitTest {

    private InstantMessage first;
    private InstantMessage second;


    /**
     * Creates the first message to be send at 9:00 as well as the second one to
     * be send at 10:00.
     */
    @Before
    public void setUp() {

        first = new InstantMessage();
        first.setSendDate(new DateTime(2008, 10, 10, 9, 0, 0, 0));

        second = new InstantMessage();
        second.setSendDate(new DateTime(2008, 10, 10, 10, 0, 0, 0));
    }


    /**
     * Asserts that messages sent at the exactly same time are considered equal
     * in order.
     */
    @Test
    public void sameSendTimeIsEquals() {

        assertEquals(0, first.compareTo(first));
    }


    /**
     * Asserts that a message sent earlier is considered smaller.
     */
    @Test
    public void earlierIsSmaller() {

        assertTrue(0 > first.compareTo(second));
    }


    /**
     * Asserts that a message sent later is considered larger.
     */
    @Test
    public void laterIsLarger() {

        assertTrue(0 < second.compareTo(first));
    }


    /**
     * Asserts that unsent messages are always considered smaller.
     */
    @Test
    public void unsendIsAlwaysSmaller() {

        assertTrue(0 < first.compareTo(new InstantMessage()));
        assertTrue(0 > new InstantMessage().compareTo(first));
        assertTrue(0 == new InstantMessage().compareTo(new InstantMessage()));
    }
}
