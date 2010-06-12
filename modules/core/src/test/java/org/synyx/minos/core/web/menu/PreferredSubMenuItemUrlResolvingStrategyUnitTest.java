/**
 * 
 */
package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class PreferredSubMenuItemUrlResolvingStrategyUnitTest {

    @Test
    public void resolvesUrlOfFirstSubMenu() {

        MenuItem item = Mockito.mock(MenuItem.class);

        MenuItem first = Mockito.mock(MenuItem.class);
        MenuItem second = Mockito.mock(MenuItem.class);
        MenuItem third = Mockito.mock(MenuItem.class);

        PreferredSubMenuItemUrlResolvingStrategy strategy = new PreferredSubMenuItemUrlResolvingStrategy(first, third);

        when(third.getUrl()).thenReturn("FOO");
        when(item.getSubMenues()).thenReturn(Arrays.asList(second, third));

        String url = strategy.resolveUrl(item);

        assertThat(url, is("FOO"));
    }


    @Test
    public void resolvesToNullIfNoneFound() {

        MenuItem item = Mockito.mock(MenuItem.class);

        MenuItem first = Mockito.mock(MenuItem.class);
        MenuItem second = Mockito.mock(MenuItem.class);
        MenuItem third = Mockito.mock(MenuItem.class);

        PreferredSubMenuItemUrlResolvingStrategy strategy = new PreferredSubMenuItemUrlResolvingStrategy(first, third);
        when(item.getSubMenues()).thenReturn(Arrays.asList(second));

        String url = strategy.resolveUrl(item);

        assertNull(url);
    }

}
