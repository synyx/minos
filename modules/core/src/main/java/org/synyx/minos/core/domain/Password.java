package org.synyx.minos.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.synyx.minos.util.Assert;


/**
 * Value object to abstract passwords.
 * 
 * @author Oliver Gierke
 */
@Embeddable
@ValueObject
public class Password {

    private String password;

    @Transient
    private boolean encrypted = true;


    /**
     * Empty constructor for ORM.
     */
    protected Password() {

    }


    /**
     * Creates a new unencrypted password.
     * 
     * @param password
     */
    public Password(String password) {

        this(password, false);
    }


    /**
     * Creates a new password either encrypted or unencrypted.
     * 
     * @param password
     * @param encrypted
     */
    public Password(String password, boolean encrypted) {

        Assert.notNull(password);

        this.password = password;
        this.encrypted = encrypted;
    }


    /**
     * Returns whether the password is encrpyted.
     * 
     * @return the encrypted
     */
    public boolean isEncrypted() {

        return encrypted;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return password;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }

        Password that = (Password) obj;

        return this.password.equals(that.password) && this.encrypted == that.encrypted;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = 17;
        result += 31 * password.hashCode();
        result += encrypted ? 0 : 1;

        return result;
    }
}
