package org.synyx.minos.acceptance;

import net.sourceforge.jwebunit.junit.WebTester;

import org.synyx.minos.test.WebtestCommand;


/**
 * Performances a login with the given user data (standard admin user by
 * default).
 * 
 * @author Markus Knittig - knittig@synyx.de
 */
public class LoginCommand implements WebtestCommand {

    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin";

    private String username = DEFAULT_USERNAME;
    private String password = DEFAULT_PASSWORD;


    /**
     * Goes to "/" and performance a login with the given username and password.
     */
    public void execute(WebTester tester) {

        tester.beginAt("/");
        tester.setTextField("j_username", username);
        tester.setTextField("j_password", password);
        tester.submit();
    }


    /**
     * Sets the username to login.
     * 
     * @param username The username, reset to default if set to
     *            <code>null</code>
     */
    public LoginCommand withUsername(String username) {

        this.username = (username == null ? DEFAULT_USERNAME : username);
        return this;
    }


    /**
     * Sets the password to login.
     * 
     * @param password The password, reset to default if set to
     *            <code>null</code>
     */
    public LoginCommand withPassword(String password) {

        this.password = (password == null ? DEFAULT_PASSWORD : password);
        return this;
    }

}
