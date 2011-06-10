package org.synyx.minos.i18n.util;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.NotReadablePropertyException;
import org.synyx.minos.i18n.domain.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CollationUtilsUnitTest {

    private final String FIELD_NAME = "key";
    private final String VALUE = "foo";

    private Collection<Message> collection;
    private Message message;

    @Before
    public void setUp() {
        collection = new ArrayList<Message>();

        message = new Message();
        message.setKey(VALUE);

        Message noiseMessage = new Message();
        noiseMessage.setKey("bar");

        collection.add(noiseMessage);
        collection.add(message);
    }

    @Test
    public void testGetRealMatchEverythingNull() {
        Assert.assertNull(CollationUtils.getRealMatch(null, null, null));
    }

    @Test
    public void testGetRealMatchFieldNameAndValueNull() {
        Assert.assertNull(CollationUtils.getRealMatch(collection, null, null));
    }

    @Test
    public void testGetRealMatchValueNull() {
        Assert.assertNull(CollationUtils.getRealMatch(collection, FIELD_NAME, null));
    }

    @Test
    public void testGetRealMatchFieldNameNull() {
        Assert.assertNull(CollationUtils.getRealMatch(collection, null, VALUE));
    }

    @Test
    public void testGetRealMatchCollectionEmpty() {
        Assert.assertNull(CollationUtils.getRealMatch(Collections.<String>emptyList(), FIELD_NAME, VALUE));
    }

    @Test
    public void testGetRealMatchValueEmpty() {
        CollationUtils.getRealMatch(collection, FIELD_NAME, "");
    }

    @Test(expected = NotReadablePropertyException.class)
    public void testGetRealMatchFieldNameEmpty() {
        CollationUtils.getRealMatch(collection, "", VALUE);

    }

    @Test
    public void testGetRealMatch() {
        Assert.assertEquals(CollationUtils.getRealMatch(collection, FIELD_NAME, VALUE), message);
    }


}
