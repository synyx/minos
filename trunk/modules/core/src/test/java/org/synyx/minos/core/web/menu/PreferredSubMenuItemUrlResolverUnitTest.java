package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link PreferredSubMenuItemUrlResolver}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class PreferredSubMenuItemUrlResolverUnitTest {

    MenuItem firstItem;
    Menu first;
    MenuItem secondItem;
    Menu second;
    MenuItem thirdItem;
    Menu third;


    @Before
    public void setUp() {

        firstItem = MenuItem.create("first").withUrl("BAR").build();
        first = Menu.create(firstItem);
        secondItem = MenuItem.create("second").withUrl("BAR").build();
        second = Menu.create(secondItem);
        thirdItem = MenuItem.create("third").withUrl("FOO").build();
        third = Menu.create(thirdItem);
    }


    @Test
    public void resolvesUrlOfFirstSubMenu() {

        PreferredSubMenuItemUrlResolver strategy = new PreferredSubMenuItemUrlResolver(first, third);

        MenuItem itemItem = MenuItem.create("item").withUrlResolver(strategy).build();
        Menu item = Menu.create(itemItem, new MenuItems(second, third));

        String url = strategy.resolveUrl(item);

        assertThat(url, is("FOO"));
    }


    @Test
    public void resolvesToNullIfNoneFound() {

        PreferredSubMenuItemUrlResolver strategy = new PreferredSubMenuItemUrlResolver(first, third);

        MenuItem itemItem = MenuItem.create("item").withUrlResolver(strategy).build();
        Menu item = Menu.create(itemItem, new MenuItems(second));

        String url = strategy.resolveUrl(item);

        assertNull(url);
    }

}
