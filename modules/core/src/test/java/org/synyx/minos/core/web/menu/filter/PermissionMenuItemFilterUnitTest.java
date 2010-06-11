/**
 * 
 */
package org.synyx.minos.core.web.menu.filter;

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


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
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

        filter = new PermissionMenuItemFilter();
        filter.setAuthenticationService(authService);

        a = MenuItem.create("A").withUrl("a").withPosition(1).withPermission(permA).build();
        b = MenuItem.create("B").withUrl("b").withPosition(1).withPermission(permB).build();
    }


    @Test
    public void testCallsHasPermissions() {

        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(a);
        items.add(b);

        filter.filterMenuItems(items);
        verify(authService).hasAllPermissions(a.getPermissions());
        verify(authService).hasAllPermissions(b.getPermissions());

    }


    @Test
    public void testFilterNone() {

        when(authService.hasAllPermissions(a.getPermissions())).thenReturn(true);
        when(authService.hasAllPermissions(b.getPermissions())).thenReturn(true);

        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(a);
        items.add(b);

        List<MenuItem> result = filter.filterMenuItems(items);
        assertTrue(result.contains(a));
        assertTrue(result.contains(b));

    }


    @Test
    public void testFilterBoth() {

        when(authService.hasAllPermissions(a.getPermissions())).thenReturn(false);
        when(authService.hasAllPermissions(b.getPermissions())).thenReturn(false);

        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(a);
        items.add(b);

        List<MenuItem> result = filter.filterMenuItems(items);
        assertTrue(!result.contains(a));
        assertTrue(!result.contains(b));

    }


    @Test
    public void testFilterOne() {

        when(authService.hasAllPermissions(a.getPermissions())).thenReturn(false);
        when(authService.hasAllPermissions(b.getPermissions())).thenReturn(true);

        List<MenuItem> items = new ArrayList<MenuItem>();
        items.add(a);
        items.add(b);

        List<MenuItem> result = filter.filterMenuItems(items);
        assertTrue(!result.contains(a));
        assertTrue(result.contains(b));

    }
}
