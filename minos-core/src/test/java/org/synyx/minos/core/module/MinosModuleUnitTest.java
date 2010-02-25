package org.synyx.minos.core.module;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.internal.MinosModule;
import org.synyx.minos.umt.web.UserForm;


/**
 * Unit test for {@link MinosModule}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosModuleUnitTest {

    private static final String PACKAGE = "org.synyx.minos.core";


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullIdentifiers() throws Exception {

        new MinosModule(null);
    }


    @Test
    public void returnsCorrectDefaultBasePackage() throws Exception {

        assertThat(new MinosModule(Core.IDENTIFIER).getBasePackage(),
                is(PACKAGE));
    }


    @Test
    public void preventsNullLifecycle() throws Exception {

        MinosModule info = new MinosModule(Core.IDENTIFIER);
        assertThat(info.getLifecycle(), is(notNullValue()));

        info.setLifecycle(null);
        assertThat(info.getLifecycle(), is(notNullValue()));
    }


    @Test
    public void returnsModuleResource() throws Exception {

        Resource resource =
                new MinosModule(Core.IDENTIFIER)
                        .getModuleResource("module-context.xml");

        assertTrue(resource.getFile().exists());
    }


    @Test
    public void claimsTypeOwnershipCorrectly() throws Exception {

        Module module = new MinosModule(Core.IDENTIFIER);

        assertTrue(module.isModuleType(User.class));
        assertFalse(module.isModuleType(UserForm.class));
    }


    @Test
    public void returnsModulesCorrectly() throws Exception {

        MinosModule core = new MinosModule("core");

        MinosModule umt = new MinosModule("umt");
        umt.setDependsOn(Arrays.asList(core));

        MinosModule another = new MinosModule("another");
        another.setDependsOn(Arrays.asList(umt));

        assertThat(another.getDependants(), hasItems((Module) umt, core));
    }
}
