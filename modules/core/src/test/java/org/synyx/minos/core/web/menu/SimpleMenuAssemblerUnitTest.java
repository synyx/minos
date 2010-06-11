/**
 * 
 */
package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class SimpleMenuAssemblerUnitTest {

    @Test
    public void testAssemblesToMain() {

        Menu menu;
        MenuItem m1 = MenuItem.create("1").withPosition(1).withUrl("foo").build();
        MenuItem m2 = MenuItem.create("2").withPosition(1).withUrl("foo").build();

        menu = new Menu(m1, m2);

        SimpleMenuAssembler assembler = new SimpleMenuAssembler();

        Map<String, Menu> result = assembler.assembleMenues(menu);
        assertThat(result.size(), is(1));
        assertThat(result.containsKey("MAIN"), is(true));
        assertThat(result.get("MAIN").getItems().size(), is(2));
        assertThat(result.get("MAIN").getItems().contains(m1), is(true));
        assertThat(result.get("MAIN").getItems().contains(m2), is(true));

    }
}
