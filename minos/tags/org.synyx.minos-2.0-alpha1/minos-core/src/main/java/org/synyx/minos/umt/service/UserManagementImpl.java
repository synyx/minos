package org.synyx.minos.umt.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.core.authentication.AuthenticationService;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.RoleDao;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Implementation of user management module. Uses an implementation of {@code
 * PasswordCreator} to generate a random password, that is used as initial
 * password for a new user after successful registration.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class UserManagementImpl implements UserManagement {

    private UserDao userDao;
    private RoleDao roleDao;

    private AuthenticationService authenticationService;
    private PasswordCreator passwordCreator;


    /**
     * Setter to inject <code>UserDao</code>.
     * 
     * @param userDao the userDao to set
     */
    @Required
    public void setUserDao(UserDao userDao) {

        this.userDao = userDao;
    }


    /**
     * Setter to inject <code>RoleDao</code>.
     * 
     * @param roleDao the roleDao to set
     */
    @Required
    public void setRoleDao(RoleDao roleDao) {

        this.roleDao = roleDao;
    }


    /**
     * Setter to inject an implementation of {@code PasswordCreator}. The
     * instance is used to create passwords for new users.
     * 
     * @param passwordCreator
     */
    @Required
    public void setPasswordCreator(PasswordCreator passwordCreator) {

        this.passwordCreator = passwordCreator;
    }


    /**
     * Setter to inject an {@link AuthenticationService} to encrypt passwords.
     * 
     * @param authenticationService the authenticationService to set
     */
    public void setAuthenticationService(
            AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.umt.service.UserManagement#delete(java.lang.String)
     */
    public void deleteUser(Long id) throws UserNotFoundException {

        Assert.notNull(id);

        User user = getUser(id);

        if (null == user) {
            throw new UserNotFoundException(id);
        }

        if (authenticationService.isCurrentUser(user)) {
            throw new IllegalArgumentException(
                    "Illegal attempt to delete the currently logged in user!");
        }

        userDao.delete(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.umt.service.UserManagement#save(com.synyx.minos.umt.domain
     * .User)
     */
    public void save(User user) {

        Assert.notNull(user);

        if (!user.isNew() && !exists(user.getId())) {
            throw new IllegalArgumentException("User with id " + user.getId()
                    + " does not exist!");
        }

        if (user.isNew() && !user.hasPassword()) {
            user.setPassword(passwordCreator.generatePassword());
        }

        // Trigger encryption
        eventuallyEncryptPassword(user);

        userDao.save(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.umt.service.UserManagement#save(com.synyx.minos.umt.domain
     * .Role)
     */
    public void save(Role role) {

        Assert.notNull(role);

        roleDao.save(role);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getUsers()
     */
    @Transactional(readOnly = true)
    public List<User> getUsers() {

        return userDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.umt.service.UserManagement#getUsers(org.synyx.hades.domain
     * .Pageable, org.synyx.hades.domain.support.Sort)
     */
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {

        return userDao.readAll(pageable);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getUser(java.lang.Long)
     */
    @Transactional(readOnly = true)
    public User getUser(Long id) {

        Assert.notNull(id);

        try {
            return userDao.readByPrimaryKey(id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getUser(java.lang.String)
     */
    @Transactional(readOnly = true)
    public User getUser(String username) {

        Assert.notNull(username);

        try {
            return userDao.findByUsername(username);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    /**
     * Encrypts the password of the given user if it matches the following
     * conditions:
     * <ul>
     * <li>An encryption provider is configured</li>
     * <li>The user is new, meaning the password has never been encrypted yet</li>
     * <li>The user's password is not the one of the old user</li>
     * </ul>
     * 
     * @param user
     * @return
     */
    private void eventuallyEncryptPassword(User user) {

        if (null == authenticationService) {
            return;
        }

        // Encrypt new users password and set it
        if (user.isNew()) {

            userDao.save(user);
            user.setPassword(authenticationService
                    .getEncryptedPasswordFor(user));

            return;
        }

        User oldUser = userDao.readByPrimaryKey(user.getId());

        if (null == oldUser) {
            return;
        }

        // Case 1: Empty password
        // Use old password
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(oldUser.getPassword());
            return;
        }

        // Case 2: Password available
        // Use new password if it does not match the old one
        if (!oldUser.getPassword().equals(user.getPassword())) {
            user.setPassword(authenticationService
                    .getEncryptedPasswordFor(user));
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#exists(java.lang.Long)
     */
    @Transactional(readOnly = true)
    public boolean exists(Long id) {

        if (null == id) {
            return false;
        }

        return null != userDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getRoles()
     */
    @Transactional(readOnly = true)
    public List<Role> getRoles() {

        return roleDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getRole(java.lang.Long)
     */
    @Transactional(readOnly = true)
    public Role getRole(Long id) {

        return roleDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.umt.service.UserManagement#getUserByEmail(java.lang.String
     * )
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String argEmail) {

        return userDao.findByEmailAddress(argEmail);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.synyx.minos.umt.service.UserManagement#deleteRole(org.synyx.minos
     * .core.domain.Role)
     */
    @Override
    public void deleteRole(Role role) {

        roleDao.delete(role);
    }
}
