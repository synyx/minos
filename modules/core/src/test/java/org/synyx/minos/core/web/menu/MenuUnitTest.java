package org.synyx.minos.core.web.menu;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MenuUnitTest {

    @Test
    public void returnsUrlOfFirstSubMenuItem() throws Exception {

        MenuItem parent =
                MenuItem.create("some").withKeyBase("keyBase").withPosition(0).withUrlResolver(
                        new FirstSubMenuUrlResolver()).build();

        MenuItem child =
                MenuItem.create("some").withKeyBase("keyBase").withPosition(0).withUrl("/url").withParent(parent)
                        .build();

        Menu childMenu = Menu.create(child);
        Menu parentMenu = Menu.create(parent, new MenuItems(childMenu));

        assertEquals("/url", parentMenu.getUrl());
    }


    @Test
    public void ordersSubmenusCorrectly() throws Exception {

        MenuItem parent = MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).withUrl("url").build();

        MenuItem first =
                MenuItem.create("SOME").withKeyBase("keyBase").withPosition(1).withUrl("url").withParent(parent)
                        .build();
        MenuItem second =
                MenuItem.create("SOME").withKeyBase("keyBase").withPosition(10).withUrl("url").withParent(parent)
                        .build();

        MenuItem third =
                MenuItem.create("SOME").withKeyBase("keyBase").withPosition(42).withUrl("url").withParent(parent)
                        .build();

        Menu firstMenu = Menu.create(first);
        Menu secondMenu = Menu.create(second);
        Menu thirdMenu = Menu.create(third);

        Menu parentMenu = Menu.create(parent, new MenuItems(secondMenu, thirdMenu, firstMenu));

        Iterator<Menu> iterator = parentMenu.getSubMenus().iterator();

        assertThat(iterator.next(), is(firstMenu));
        assertThat(iterator.next(), is(secondMenu));
    }
}
