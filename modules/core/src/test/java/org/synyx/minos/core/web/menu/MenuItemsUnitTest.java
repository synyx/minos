package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicate;


/**
 * Unit test for {@link MenuItems}.
 * 
 * @author Oliver Gierke
 */
public class MenuItemsUnitTest {

    private MenuItem first;
    private MenuItem second;
    private MenuItem third;

    private MenuItems items;


    @Before
    public void setUp() {

        third = MenuItem.create("third").withUrl("/foo").build();
        second = MenuItem.create("second").withUrl("/foo").build();
        first = MenuItem.create("first").withUrl("/foo").withSubmenues(second, third).build();

        items = new MenuItems(first);
    }


    @Test
    public void calculatesSizeCorrectly() throws Exception {

        assertThat(items.size(), is(1));
    }


    @Test
    public void containsMenuItems() throws Exception {

        assertContains(items, true, first, second, third);
    }


    @Test
    public void filtersMenuItemCorrectly() throws Exception {

        MenuItems result = items.filter(Arrays.asList(new Predicate<MenuItem>() {

            @Override
            public boolean apply(MenuItem input) {

                return input.getId() != third.getId();
            }
        }));

        assertContains(result, true, first, second);
        assertContains(result, false, third);
    }


    /**
     * Asserts that the given {@link MenuItems} instance contains or does not contain the given {@link MenuItem}s.
     * 
     * @param target the {@link MenuItems} that sould be considered
     * @param contains whether the {@link MenuItems} should contain the given {@link MenuItem}s or whether it must not
     *            contain them
     * @param menuItems the {@link MenuItem}s that should be tested for
     */
    private void assertContains(MenuItems target, boolean contains, MenuItem... menuItems) {

        for (MenuItem item : menuItems) {
            assertThat(target.contains(item), is(contains));
        }
    }
}
