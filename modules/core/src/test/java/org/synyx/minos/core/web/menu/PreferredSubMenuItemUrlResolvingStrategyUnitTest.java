package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link PreferredSubMenuItemUrlResolvingStrategy}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class PreferredSubMenuItemUrlResolvingStrategyUnitTest {

    MenuItem first;
    MenuItem second;
    MenuItem third;


    @Before
    public void setUp() {

        first = MenuItem.create("first").withUrl("BAR").build();
        second = MenuItem.create("second").withUrl("BAR").build();
        third = MenuItem.create("third").withUrl("FOO").build();
    }


    @Test
    public void resolvesUrlOfFirstSubMenu() {

        MenuItem item = MenuItem.create("item").withSubmenues(second, third).build();

        PreferredSubMenuItemUrlResolvingStrategy strategy = new PreferredSubMenuItemUrlResolvingStrategy(first, third);
        String url = strategy.resolveUrl(item);

        assertThat(url, is("FOO"));
    }


    @Test
    public void resolvesToNullIfNoneFound() {

        MenuItem item = MenuItem.create("item").withSubmenues(second).build();

        PreferredSubMenuItemUrlResolvingStrategy strategy = new PreferredSubMenuItemUrlResolvingStrategy(first, third);
        String url = strategy.resolveUrl(item);

        assertNull(url);
    }

}
