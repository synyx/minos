package org.synyx.minos.skillz.acceptance;

import org.synyx.minos.acceptance.AbstractCoreAcceptanceTest;


/**
 * @author Markus Knittig - knittig@synyx.de
 */
public class MatrixAcceptanceTest extends AbstractCoreAcceptanceTest {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        clickLinkWithExactText(translateMessage("skillz.menu.title"));
        clickLinkWithExactText(translateMessage("skillz.menu.resume.title"));
    }


    public void testEditMatrix() throws Exception {

        clickLinkWithExactText(translateMessage("skillz.skillMatrix.edit"));
        clickRadioOption("entries[1].level", "1");
        submit();

        assertElementPresentByXPath("//table[@id='skillzMatrix']//td[..//text()='SOAP'][2 and text()='X']");
    }

}
