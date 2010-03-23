package org.synyx.minos.core.domain;

import javax.persistence.Embeddable;

import org.synyx.minos.util.Assert;


/**
 * Value objects to represent email addresses.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Embeddable
@ValueObject
public class EmailAddress extends AbstractNamedEntity {

    // Regular expression to test for valid email
    public static final String EMAIL_REGEX =
            "[-_.(\\w)]+@((([(\\w)]|[(\\w)][(\\w)-]*"
                    + "[(\\w)])\\.)+(([a-z]{2,6})|(([0-9][0-9]?|[0-1][0-9]"
                    + "[0-9]|[2][0-4][0-9]|[2][5][0-5])\\.){3}([0-9][0-9]?|[0-1]"
                    + "[0-9][0-9]|[2][0-4][0-9]|[2][5][0-5])))";

    private String emailAddress;


    protected EmailAddress() {

        // Required by ORM
        super();
    }


    public EmailAddress(String emailAddress) {

        Assert.notBlank(emailAddress);

        this.emailAddress = emailAddress.trim();

        if (!this.emailAddress.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email address!");
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.domain.AbstractNamedEntity#getValue()
     */
    @Override
    protected String getValue() {

        return emailAddress;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return emailAddress;
    }
}
