package org.synyx.minos.core.domain;

import org.synyx.minos.util.Assert;

import javax.persistence.Embeddable;


/**
 * Value objects to represent email addresses.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
@Embeddable
@ValueObject
public class EmailAddress extends AbstractNamedEntity {

    /** A regular expression to test for valid email addresses. */
    public static final String EMAIL_REGEX = "[-_.(\\w)]+@((([(\\w)]|[(\\w)][(\\w)-]*"
        + "[(\\w)])\\.)+(([a-zA-Z]{2,6})|(([0-9][0-9]?|[0-1][0-9]"
        + "[0-9]|[2][0-4][0-9]|[2][5][0-5])\\.){3}([0-9][0-9]?|[0-1]"
        + "[0-9][0-9]|[2][0-4][0-9]|[2][5][0-5])))";

    private String emailAddress;

    protected EmailAddress() {

        // Required by ORM
        super();
    }


    /**
     * Construct a new {@link EmailAddress} from the given string. The address is stripped of leading and trailing
     * whitespace and also checked against {@link EmailAddress#EMAIL_REGEX} for validity. If invalid, an
     * {@link IllegalArgumentException} is thrown.
     *
     * @param  emailAddress  an email address
     */
    public EmailAddress(String emailAddress) {

        Assert.notBlank(emailAddress);

        this.emailAddress = emailAddress.trim();

        if (!this.emailAddress.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email address!");
        }
    }

    @Override
    protected String getValue() {

        return emailAddress;
    }


    @Override
    public String toString() {

        return emailAddress;
    }
}
