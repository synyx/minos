package org.synyx.minos.core.module.config;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.core.security.ModulePermissionAware;


/**
 * Integration test for {@link ModuleBeanDefinitionParser}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ModuleBeanDefinitionParserIntegrationTest {

    @Autowired
    private ApplicationContext context;


    @Test
    public void registersFooModule() throws Exception {

        Module module = context.getBean("foo", Module.class);
        assertThat(module.getIdentifier(), is("foo"));
    }


    @Test
    public void registersMessageSourcesForEachModule() throws Exception {

        // 3 module ones + ApplicationContext itself
        assertThat(context.getBeanNamesForType(MessageSource.class).length, is(4));
    }


    @Test
    public void registersDependsOnCorrectly() throws Exception {

        Module foo = context.getBean("foo", Module.class);
        Module bar = context.getBean("bar", Module.class);

        Module module = context.getBean("foobar", Module.class);

        assertThat(module.getDirectDependants(), hasItems(foo, bar));
    }


    @Test
    public void wiresLifecycleCorrectly() throws Exception {

        Module foo = context.getBean("foo", Module.class);

        assertThat(foo.getLifecycle(), is(not(instanceOf(SimpleNoOpLifecycle.class))));
    }


    @Test
    public void registersPermissionAwareBeansForModules() throws Exception {

        Map<String, ModulePermissionAware> beans = context.getBeansOfType(ModulePermissionAware.class);

        assertThat(beans.entrySet().size(), is(3));
    }
}
