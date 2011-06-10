package org.synyx.minos.core.bootstrap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.umt.UmtPermissions;
import org.synyx.minos.umt.service.UserManagement;

import java.util.Set;


/**
 * Simple unit test for {@link MinosCoreInstaller}.
 *
 * @author Stefan Kuhn - kuhn@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MinosCoreInstallerUnitTest {

    @Mock
    private UserManagement userManagement;

    @Test
    public void testCreateRoles() {

        MinosCoreInstaller minosCoreInstaller = new MinosCoreInstaller();

        minosCoreInstaller.setUserManagement(userManagement);

        Role adminRole = minosCoreInstaller.createAdmin();
        Role userRole = minosCoreInstaller.createUser();
        Role testRole = new Role("TEST");

        assertTrue(adminRole.isSystemRole() == Boolean.TRUE);
        assertTrue(userRole.isSystemRole() == Boolean.TRUE);
        assertFalse(testRole.isSystemRole() == Boolean.TRUE);

        Set<String> permissionsAdmin = adminRole.getPermissions();
        final String UMT_TEST = "UMT_TEST";

        assertTrue(permissionsAdmin.contains(UmtPermissions.UMT_ADMIN));
        assertFalse(permissionsAdmin.contains(UMT_TEST));
    }
}
