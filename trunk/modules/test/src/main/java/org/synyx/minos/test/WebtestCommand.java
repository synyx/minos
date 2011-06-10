package org.synyx.minos.test;

import net.sourceforge.jwebunit.junit.WebTester;


/**
 * Interface for JWebUnit commands.
 *
 * @author Markus Knittig - knittig@synyx.de
 */
public interface WebtestCommand {

    /**
     * Executes the command with the given {@link WebTester} instance.
     */
    void execute(WebTester tester);
}
