/**
 * 
 */
package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;
import org.synyx.minos.core.domain.User;


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

        User user = Mockito.mock(User.class);
        when(third.getUrl(user)).thenReturn("FOO");
        when(item.getSubMenues()).thenReturn(Arrays.asList(second, third));

        String url = strategy.resolveUrl(user, item);

        assertThat(url, is("FOO"));
    }


    @Test
    public void resolvesToNullIfNoneFound() {

        MenuItem item = Mockito.mock(MenuItem.class);

        MenuItem first = Mockito.mock(MenuItem.class);
        MenuItem second = Mockito.mock(MenuItem.class);
        MenuItem third = Mockito.mock(MenuItem.class);

        PreferredSubMenuItemUrlResolvingStrategy strategy = new PreferredSubMenuItemUrlResolvingStrategy(first, third);
        User user = Mockito.mock(User.class);
        when(item.getSubMenues()).thenReturn(Arrays.asList(second));

        String url = strategy.resolveUrl(user, item);

        assertNull(url);
    }

}
