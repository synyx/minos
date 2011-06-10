package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

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


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullParent() throws Exception {

        MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).withParent(null).build();

    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullUrlResolver() throws Exception {

        MenuItem.create("SOME").withKeyBase("keyBase").withPosition(0).withUrlResolver(null).build();

    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullId() throws Exception {

        MenuItem.create(null).withKeyBase("keyBase").withPosition(0).withUrl("/url").build();

    }


    @Test
    public void returnsCorrectPaths() throws Exception {

        MenuItem parent = MenuItem.create("PARENT").withKeyBase("keyBase").withPosition(0).withUrl("/url").build();
        MenuItem child =
                MenuItem.create("CHILD").withKeyBase("keyBase").withPosition(0).withUrl("/url/child")
                        .withParent(parent).build();

        assertThat(child.getParentPath(), is("PARENT"));
        assertThat(child.getPath(), is(String.format("PARENT" + MenuItem.PATH_SEPARATOR + "CHILD")));

    }


    @Test
    public void clonesCorrect() {

        List<String> permissions = Arrays.asList("FOO", "BAR");
        MenuItem item =
                MenuItem.create("ITEM").withKeyBase("keyBase").withPosition(0).withUrl("/url").withPermissions(
                        permissions).build();

        MenuItem clone = MenuItem.deepCopy(item).build();

        assertTrue(item != clone);

        // permissions should be equal ...
        assertThat(item.getPermissions(), is(clone.getPermissions()));
        // ...but not same
        assertTrue(item.getPermissions() != clone.getPermissions());

    }


    @Test
    public void changesParentCorrect() {

        MenuItem parent =
                MenuItem.create("ORIGINALPARENT").withKeyBase("keyBase").withPosition(0).withUrl("/url").build();

        MenuItem child =
                MenuItem.create("CHILD").withKeyBase("keyBase").withPosition(0).withUrl("/url").withParent(parent)
                        .build();

        MenuItem newParent =
                MenuItem.create("NEWPARENT").withKeyBase("keyBase").withPosition(0).withUrl("/url").build();

        MenuItem newChild = newParent.createChild(child).build();

        // old parent&child still match
        assertThat(child.getParentPath(), is(parent.getPath()));

        // and the new ones match also
        assertThat(newChild.getParentPath(), is(newParent.getPath()));

    }

}
