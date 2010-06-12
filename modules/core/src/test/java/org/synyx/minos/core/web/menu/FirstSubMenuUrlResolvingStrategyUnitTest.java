/**
 * 
 */
package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class FirstSubMenuUrlResolvingStrategyUnitTest {

    @Test
    public void resolvesUrlOfFirstSubMenu() {

        FirstSubMenuUrlResolvingStrategy strategy = new FirstSubMenuUrlResolvingStrategy();

        MenuItem item = Mockito.mock(MenuItem.class);
        MenuItem first = Mockito.mock(MenuItem.class);
        MenuItem second = Mockito.mock(MenuItem.class);

        when(first.getUrl()).thenReturn("FOO");
        when(item.getSubMenues()).thenReturn(Arrays.asList(first, second));

        String url = strategy.resolveUrl(item);

        assertThat(url, is("FOO"));
    }


    @Test
    public void resolvesToNullIfNoSubmenues() {

        MenuItem item = Mockito.mock(MenuItem.class);

        FirstSubMenuUrlResolvingStrategy strategy = new FirstSubMenuUrlResolvingStrategy();

        when(item.getSubMenues()).thenReturn(new ArrayList<MenuItem>());

        String url = strategy.resolveUrl(item);

        assertNull(url);
    }

}
