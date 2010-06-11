/**
 * 
 */
package org.synyx.minos.core.web.menu;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;
import org.synyx.minos.core.domain.User;


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

        User user = Mockito.mock(User.class);
        when(first.getUrl(user)).thenReturn("FOO");
        when(item.getSubMenues()).thenReturn(Arrays.asList(first, second));

        String url = strategy.resolveUrl(user, item);

        assertThat(url, is("FOO"));
    }


    @Test
    public void resolvesToNullIfNoSubmenues() {

        MenuItem item = Mockito.mock(MenuItem.class);

        FirstSubMenuUrlResolvingStrategy strategy = new FirstSubMenuUrlResolvingStrategy();
        User user = Mockito.mock(User.class);

        when(item.getSubMenues()).thenReturn(new ArrayList<MenuItem>());

        String url = strategy.resolveUrl(user, item);

        assertNull(url);
    }

}
