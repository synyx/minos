package org.synyx.minos.core.configuration;

import static org.synyx.minos.TestConstants.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.synyx.minos.core.configuration.ConfyxConfigurationService;

import com.synyx.confyx.Confyx;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-context.xml")
public class ConfyxConfigurationServiceIntegrationTest {

    private static final String FAMILY_ID = "foo.bar";

    @Autowired
    private ConfyxConfigurationService configService;

    @Autowired
    private Confyx confyx;


    @Test
    public void assertCorrectInitialization() {

        Assert.assertNotNull(configService);
        Assert.assertNotNull(confyx);

        String configValue = configService.getConfigValue(FAMILY_ID, USER);

        Assert.assertEquals("value", configValue);
    }
}
