package org.synyx.minos.i18n.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AvailableMessageUnitTest {
    private final String BASE_NAME = "de";
    private final String KEY = "foo";
    private final String MESSAGE = "bar";

    private AvailableMessage availableMessage;

    @Before
    public void setUp() {
        availableMessage = new AvailableMessage(BASE_NAME, KEY, MESSAGE);
    }

    @Test
    public void testAvailableMessage() {
        Assert.assertEquals(availableMessage.getBasename(), BASE_NAME);
        Assert.assertEquals(availableMessage.getKey(), KEY);
        Assert.assertEquals(availableMessage.getMessage(), MESSAGE);
    }
}
