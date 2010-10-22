package org.synyx.minos.skillz.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;

import org.synyx.minos.acceptance.AbstractCrudFormAcceptanceTest;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class ReferencesAcceptanceTest extends AbstractCrudFormAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("skillz.menu.title"));
        clickLinkWithExactText(translateMessage("skillz.menu.resume.title"));
    }


    @Override
    protected void fillCreateFormFields(WebTester tester, String identifier) {

        tester.setTextField("end", "01.01.2010");
        tester.selectOption("project", "Project1");
        tester.checkCheckbox("responsibilities", "1");
        tester.setTextField("additionalDescription", identifier);
    }


    @Override
    protected void fillEditFormFields(WebTester tester) {

        tester.checkCheckbox("responsibilities", "2");
    }


    @Override
    protected String getPathFromTable(String name) {

        return getElementAttributeByXPath(
                "//table[@id='reference']//tr[td/@class='projectDescription' and td//p/text()='" + name + "']//a",
                "href");
    }


    @Override
    protected String getFormIdentifier() {

        return "resume/references";
    }


    @Override
    protected String getFormTableId() {

        return "reference";
    }


    @Override
    protected String getMessageSourcePrefix() {

        return "skillz.reference";
    }

}
