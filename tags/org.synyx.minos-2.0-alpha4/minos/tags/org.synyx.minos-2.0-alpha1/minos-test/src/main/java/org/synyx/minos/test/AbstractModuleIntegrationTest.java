package org.synyx.minos.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Abstract base class for integration tests using entire Minos.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/minos/**/module-context.xml")
public abstract class AbstractModuleIntegrationTest extends
        DatabasePopulationAwareTest {

}
