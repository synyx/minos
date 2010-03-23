package org.synyx.minos.core.module.internal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.synyx.minos.core.Core;
import org.synyx.minos.core.module.Lifecycle;
import org.synyx.minos.umt.Umt;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MinosModuleManagerUnitTest {

    private MinosModuleManager manager;

    @Mock
    private ModuleDescriptorDao dao;

    @Mock
    private Lifecycle coreLifecycle;
    @Mock
    private Lifecycle umtLifecycle;

    @Mock
    private ApplicationContext context;

    private ContextRefreshedEvent event;


    @Before
    public void setUp() {

        MinosModule core = new MinosModule(Core.IDENTIFIER);
        core.setLifecycle(coreLifecycle);

        MinosModule umt = new MinosModule(Umt.IDENTIFIER);
        umt.setLifecycle(umtLifecycle);
        umt.setDependsOn(Arrays.asList(core));

        manager = new MinosModuleManager(dao);
        manager.setModules(Arrays.asList(core, umt));
        manager.setApplicationContext(context);

        event = new ContextRefreshedEvent(context);
    }


    @Test
    public void invokesInstallPhaseOnFirstStart() throws Exception {

        ModuleDescriptor coreDescriptor = new ModuleDescriptor(Core.IDENTIFIER);
        ModuleDescriptor umtDescriptor = new ModuleDescriptor(Umt.IDENTIFIER);

        when(dao.findByIdentifier(Core.IDENTIFIER)).thenReturn(coreDescriptor);
        when(dao.findByIdentifier(Umt.IDENTIFIER)).thenReturn(umtDescriptor);

        manager.onApplicationEvent(event);

        verify(coreLifecycle).install();
        verify(umtLifecycle).install();

        assertTrue(coreDescriptor.isInstalled());
        assertTrue(umtDescriptor.isInstalled());
        verify(dao).save(coreDescriptor);
        verify(dao).save(umtDescriptor);
    }


    @Test
    public void doesNotInstallAlreadyInstalledModules() throws Exception {

        markInstalled(Core.IDENTIFIER);

        manager.onApplicationEvent(event);

        verify(coreLifecycle, never()).install();
    }


    @Test
    public void startsModuleOnFirstRefreshedEvent() throws Exception {

        markInstalled(Core.IDENTIFIER);

        manager.onApplicationEvent(event);
        verify(coreLifecycle).onStart();
        assertTrue(manager.isAvailable(Core.IDENTIFIER));
    }


    @Test
    public void doesNotStartModuleTwice() throws Exception {

        markInstalled(Core.IDENTIFIER);

        manager.onApplicationEvent(event);
        manager.onApplicationEvent(event);
        verify(coreLifecycle).onStart();
    }


    @Test
    public void shutsDownModulesInReverseOrder() throws Exception {

        markInstalled(Core.IDENTIFIER);
        markInstalled(Umt.IDENTIFIER);

        manager.onApplicationEvent(event);
        manager.destroy();

        InOrder order = inOrder(umtLifecycle, coreLifecycle);

        order.verify(umtLifecycle).onStop();
        order.verify(coreLifecycle).onStop();
    }


    private void markInstalled(String identifier) {

        ModuleDescriptor descriptor = new ModuleDescriptor(identifier);
        descriptor.setInstalled();

        when(dao.findByIdentifier(identifier)).thenReturn(descriptor);
    }
}
