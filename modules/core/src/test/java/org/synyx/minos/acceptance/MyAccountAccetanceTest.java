package org.synyx.minos.acceptance;

import org.junit.Test;
import org.synyx.minos.umt.web.MyAccountController;


/**
 * Acceptance test for {@link MyAccountController}.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class MyAccountAccetanceTest extends AbstractCoreAcceptanceTest {

    @Test
    public void testSaveMyAccount() throws Exception {

        clickLinkWithExactText(translateMessage("umt.myaccount.title"));

        setTextField("firstname", "foobar");
        submit();

        assertTextFieldEquals("firstname", "foobar");
    }

}
