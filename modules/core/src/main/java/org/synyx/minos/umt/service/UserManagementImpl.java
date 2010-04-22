package org.synyx.minos.umt.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
 * Implementation of user management module. Uses an implementation of {@code PasswordCreator} to generate a random
 * password, that is used as initial password for a new user after successful registration.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional(readOnly = true)
public class UserManagementImpl implements UserManagement, UserAccountManagement {

    private final UserDao userDao;
    private final RoleDao roleDao;

    private final AuthenticationService authenticationService;
    private final PasswordCreator passwordCreator;


    /**
     * Creates a new {@link UserManagementImpl} with the given {@link UserDao}, {@link RoleDao},
     * {@link AuthenticationService} and {@link PasswordCreator}.
     * 
     * @param userDao
     * @param roleDao
     * @param authenticationService
     */
    public UserManagementImpl(UserDao userDao, RoleDao roleDao, AuthenticationService authenticationService,
            PasswordCreator passwordCreator) {

        Assert.notNull(userDao);
        Assert.notNull(roleDao);
        Assert.notNull(authenticationService);

        this.userDao = userDao;
        this.roleDao = roleDao;
        this.authenticationService = authenticationService;
        this.passwordCreator = passwordCreator;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.umt.service.UserManagement#delete(java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(User user) throws UserNotFoundException {

        Assert.notNull(user);

        if (authenticationService.isCurrentUser(user)) {
            throw new IllegalArgumentException("Illegal attempt to delete the currently logged in user!");
        }

        if (!exists(user.getId())) {
            throw new UserNotFoundException(user.getId());
        }

        userDao.delete(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#save(com.synyx.minos.umt.domain .User, boolean)
     */
    @Override
    @Transactional(readOnly = false)
    public void save(User user, boolean forcePasswordEncryption) {

        Assert.notNull(user);

        if (!user.isNew() && !exists(user.getId())) {
            throw new IllegalArgumentException("User with id " + user.getId() + " does not exist!");
        }

        if (user.isNew() && !user.hasPassword()) {
            user.setPassword(passwordCreator.generatePassword());
        }

        eventuallyEncryptPassword(user, forcePasswordEncryption);

        userDao.save(user);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#save(com.synyx.minos.umt.domain .User)
     */
    @Override
    @Transactional(readOnly = false)
    public void save(User user) {

        save(user, false);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#save(com.synyx.minos.umt.domain .Role)
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Role role) {

        Assert.notNull(role);

        roleDao.save(role);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getUsers()
     */
    @Override
    public List<User> getUsers() {

        return userDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getUsers(org.synyx.hades.domain .Pageable,
     * org.synyx.hades.domain.support.Sort)
     */
    @Override
    public Page<User> getUsers(Pageable pageable) {

        return userDao.readAll(pageable);
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getUser(java.lang.Long)
     */
    @Override
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
    @Override
    public User getUser(String username) {

        Assert.notNull(username);

        try {
            return userDao.findByUsername(username);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    /**
     * Encrypts the password of the given user if it matches the following conditions:
     * <ul>
     * <li>An encryption provider is configured</li>
     * <li>The user is new, meaning the password has never been encrypted yet</li>
     * <li>The user's password is not the one of the old user or forcePasswordEncryption is true</li>
     * </ul>
     * 
     * @param user
     * @param forcePasswordEncryption
     * @return
     */
    private void eventuallyEncryptPassword(User user, boolean forcePasswordEncryption) {

        if (null == authenticationService) {
            return;
        }

        // Encrypt new users password and set it
        if (user.isNew()) {

            userDao.save(user);
            user.setPassword(authenticationService.getEncryptedPasswordFor(user));

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
        if (forcePasswordEncryption || !oldUser.getPassword().equals(user.getPassword())) {
            user.setPassword(authenticationService.getEncryptedPasswordFor(user));
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#exists(java.lang.Long)
     */
    @Override
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
    @Override
    public List<Role> getRoles() {

        return roleDao.readAll();
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.umt.service.UserManagement#getRole(java.lang.Long)
     */
    @Override
    public Role getRole(Long id) {

        return roleDao.readByPrimaryKey(id);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.umt.service.UserManagement#getRole(java.lang.String)
     */
    @Override
    public Role getRole(String name) {

        return roleDao.findByName(name);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.umt.service.UserManagement#getUsersByEmail(java.lang.String)
     */
    @Override
    public List<User> getUsersByEmail(String argEmail) {

        return userDao.findByEmailAddress(argEmail);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.umt.service.UserManagement#deleteRole(org.synyx.minos .core.domain.Role)
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteRole(Role role) {

        roleDao.delete(role);
    }


    @Override
    public void saveUserAccount(User user) {

        Assert.notNull(user);

        User existingUser = getUser(user.getId());
        user.setUsername(existingUser.getUsername());
        user.setRoles(existingUser.getRoles());
        user.setActive(existingUser.isActive());

        save(user);
        userDao.flush();
    }
}
