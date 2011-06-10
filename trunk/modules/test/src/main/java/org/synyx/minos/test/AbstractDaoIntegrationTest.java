package org.synyx.minos.test;

import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;


/**
 * Abstract base class for DAO tests. Configures an application context from all files named {@code dao-context.xml} in
 * the classpath.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/minos/**/dao-context.xml")
@Transactional
public abstract class AbstractDaoIntegrationTest extends DatabasePopulationAwareTest {
}
