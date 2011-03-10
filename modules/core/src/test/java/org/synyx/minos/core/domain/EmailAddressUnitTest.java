package org.synyx.minos.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link EmailAddress}.
 *
 * @author Stefan Kuhn - kuhn@synyx.de
 */
public class EmailAddressUnitTest {

    private EmailAddress emailAddress_one_invalid = null;
    private EmailAddress emailAddress_two_invalid = null;
    private EmailAddress emailAddress_one_valid = null;
    private EmailAddress emailAddress_two_valid = null;
    private EmailAddress emailAddress_three_valid = null;

    /**
     * Sets up some valid {@link EmailAddress}es.
     */
    @Before
    public void setUp() {

        this.emailAddress_one_valid = new EmailAddress("test@synyx.de");
        this.emailAddress_two_valid = new EmailAddress("TeSt1234@synyx.de");
        this.emailAddress_three_valid = new EmailAddress(" testMore@synyx.de");
    }


    /**
     * Asserts that an Email Address can not be constructed from the String "63%264@synyx.de" without
     * throwing an IllegalArgumentException. The member stays null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAssertEmailAddressOneInvalid() {

        this.emailAddress_one_invalid = new EmailAddress("63%264@synyx.de");

        assertTrue(this.emailAddress_one_invalid == null);
    }


    /**
     * Asserts that an Email Address can not be constructed from the String "test@synyx" without
     * throwing an IllegalArgumentException. The member stays null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAssertEmailAddressTwoInvalid() {

        this.emailAddress_two_invalid = new EmailAddress("test@synyx");

        assertEquals(this.emailAddress_two_invalid, null);
    }


    /**
     * Asserts that an Email Addresses can be constructed from the Strings "test@synyx.de", "TeSt1234@synyx.de"
     * and " testMore@synyx.de" without throwing an IllegalArgumentException. The members also match the RegEx-
     * Pattern from the domain class and the values are set correctly.
     */
    @Test
    public void testAssertEmailAddressesValid() {

        final String DOMAIN_REGEX = EmailAddress.EMAIL_REGEX;

        assertTrue(emailAddress_one_valid.getValue().matches(DOMAIN_REGEX));
        assertTrue(emailAddress_two_valid.getValue().matches(DOMAIN_REGEX));
        assertTrue(emailAddress_three_valid.getValue().matches(DOMAIN_REGEX));

        assertEquals(emailAddress_one_valid.getValue(), "test@synyx.de");
        assertEquals(emailAddress_two_valid.getValue(), "TeSt1234@synyx.de");
        assertEquals(emailAddress_three_valid.getValue(), "testMore@synyx.de");

        assertEquals(emailAddress_one_valid.toString(), "test@synyx.de");
        assertEquals(emailAddress_two_valid.toString(), "TeSt1234@synyx.de");
        assertEquals(emailAddress_three_valid.toString(), "testMore@synyx.de");
    }
}
