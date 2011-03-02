package org.synyx.minos.core.module;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.springframework.core.io.Resource;

import org.synyx.minos.core.Core;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.internal.MinosModule;
import org.synyx.minos.umt.web.UserForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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


    @Test(expected = IllegalArgumentException.class)
    public void rejectsInvalidIdentifiers_StartsNumber() {

        new MinosModule("1identifier");
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsInvalidIdentifiers_StartsDot() {

        new MinosModule(".identifier");
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsInvalidIdentifiers_ContainsInvalidChar() {

        new MinosModule("an-identifier");
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsInvalidIdentifiers_EndsDot() {

        new MinosModule("identifier.");
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsInvalidIdentifiers_DoubleDot() {

        new MinosModule("an..identifier");
    }


    @Test
    public void acceptsValidIdentifiers_Plain() {

        new MinosModule("identifier");
    }


    @Test
    public void acceptsValidIdentifiers_Dotted() {

        new MinosModule("an.identifier");
    }


    @Test
    public void acceptsValidIdentifiers_WithNumbers() {

        new MinosModule("identifier123");
    }


    @Test
    public void returnsCorrectDefaultBasePackage() throws Exception {

        assertThat(new MinosModule(Core.IDENTIFIER).getBasePackage(), is(PACKAGE));
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

        Resource resource = new MinosModule(Core.IDENTIFIER).getModuleResource("module-context.xml");

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

        // Checks transient resolving
        List<Module> dependencies = another.getDependencies();
        assertThat(dependencies, hasItems((Module) umt, core));
        assertThat(dependencies.get(0), is((Module) umt));
        assertThat(dependencies.get(1), is((Module) core));

        // Check correct natural order
        List<Module> modules = new ArrayList<Module>();
        modules.add(another);
        modules.add(core);
        modules.add(umt);

        Collections.sort(modules);

        assertThat(modules.get(0), is((Module) another));
        assertThat(modules.get(1), is((Module) umt));
        assertThat(modules.get(2), is((Module) core));
    }
}
