package org.synyx.minos.acceptance;

import org.synyx.minos.test.AbstractAcceptanceTest;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class AbstractCoreAcceptanceTest extends AbstractAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        new LoginCommand().execute(getTester());
    }


    /**
     * Asserts if a success message is shown and the message source matches.
     * 
     * @see AbstractAcceptanceTest#assertMessageSource
     */
    protected void assertSuccessMessage(String code) {

        String message =
                getElementTextByXPath("//ul[contains(concat(' ', normalize-space(@class), ' '), 'successMessage')]/li");
        assertMessageSource(code, message);
    }

}
