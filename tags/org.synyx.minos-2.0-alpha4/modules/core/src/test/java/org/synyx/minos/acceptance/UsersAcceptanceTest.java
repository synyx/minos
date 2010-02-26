package org.synyx.minos.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class UsersAcceptanceTest extends AbstractCrudFormAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("umt.menu.title"));
    }


    @Override
    protected void fillCreateFormFields(WebTester tester, String identifier) {

        tester.setTextField("username", identifier);
        tester.setTextField("firstname", "Joe");
        tester.setTextField("lastname", "Doe");
        tester.setTextField("emailAddress", identifier + "@example.com");
        tester.checkCheckbox("roles", "2");
        tester.setTextField("newPassword", "test123");
        tester.setTextField("repeatedPassword", "test123");
    }


    @Override
    protected void fillEditFormFields(WebTester tester) {

        tester.setTextField("firstname", "Max");
    }


    @Override
    protected String getMessageSourcePrefix() {

        return "umt.user";
    }


    @Override
    protected String getFormIdentifier() {

        return "users";
    }


    @Override
    protected String getFormTableId() {

        return "user";
    }

}
