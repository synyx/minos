package org.synyx.minos.core.web.menu;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.synyx.minos.core.domain.User;



/**
 * Unit test for {@link MenuItem}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MenuItemUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void rejectsItemsWithoutUrl() throws Exception {

        new MenuItem("keyBase", 0, (String) null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsItemsWithoutSubMenuesAtAll() {
        
        new MenuItem("keyBase", 0, new MenuItem[] {});
    }


    @Test
    public void returnsUrlOfFirstSubMenuItemIfNoUrlConfigured()
            throws Exception {

        MenuItem subMenuItem = new MenuItem("keyBase", 0, "/url");
        MenuItem menuItem = new MenuItem("keyBase", 0, subMenuItem);

        assertEquals("/url", menuItem.getUrl(null));
    }

    @Test
    public void calculatesPermissionsCorrectly() throws Exception {

        MenuItem parent =
                new MenuItem("keyBase", 0, "url").add("MY_PERMISSION");
        MenuItem child =
                new MenuItem("keyBase", 0, "url").add("YOUR_PERMISSION");

        parent.withSub(child);

        assertEquals(1, parent.getPermissions().size());
        assertTrue(child.getPermissions().containsAll(
                Arrays.asList("MY_PERMISSION", "YOUR_PERMISSION")));
    }


    @Test
    public void replacesUserCorrectly() throws Exception {

        String url = String.format("/foo/%s/resume", MenuItem.USER_PLACEHOLDER);
        MenuItem item = new MenuItem("keyBase", 0, url);

        User user = new User("oliver.gierke", "gierke@synyx.de", "password");
        assertEquals("/foo/oliver.gierke/resume", item.getUrl(user));
    }


    @Test
    public void removesPlaceholderCorrectlyIfNoUserProvided() throws Exception {

        String url = String.format("/foo/%s/resume", MenuItem.USER_PLACEHOLDER);
        MenuItem item = new MenuItem("keyBase", 0, url);

        assertEquals("/foo/resume", item.getUrl(null));
    }
}
