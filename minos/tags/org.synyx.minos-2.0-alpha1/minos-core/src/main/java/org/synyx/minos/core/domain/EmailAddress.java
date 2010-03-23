package org.synyx.minos.core.domain;

import javax.persistence.Embeddable;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@Embeddable
public class EmailAddress {

    // Regular expression to test for valid email
    private static final String EMAIL_REGEX =
            "[-_.(\\w)]+@((([(\\w)]|[(\\w)][(\\w)-]*"
                    + "[(\\w)])\\.)+(([a-z]{2,6})|(([0-9][0-9]?|[0-1][0-9]"
                    + "[0-9]|[2][0-4][0-9]|[2][5][0-5])\\.){3}([0-9][0-9]?|[0-1]"
                    + "[0-9][0-9]|[2][0-4][0-9]|[2][5][0-5])))";

    private String emailAddress;


    public EmailAddress(String emailAddress) {

        if (!emailAddress.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email address!");
        }

        this.emailAddress = emailAddress;
    }


    public String asString() {

        return emailAddress;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return asString();
    }
}
