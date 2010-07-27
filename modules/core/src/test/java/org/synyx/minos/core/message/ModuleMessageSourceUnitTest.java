package org.synyx.minos.core.message;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.internal.MinosModule;


/**
 * Unit test for {@link ModuleMessageSourceImpl}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleMessageSourceUnitTest {

    @Test
    public void supportsKeysPrefixedWithModuleId() throws Exception {

        Module module = new MinosModule(Core.IDENTIFIER);

        MessageSourcePlugin plugin = new ModuleMessageSourceImpl(module);
        assertThat(plugin.supports("core"), is(true));
    }


    @Test
    public void supportsNonModuleIdPrefixedKeysIfConfiguredLenient() throws Exception {

        Module module = new MinosModule(Core.IDENTIFIER);

        ModuleMessageSourceImpl plugin = new ModuleMessageSourceImpl(module);
        plugin.setLenient(true);

        assertThat(plugin.supports("core"), is(true));
        assertThat(plugin.supports("foo"), is(true));
    }
}
