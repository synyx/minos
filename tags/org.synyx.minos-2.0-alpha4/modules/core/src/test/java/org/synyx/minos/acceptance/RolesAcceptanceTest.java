package org.synyx.minos.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class RolesAcceptanceTest extends AbstractCrudFormAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("umt.menu.title"));
        clickLinkWithExactText(translateMessage("umt.menu.roles.title"));
    }


    @Override
    protected void fillCreateFormFields(WebTester tester, String identifier) {

        tester.setTextField("name", identifier);
        tester.checkCheckbox("permissions", "UMT_ADMIN");
    }


    @Override
    protected void fillEditFormFields(WebTester tester) {

        tester.setTextField("name", "foobar");
    }


    @Override
    protected String getMessageSourcePrefix() {

        return "umt.role";
    }


    @Override
    protected String getFormIdentifier() {

        return "roles";
    }


    @Override
    protected String getFormTableId() {

        return "role";
    }

}
