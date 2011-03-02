package org.synyx.minos.core.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.synyx.hades.domain.auditing.AuditorAware;

import org.synyx.minos.core.domain.Password;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.UserDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Implementation of the {@code AuthenticationService} to use Spring Security.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SpringSecurityAuthenticationService extends AbstractAuthenticationService implements AuditorAware<User> {

    private static final Log LOG = LogFactory.getLog(SpringSecurityAuthenticationService.class);

    private final UserDao userDao;
    private final AccessDecisionManager accessDecisionManager;
    private final SaltSource saltSource;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new {@link SpringSecurityAuthenticationService}.
     *
     * @param userDao the DAO to lookup users
     * @param accessDecisionManager the {@link AccessDecisionManager} to be consulted to find out about permissions
     * @param saltSource the {@link SaltSource} to be used on password encryption
     * @param passwordEncoder the {@link PasswordEncoder} to be used for password encryption
     */
    public SpringSecurityAuthenticationService(UserDao userDao, AccessDecisionManager accessDecisionManager,
        SaltSource saltSource, PasswordEncoder passwordEncoder) {

        super();
        this.userDao = userDao;
        this.accessDecisionManager = accessDecisionManager;
        this.saltSource = saltSource;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.synyx.hades.domain.support.AuditorAware#getCurrentAuditor()
     */
    @Override
    public User getCurrentAuditor() {

        return getCurrentUser();
    }


    /*
     * (non-Javadoc)
     *
     * @see com.synyx.minos.core.authentication.AuthenticationService#getCurrentUser ()
     */
    @Override
    public User getCurrentUser() {

        UserDetails userDetails = getAuthenticatedUser();

        if (userDetails == null) {
            return null;
        } else if (userDetails instanceof MinosUserDetails) {
            return userDao.readByPrimaryKey(((MinosUserDetails) userDetails).getId());
        } else {
            return userDao.findByUsername(userDetails.getUsername());
        }
    }


    /*
     * (non-Javadoc)
     *
     * @seecom.synyx.minos.core.authentication.AuthenticationService#
     * getEncryptedPasswordFor(com.synyx.minos.core.domain.User)
     */
    @Override
    public Password getEncryptedPasswordFor(User user) {

        if (passwordEncoder == null) {
            return user.getPassword();
        }

        Object salt = saltSource == null ? null : saltSource.getSalt(new MinosUserDetails(user));
        String plainPassword = user.getPassword().toString();

        return new Password(passwordEncoder.encodePassword(plainPassword, salt), true);
    }


    /**
     * Checks the current authentication for the given permissions.
     *
     * @param permissions
     * @return whether the currently authenticated {@link User} has the given permissions. Will return {@literal false}
     *         if {@literal null} or an empty collection is given.
     */
    @Override
    protected boolean hasPermissions(Collection<String> permissions) {

        if (null == permissions || permissions.isEmpty()) {
            return false;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return false;
        }

        try {
            accessDecisionManager.decide(authentication, null, toAttributes(permissions));

            return true;
        } catch (AccessDeniedException e) {
            LOG.debug("Access denied!", e);

            return false;
        } catch (InsufficientAuthenticationException e) {
            LOG.debug("Access denied!", e);

            return false;
        }
    }


    private List<ConfigAttribute> toAttributes(Collection<String> permissions) {

        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();

        for (String permission : permissions) {
            attributes.add(new SecurityConfig(permission.toString()));
        }

        return attributes;
    }


    private UserDetails getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null == authentication) {
            return null;
        }

        // Principal may be "anonymous", which is a string
        if (!(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }

        return (UserDetails) authentication.getPrincipal();
    }
}
