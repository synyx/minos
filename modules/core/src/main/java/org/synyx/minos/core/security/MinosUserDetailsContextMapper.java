package org.synyx.minos.core.security;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import org.springframework.transaction.annotation.Transactional;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class MinosUserDetailsContextMapper implements UserDetailsContextMapper {

    private final UserManagement userManagement;

    private String defaultRoles = "";

    public MinosUserDetailsContextMapper(UserManagement userManagement) {

        this.userManagement = userManagement;
    }

    @Override
    @Transactional
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
        Collection<GrantedAuthority> authority) {

        User user = userManagement.getUser(username);

        if (null == user) {
            user = createUser(ctx, username);
        }

        return new MinosUserDetails(user);
    }


    private User createUser(DirContextOperations ctx, String username) {

        String mail = (String) ctx.getObjectAttribute("mail");

        User user = new User(username, mail);
        user.setRoles(getDefaultRoleList());

        userManagement.save(user);

        return user;
    }


    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

        throw new UnsupportedOperationException(
            "MinosUserDetailsContextMapper only supports reading from a context. Please"
            + "use a subclass if mapUserToContext() is required.");
    }


    public List<Role> getDefaultRoleList() {

        List<String> roles = Arrays.asList(defaultRoles.split(","));
        List<Role> defaultRoleList = new ArrayList<Role>();

        for (String role : roles) {
            Role defaultRole = userManagement.getRole(role);

            defaultRoleList.add(defaultRole);
        }

        return defaultRoleList;
    }


    public void setDefaultRoles(String defaultRoles) {

        Assert.hasText(defaultRoles, "A comma-separated list of default roles has to be provided");

        this.defaultRoles = defaultRoles;
    }
}
