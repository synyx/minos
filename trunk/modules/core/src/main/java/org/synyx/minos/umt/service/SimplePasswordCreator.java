package org.synyx.minos.umt.service;

import org.synyx.minos.core.domain.Password;

import java.util.Random;


/**
 * Its length will be derived from the configured password length. The alphabet the password is build from and the
 * password length can be configured.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SimplePasswordCreator implements PasswordCreator {

    protected static final String DEFAULT_PASSWORD_ALPHABET =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    protected static final int DEFAULT_PASSWORD_LENGTH = 8;

    private int passwordLength = DEFAULT_PASSWORD_LENGTH;
    private String passwordAlphabet = DEFAULT_PASSWORD_ALPHABET;

    /**
     * Sets standard password length. Defaults to {@value #DEFAULT_PASSWORD_LENGTH}.
     *
     * @param passwordLength the password length to set
     */
    public void setPasswordLength(int passwordLength) {

        this.passwordLength = passwordLength;
    }


    /**
     * Configures the alphabet that is used to create random new passwords. Defaults to
     * {@value #DEFAULT_PASSWORD_ALPHABET}.
     *
     * @param passwordAlphabet the passwordAlphabet to set
     */
    public void setPasswordAlphabet(String passwordAlphabet) {

        this.passwordAlphabet = passwordAlphabet;
    }


    public Password generatePassword() {

        StringBuffer buffer = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < passwordLength; i++) {
            buffer.append(passwordAlphabet.charAt(random.nextInt(passwordAlphabet.length())));
        }

        return new Password(buffer.toString());
    }
}
