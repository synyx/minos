package org.synyx.minos.core.authentication;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.internal.MinosModule;
import org.synyx.minos.umt.Umt;
import org.synyx.minos.umt.UmtPermissions;


/**
 * Unit test for {@link MinosPermissionAwareUnitTest}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosPermissionAwareUnitTest {

    @Test
    public void findsPermissionClassInBasePackage() throws Exception {

        Module module = new MinosModule(Umt.IDENTIFIER);

        PermissionAware declarator = new ModulePermissionAware(module);

        assertThat(declarator.getPermissions(),
                hasItem(UmtPermissions.UMT_ADMIN));
    }
}
