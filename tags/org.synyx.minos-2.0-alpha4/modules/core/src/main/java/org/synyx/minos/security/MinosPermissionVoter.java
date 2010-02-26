package org.synyx.minos.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.umt.dao.RoleDao;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosPermissionVoter implements AccessDecisionVoter {

    private Map<Authentication, Collection<ConfigAttribute>> cache =
            new WeakHashMap<Authentication, Collection<ConfigAttribute>>();

    private RoleDao roleDao;


    /**
     * @param roleHierarchy
     */
    public MinosPermissionVoter(RoleDao roleDao) {

        this.roleDao = roleDao;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.vote.AccessDecisionVoter#supports(java.lang
     * .Class)
     */
    public boolean supports(Class<?> clazz) {

        return true;
    }


    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.security.vote.AccessDecisionVoter#supports(org.
     * springframework.security.ConfigAttribute)
     */
    public boolean supports(ConfigAttribute attribute) {

        return true;
    }


    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.security.access.AccessDecisionVoter#vote(org.
     * springframework.security.core.Authentication, java.lang.Object,
     * java.util.Collection)
     */
    public int vote(Authentication authentication, Object object,
            Collection<ConfigAttribute> configAttributes) {

        if (configAttributes.isEmpty()) {
            return ACCESS_ABSTAIN;
        }

        synchronized (cache) {
            if (cache.containsKey(authentication)) {
                for (ConfigAttribute attribute : cache.get(authentication)) {
                    if (configAttributes.contains(attribute)) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String roleName = Role.stripPrefix(authority.getAuthority());
            Role role = roleDao.findByName(roleName);

            if (role == null) {
                continue;
            }

            for (String permission : role.getPermissions()) {

                ConfigAttribute attribute = new SecurityConfig(permission);

                if (configAttributes.contains(attribute)) {

                    put(authentication, attribute);

                    return ACCESS_GRANTED;
                }
            }
        }

        return ACCESS_ABSTAIN;
    }


    private synchronized void put(Authentication authentication,
            ConfigAttribute attribute) {

        Collection<ConfigAttribute> attributes = cache.get(authentication);

        if (null == attributes) {
            attributes = new HashSet<ConfigAttribute>();
        }

        attributes.add(attribute);
        cache.put(authentication, attributes);
    }
}
