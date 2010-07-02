/**
 * 
 */
package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MenuUnitTest {

    private Menu menu;

    MenuItem m1_2;


    /**
     * <pre>
     *                1               2
     *             /     \
     *          1_1     1_2
     *                 /  \
     *             1_2_1   1_2_2
     * </pre>
     */
    @Before
    public void setup() {

        MenuItem m1_1 = MenuItem.create("1_1").withPosition(1).withUrl("foo").build();
        MenuItem m1_2_1 = MenuItem.create("1_2_1f").withPosition(1).withUrl("foo").build();
        MenuItem m1_2_2 = MenuItem.create("1_2_2").withPosition(1).withUrl("foo").build();
        m1_2 = MenuItem.create("1_2").withPosition(1).withUrl("foo").withSubmenues(m1_2_1, m1_2_2).build();
        MenuItem m1 = MenuItem.create("1").withPosition(1).withUrl("foo").withSubmenues(m1_1, m1_2).build();
        MenuItem m2 = MenuItem.create("2").withPosition(1).withUrl("foo").build();

        menu = new Menu(m1, m2);

    }


    @Test
    public void testHasFirstLevel() {

        assertThat(menu.hasMenuItem("1"), is(true));
    }


    @Test
    public void testHasSecondLevel() {

        assertThat(menu.hasMenuItem("1_2"), is(true));
    }
}
