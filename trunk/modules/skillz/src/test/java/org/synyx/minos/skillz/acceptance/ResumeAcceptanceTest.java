package org.synyx.minos.skillz.acceptance;

import java.io.File;

import org.synyx.minos.acceptance.AbstractCoreAcceptanceTest;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class ResumeAcceptanceTest extends AbstractCoreAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("skillz.menu.title"));
        clickLinkWithExactText(translateMessage("skillz.menu.resume.title"));
    }


    public void testSaveResume() throws Exception {

        setTextField("position", "salesman");
        clickButtonWithText(translateMessage("core.ui.save"));
        submit();

        assertSuccessMessage("skillz.resume.save.success");
        assertTextFieldEquals("position", "salesman");
    }


    public void testSavePhoto() throws Exception {

        uploadPhoto();

        assertSuccessMessage("skillz.resume.photo.save.success");
        assertElementPresentByXPath("//div[@id='photo']/table");
    }


    public void testDeletePhoto() throws Exception {

        uploadPhoto();

        clickElementByXPath("//div[@id='photo']//form/input[@alt='" + translateMessage("core.ui.delete") + "']");

        assertSuccessMessage("skillz.resume.photo.delete.success");
        assertElementNotPresentByXPath("//div[@id='photo']/table");
    }


    private void uploadPhoto() {

        setTextField("photoBinary", new File("../minos-core/src/test/resources/org/synyx/minos/util/logo.png")
                .getAbsolutePath());
        submit();
    }

}
