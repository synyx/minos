/**
 * 
 */
package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class FirstSubMenuUrlResolverUnitTest {

    @Test
    public void resolvesUrlOfFirstSubMenu() {

        FirstSubMenuUrlResolver strategy = new FirstSubMenuUrlResolver();

        MenuItem first = Mockito.mock(MenuItem.class);
        when(first.getUrl()).thenReturn("FOO");

        MenuItem second = Mockito.mock(MenuItem.class);
        MenuItem item = MenuItem.create("BAR").withSubmenues(first, second).build();

        String url = strategy.resolveUrl(item);

        assertThat(url, is("FOO"));
    }


    @Test
    public void resolvesToNullIfNoSubmenues() {

        MenuItem item = Mockito.mock(MenuItem.class);

        FirstSubMenuUrlResolver strategy = new FirstSubMenuUrlResolver();

        when(item.getSubMenues()).thenReturn(new MenuItems());

        String url = strategy.resolveUrl(item);

        assertNull(url);
    }

}
