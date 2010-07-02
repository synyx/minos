package org.synyx.minos.core.web.menu.filter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.web.menu.MenuItem;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */

@RunWith(MockitoJUnitRunner.class)
public class PermissionMenuItemFilterUnitTest {

    @Mock
    private AuthenticationService authService;

    private PermissionMenuItemFilter filter;

    private String permA = "A";
    private String permB = "B";

    private MenuItem a;
    private MenuItem b;


    @Before
    public void setup() {

        filter = new PermissionMenuItemFilter(authService);

        a = MenuItem.create("A").withUrl("a").withPosition(1).withPermission(permA).build();
        b = MenuItem.create("B").withUrl("b").withPosition(1).withPermission(permB).build();
    }


    @Test
    public void testCallsHasPermissions() {

        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(a);
        items.add(b);

        filter.apply(a);
        filter.apply(b);
        verify(authService).hasAllPermissions(a.getPermissions());
        verify(authService).hasAllPermissions(b.getPermissions());

    }


    @Test
    public void testFilterNone() {

        assertAAndB(true, true);
    }


    @Test
    public void testFilterBoth() {

        assertAAndB(false, false);
    }


    @Test
    public void testFilterOne() {

        assertAAndB(false, true);
    }


    private void assertAAndB(boolean forA, boolean forB) {

        when(authService.hasAllPermissions(a.getPermissions())).thenReturn(forA);
        when(authService.hasAllPermissions(b.getPermissions())).thenReturn(forB);

        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(a);
        items.add(b);

        List<MenuItem> result = Lists.newArrayList(Iterables.filter(items, filter));
        assertThat(result.contains(a), is(forA));
        assertThat(result.contains(b), is(forB));
    }
}
