package org.synyx.minos.skillz.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;

import org.synyx.minos.acceptance.AbstractCrudFormAcceptanceTest;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class CategoriesAcceptanceTest extends AbstractCrudFormAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("skillz.menu.title"));
        clickLinkWithExactText(translateMessage("skillz.menu.skillz.title"));
    }


    public void testAddSkill() throws Exception {

        createCategoryAndAddSkill("Spring");

        assertSuccessMessage("skillz.skill.save.success");
        assertTextInTable("skills", "Spring");
    }


    public void testDeleteSkill() throws Exception {

        createCategoryAndAddSkill("Spring MVC");

        clickElementByXPath("//form/input[@alt='" + translateMessage("core.ui.delete") + "']");

        assertSuccessMessage("skillz.skill.delete.success");
        assertTextNotInTable("skills", "Spring MVC");
    }


    public void testMoveSkill() throws Exception {

        String destination = createEntity();
        createCategoryAndAddSkill("Spring Security");

        selectOption("category", destination);
        clickButtonWithText(translateMessage("skillz.skill.move"));

        clickLinkWithExactText(translateMessage("skillz.menu.skillz.title"));
        clickLinkWithExactText(destination);
        assertTextInTable("skills", "Spring Security");
    }


    private String createCategoryAndAddSkill(String skillName) {

        String identifier = createEntity();
        clickEditLink(identifier);

        setWorkingForm("addSkill");
        setTextField("name", skillName);
        submit();

        return identifier;
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

        return "categories";
    }


    @Override
    protected String getFormTableId() {

        return "category";
    }


    @Override
    protected String getMessageSourcePrefix() {

        return "skillz.category";
    }

}
