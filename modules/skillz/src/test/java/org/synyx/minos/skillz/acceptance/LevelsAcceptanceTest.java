package org.synyx.minos.skillz.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;

import org.synyx.minos.acceptance.AbstractCrudFormAcceptanceTest;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class LevelsAcceptanceTest extends AbstractCrudFormAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("skillz.menu.title"));
        clickLinkWithExactText(translateMessage("skillz.menu.skillz.title"));
    }


    @Override
    protected void fillCreateFormFields(WebTester tester, String identifier) {

        tester.setTextField("name", identifier);
    }


    @Override
    protected void fillEditFormFields(WebTester tester) {

        tester.setTextField("name", String.valueOf(System.currentTimeMillis()));
    }


    @Override
    protected String getFormIdentifier() {

        return "levels";
    }


    @Override
    protected String getFormTableId() {

        return "level";
    }


    @Override
    protected String getMessageSourcePrefix() {

        return "skillz.level";
    }

}
