package org.synyx.minos.core.web.menu;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.synyx.minos.core.domain.User;


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

        assertEquals("/url", menuItem.getUrl(null));
    }


    @Test
    public void replacesUserCorrectly() throws Exception {

        String url = String.format("/foo/%s/resume", MenuItem.USER_PLACEHOLDER);
        MenuItem item = MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).withUrl(url).build();

        User user = new User("oliver.gierke", "gierke@synyx.de", "password");
        assertEquals("/foo/oliver.gierke/resume", item.getUrl(user));
    }


    @Test
    public void removesPlaceholderCorrectlyIfNoUserProvided() throws Exception {

        String url = String.format("/foo/%s/resume", MenuItem.USER_PLACEHOLDER);
        MenuItem item = MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).withUrl(url).build();

        assertEquals("/foo/resume", item.getUrl(null));
    }


    @Test
    public void ordersSubmenuesCorrectly() throws Exception {

        MenuItem first = MenuItem.create("SOME").withKeyBase("keyBase").withPosition(1).withUrl("url").build();
        MenuItem second = MenuItem.create("SOME").withKeyBase("keyBase").withPosition(10).withUrl("url").build();

        MenuItem item =
                MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).withUrl("url").withSubmenues(second,
                        first).build();

        assertThat(item.getSubMenues().get(0), is(first));
        assertThat(item.getSubMenues().get(1), is(second));
    }
}
