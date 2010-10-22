package org.synyx.minos.acceptance;

import java.util.UUID;

import net.sourceforge.jwebunit.junit.WebTester;


/**
 * An abstract class for acceptance testing Minos CRUD forms. Tests if the table is shown and if an entity can be
 * successfully create, edited and deleted. To exclude a test override it and leave it empty.
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public abstract class AbstractCrudFormAcceptanceTest extends AbstractCoreAcceptanceTest {

    /**
     * @return The message source prefix, normally made up of the module name and the form name, separated by a dot e.g.
     *         "umt.user"
     */
    protected abstract String getMessageSourcePrefix();


    /**
     * @return The unique URL part at the end of the URL, which is used in all URLs of the form, e.g. "users"
     */
    protected abstract String getFormIdentifier();


    /**
     * @return The CSS id of the form table
     */
    protected abstract String getFormTableId();


    /**
     * Fills all form fields out, which are necessary to create a valid entity.
     * 
     * @param tester Instance of {@link WebTester} for filling out form fields
     * @param identifier Unique identifier for the entity
     */
    protected abstract void fillCreateFormFields(WebTester tester, String identifier);


    /**
     * Makes a valid change to the form.
     * 
     * @param tester Instance of {@link WebTester} for filling out form fields
     */
    protected abstract void fillEditFormFields(WebTester tester);


    /**
     * Tests if the table is shown by looking it up with the corresponding CSS id.
     */
    public void testShowTable() throws Exception {

        assertTablePresent(getFormTableId());
    }


    /**
     * Tests if an entity can be created successfully.
     */
    public void testCreateEntity() throws Exception {

        createEntity(createUUIDString());

        assertCrudSuccessMessage(".save.success");
    }


    /**
     * Tests if an entity can be edited successfully.
     */
    public void testEditEntity() throws Exception {

        String editIdentifier = createUUIDString();
        createEntity(editIdentifier);

        clickEditLink(editIdentifier);
        fillEditFormFields(getTester());
        submit();

        assertCrudSuccessMessage(".save.success");
    }


    /**
     * Tests if an entity can be deleted successfully.
     */
    public void testDeleteEntity() throws Exception {

        String deleteIdentifier = createUUIDString();
        createEntity(deleteIdentifier);

        setHiddenField("_method", "delete");
        clickDeleteButton(deleteIdentifier);

        assertCrudSuccessMessage(".delete.success");
    }


    protected void clickEditLink(String username) {

        clickElementByXPath("//a[@href='" + getPathFromTable(username) + "']");
    }


    protected void clickDeleteButton(String name) {

        clickElementByXPath("//form[@action='" + getPathFromTable(name) + "']/input[@alt='"
                + translateMessage("core.ui.delete") + "']");
    }


    private String createUUIDString() {

        return UUID.randomUUID().toString().toUpperCase();
    }


    protected String getPathFromTable(String name) {

        return getElementAttributeByXPath("//td/a[text()='" + name + "']", "href");
    }


    protected String createEntity() {

        String identifier = createUUIDString();
        createEntity(identifier);
        return identifier;
    }


    private void createEntity(String identifier) {

        clickLinkByTitle(getMessageSourcePrefix() + ".new");

        fillCreateFormFields(getTester(), identifier);

        submit();
    }


    /**
     * Asserts if a success message is shown and the message source matches.
     * 
     * @see AbstractCoreAcceptanceTest#assertSuccessMessage
     */
    private void assertCrudSuccessMessage(String messageSourceSuffix) {

        assertSuccessMessage(getMessageSourcePrefix() + messageSourceSuffix);
    }

}
