package org.synyx.minos.core.domain;

import org.apache.commons.lang.StringUtils;

import org.synyx.hades.domain.auditing.AbstractAuditable;

import org.synyx.minos.util.Assert;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;


/**
 * Domain class for a user.
 *
 * @author  Oliver Gierke
 */
@Entity
@NamedQuery(name = "User.findByUsername", query = "from User u where u.username = ?")
public class User extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -5496786625943806937L;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private Password password;

    // unique-ness is now ensured on demand by validator
    @Column(nullable = false, unique = false)
    private String emailAddress;

    private String firstname;

    private String lastname;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    private boolean active;

    protected User() {

        this.roles = new ArrayList<Role>();
        this.active = true;
    }


    /**
     * Creates a new {@link User} from the given attributes (non null ones).
     *
     * @param  username  an username
     * @param  emailAddress  the users email address
     */
    public User(String username, String emailAddress) {

        this(username, emailAddress, null);
    }


    /**
     * Creates a new {@link User} from the given attributes (non null ones).
     *
     * @param  username  an username
     * @param  emailAddress  the users email address
     * @param  password  the users password
     */
    public User(String username, String emailAddress, String password) {

        this();
        this.username = username;
        this.emailAddress = emailAddress;

        if (null != password) {
            this.password = new Password(password);
        }
    }

    /**
     * Assigns the role to the user. Prevents {@link Role}s to be assigned twice, so there won't be any duplicates.
     *
     * @param  role  a {@link Role}
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
     * @return  if the user has a password
     */
    public boolean hasPassword() {

        return password != null;
    }


    /**
     * Returns the full name of the user.
     *
     * @return  the full name of the user
     */
    public String getFullName() {

        String firstname = StringUtils.trimToEmpty(getFirstname());
        String lastname = StringUtils.trimToEmpty(getLastname());

        return String.format("%s %s", firstname, lastname);
    }


    @Override
    public String toString() {

        return super.toString() + " " + getUsername() + " - " + getFirstname() + " " + getLastname() + " - "
            + getEmailAddress();
    }


    public String getUsername() {

        return username;
    }


    public void setUsername(String argUsername) {

        username = argUsername;
    }


    public Password getPassword() {

        return password;
    }


    public void setPassword(Password argPassword) {

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
     * Replaces the roles set on the user object with those in the supplied list.
     *
     * @param  roles  the roles to set
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
     * @param  role  the role to check for
     *
     * @return  whether the {@link User} has the given {@link Role}
     */
    public boolean has(Role role) {

        return this.roles.contains(role);
    }


    /**
     * Returns whether the {@link User} has the given role. This is a convenience method, which allows to supply the
     * role as a string.
     *
     * @param  name  the role to check for
     *
     * @return  whether the {@link User} has the given role.
     */
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


    public boolean isActive() {

        return active;
    }


    public void setActive(boolean active) {

        this.active = active;
    }
}
