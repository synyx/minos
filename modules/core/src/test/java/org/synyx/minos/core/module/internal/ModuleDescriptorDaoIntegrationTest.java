package org.synyx.minos.core.module.internal;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.synyx.minos.core.Core;
import org.synyx.minos.test.AbstractDaoIntegrationTest;


/**
 * Integration test for {@link ModuleDescriptorDao}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleDescriptorDaoIntegrationTest extends
        AbstractDaoIntegrationTest {

    @Autowired
    private ModuleDescriptorDao dao;

    private ModuleDescriptor descriptor;


    @Before
    public void setUp() {

        descriptor = new ModuleDescriptor(Core.IDENTIFIER);
    }


    @Test
    public void storesInstallStateCorrectly() throws Exception {

        dao.save(descriptor);
    }


    @Test
    public void looksUpDescriptorByIdentifierCorrectly() throws Exception {

        dao.save(descriptor);

        assertThat(dao.findByIdentifier(Core.IDENTIFIER), is(descriptor));
    }
}
