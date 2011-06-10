package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;


/**
 * Unit test for {@link SimpleMenuAssembler}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class SimpleMenuAssemblerUnitTest {

    @Test
    public void testAssemblesToMain() {

        MenuItem m1item = MenuItem.create("1").withPosition(1).withUrl("foo").build();
        Menu m1 = Menu.create(m1item);
        MenuItem m2item = MenuItem.create("2").withPosition(1).withUrl("foo").build();
        Menu m2 = Menu.create(m2item);

        SimpleMenuAssembler assembler = new SimpleMenuAssembler();

        Map<String, MenuItems> result = assembler.assembleMenus(new MenuItems(m1, m2));
        assertThat(result.size(), is(1));
        assertThat(result.containsKey("MAIN"), is(true));
        assertThat(result.get("MAIN").size(), is(2));
        assertThat(result.get("MAIN").contains(m1), is(true));
        assertThat(result.get("MAIN").contains(m2), is(true));

    }
}
