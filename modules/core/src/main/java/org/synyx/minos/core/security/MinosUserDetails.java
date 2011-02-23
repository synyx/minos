package org.synyx.minos.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;

import java.util.ArrayList;
import java.util.List;


/**
 * {@link UserDetails} implementation based on a {@link User}. Maps the {@link User}'s roles to its
 * {@link GrantedAuthority}s.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class MinosUserDetails implements UserDetails {

    private static final long serialVersionUID = -8147020879400944985L;
    private User user;

    /**
     * Creates a new {@link MinosUserDetails} instance.
     *
     * @param  user  the user this details should belong to
     */
    public MinosUserDetails(User user) {

        this.user = user;
    }

    public Long getId() {

        return user.getId();
    }


    @Override
    public List<GrantedAuthority> getAuthorities() {

        // Extract authorities
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : user.getRoles()) {
            authorities.add(new GrantedAuthorityImpl(role.toString()));

            for (String permission : role.getPermissions()) {
                authorities.add(new GrantedAuthorityImpl(permission));
            }
        }

        return authorities;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetails#getPassword()
     */
    public String getPassword() {

        return user.getPassword() == null ? null : user.getPassword().toString();
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetails#getUsername()
     */
    public String getUsername() {

        return user.getUsername();
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired ()
     */
    public boolean isAccountNonExpired() {

        return true;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
     */
    public boolean isAccountNonLocked() {

        return true;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired ()
     */
    public boolean isCredentialsNonExpired() {

        return true;
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.userdetails.UserDetails#isEnabled()
     */
    public boolean isEnabled() {

        return user.isActive();
    }


    /**
     * Returns the underlying Minos {@link User}.
     *
     * @return  the user
     */
    public User getUser() {

        return user;
    }
}
