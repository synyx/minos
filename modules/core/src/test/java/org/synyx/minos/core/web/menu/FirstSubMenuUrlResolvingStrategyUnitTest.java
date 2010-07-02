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
public class FirstSubMenuUrlResolvingStrategyUnitTest {

    @Test
    public void resolvesUrlOfFirstSubMenu() {

        FirstSubMenuUrlResolvingStrategy strategy = new FirstSubMenuUrlResolvingStrategy();

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

        FirstSubMenuUrlResolvingStrategy strategy = new FirstSubMenuUrlResolvingStrategy();

        when(item.getSubMenues()).thenReturn(new MenuItems());

        String url = strategy.resolveUrl(item);

        assertNull(url);
    }

}
