package org.synyx.minos.core.web.menu;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;


/**
 * Unit test for {@link MenuItem}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MenuItemUnitTest {

    @Test(expected = IllegalStateException.class)
    public void rejectsItemsWithoutUrl() throws Exception {

        MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).build();

    }


    @Test(expected = IllegalStateException.class)
    public void rejectsItemsWithoutUrlAndSubMenuesAtAll() {

        MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).build();
    }


    @Test
    public void returnsUrlOfFirstSubMenuItemIfNoUrlConfigured() throws Exception {

        MenuItem subMenuItem = MenuItem.create("some").withKeyBase("keyBase").withPosition(0).withUrl("/url").build();
        MenuItem menuItem =
                MenuItem.create("some").withKeyBase("keyBase").withPosition(0).withSubmenu(subMenuItem).build();

        assertEquals("/url", menuItem.getUrl());
    }


    @Test
    public void ordersSubmenuesCorrectly() throws Exception {

        MenuItem first = MenuItem.create("SOME").withKeyBase("keyBase").withPosition(1).withUrl("url").build();
        MenuItem second = MenuItem.create("SOME").withKeyBase("keyBase").withPosition(10).withUrl("url").build();

        MenuItem item =
                MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).withUrl("url")
                        .withSubmenues(second, first).build();

        Iterator<MenuItem> iterator = item.getSubMenues().iterator();

        assertThat(iterator.next(), is(first));
        assertThat(iterator.next(), is(second));
    }
}
