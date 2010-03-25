package org.synyx.minos.skillz.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;

import org.synyx.minos.acceptance.AbstractCrudFormAcceptanceTest;


public class ProjectsAcceptanceTest extends AbstractCrudFormAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("skillz.menu.title"));
        clickLinkWithExactText(translateMessage("skillz.menu.skillz.title"));
    }


    @Override
    protected void fillCreateFormFields(WebTester tester, String identifier) {

        tester.setTextField("name", identifier);
        tester.setTextField("description", "foo");
    }


    @Override
    protected void fillEditFormFields(WebTester tester) {

        tester.setTextField("description", "bar");
    }


    @Override
    protected String getFormIdentifier() {

        return "projects";
    }


    @Override
    protected String getFormTableId() {

        return "project";
    }


    @Override
    protected String getMessageSourcePrefix() {

        return "skillz.project";
    }

}
