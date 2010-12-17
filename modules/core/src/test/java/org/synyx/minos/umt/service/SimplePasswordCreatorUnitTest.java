package org.synyx.minos.umt.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.synyx.minos.core.domain.Password;


/**
 * Unit test fpr {@code SimplePasswordCreator}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SimplePasswordCreatorUnitTest {

    private SimplePasswordCreator passwordCreator;


    /**
     * Creates a new {@code SimplePasswordCreator}.
     */
    @Before
    public void setUp() {

        passwordCreator = new SimplePasswordCreator();
    }


    /**
     * Asserts that the password creator does use the default password length if nothing else was configured or respects
     * the configured password length creating new passwords.
     * 
     * @throws Exception
     */
    @Test
    public void respectsConfiguredPasswordLength() throws Exception {

        Password password = passwordCreator.generatePassword();

        Assert.assertEquals(SimplePasswordCreator.DEFAULT_PASSWORD_LENGTH, password.toString().length());

        passwordCreator.setPasswordLength(25);
        password = passwordCreator.generatePassword();

        Assert.assertEquals(25, password.toString().length());
    }


    @Test
    public void respectsConfiguredAlphabet() throws Exception {

        Password password = passwordCreator.generatePassword();

        for (char character : password.toString().toCharArray()) {

            Assert.assertTrue(SimplePasswordCreator.DEFAULT_PASSWORD_ALPHABET.contains(String.valueOf(character)));
        }
    }
}
