package org.synyx.minos.skillz.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;

import org.synyx.minos.acceptance.AbstractCrudFormAcceptanceTest;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class TemplatesAcceptanceTest extends AbstractCrudFormAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("skillz.menu.title"));
        clickLinkWithExactText(translateMessage("skillz.menu.skillz.title"));
    }


    @Override
    protected void fillCreateFormFields(WebTester tester, String identifier) {

        tester.setTextField("name", identifier);
        tester.checkCheckbox("categories", "1");
    }


    @Override
    protected void fillEditFormFields(WebTester tester) {

        tester.checkCheckbox("categories", "2");
    }


    @Override
    protected String getFormIdentifier() {

        return "templates";
    }


    @Override
    protected String getFormTableId() {

        return "template";
    }


    @Override
    protected String getMessageSourcePrefix() {

        return "skillz.template";
    }

}
