package org.synyx.minos.core.module;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.synyx.minos.core.module.helper.SampleCalendarModuleDecorator;
import org.synyx.minos.core.module.helper.SamplePMTModuleDecorator;



/**
 * Integration test for <code>ModulePostProcessor</code>.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-context.xml")
public class ModulePostProcessorIntegrationTest {

    @Autowired
    private SampleCalendarModuleDecorator calendarDecorator;

    @Autowired(required = false)
    private SamplePMTModuleDecorator pmtDecorator;


    /**
     * Tests, that the module decorator has not removed the calendar decorator.
     */
    @Test
    public void assertCalendarDecoratorAvailable() {

        Assert.assertNotNull(calendarDecorator);
    }
    
    
    /**
     * Tests, that the module decorator has removed the PMT decorator.
     */
    @Test
    public void assertPmtDecoratorNotAvailable() {

        Assert.assertNull(pmtDecorator);
    }
}
