package org.synyx.minos.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.StringUtils;
import org.synyx.hades.domain.auditing.AbstractAuditable;
import org.synyx.minos.util.Assert;


/**
 * Domain class for a user.
 * 
 * @author Oliver Gierke
 */
@Entity
@NamedQuery(name = "User.findByUsername", query = "from User u where u.username = ?")
public class User extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -5496786625943806937L;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    private String firstname;

    private String lastname;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    private boolean active;


    /**
     * Constructor of <code>User</code>.
     */
    protected User() {

        this.roles = new ArrayList<Role>();
        this.active = true;
    }


    /**
     * Creates a new {@link User} from the given attributes (non null ones).
     * 
     * @param username
     * @param emailAddress
     * @param password
     */
    public User(String username, String emailAddress, String password) {

        this();
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }


    /**
     * Assigns the role to the user. Prevents {@link Role}s to be assigned
     * twice, so there won't be any duplicates.
     * 
     * @param role
     */
    public void addRole(Role role) {

        Assert.notNull(role);

        if (!roles.contains(role)) {
            this.roles.add(role);
        }
    }


    /**
     * Returns if the user has a password.
     * 
     * @return if the user has a password
     */
    public boolean hasPassword() {

        return !StringUtils.isBlank(password);
    }


    /**
     * Returns the full name of the user.
     * 
     * @return
     */
    public String getFullName() {

        String firstname = StringUtils.trimToEmpty(getFirstname());
        String lastname = StringUtils.trimToEmpty(getLastname());

        return String.format("%s %s", firstname, lastname);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.domain.AbstractEntity#toString()
     */
    @Override
    public String toString() {

        return super.toString() + " " + getUsername() + " - " + getFirstname()
                + " " + getLastname() + " - " + getEmailAddress();
    }


    public String getUsername() {

        return username;
    }


    public void setUsername(String argUsername) {

        username = argUsername;
    }


    public String getPassword() {

        return password;
    }


    public void setPassword(String argPassword) {

        password = argPassword;
    }


    public String getEmailAddress() {

        return emailAddress;
    }


    public void setEmailAddress(String argEmailAddress) {

        emailAddress = argEmailAddress;
    }


    public String getFirstname() {

        return firstname;
    }


    public void setFirstname(String argFirstname) {

        firstname = argFirstname;
    }


    public String getLastname() {

        return lastname;
    }


    public void setLastname(String argLastname) {

        lastname = argLastname;
    }


    public List<Role> getRoles() {

        return roles;
    }


    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles) {

        this.roles = new ArrayList<Role>();

        if (null == roles) {
            return;
        }

        for (Role role : roles) {

            if (null != role && !this.roles.contains(role)) {
                this.roles.add(role);
            }
        }
    }


    /**
     * Returns whether the {@link User} has the given {@link Role}.
     * 
     * @param role
     * @return
     */
    public boolean has(Role role) {

        return this.roles.contains(role);
    }


    public boolean hasRole(String name) {

        if (StringUtils.isBlank(name)) {
            return false;
        }

        for (Role role : roles) {
            if (role.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }


    /**
     * @return the active
     */
    public boolean isActive() {

        return active;
    }


    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {

        this.active = active;
    }
}
