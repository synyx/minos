package org.synyx.minos.core.bootstrap;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.SimpleLifecycle;
import org.synyx.minos.security.CorePermissions;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Simple installer populationg the database with 100 users if none is available
 * actually. Used during development, not aimed to be used in production
 * environments.
 * <p>
 * Currently uses quite a hack to enable authenticated access to the injected
 * service.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MinosCoreInstaller extends SimpleLifecycle {

    private UserManagement userManagement;


    /**
     * Setter to inject the {@link UserManagement} to setup basic data.
     * 
     * @param userManagement the userManagement to set
     */
    public void setUserManagement(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    /**
     * Returns the {@link UserManagement}.
     * 
     * @return the userManagement
     */
    protected UserManagement getUserManagement() {

        return userManagement;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.module.SimpleLifecycle#onStart()
     */
    @Override
    public void onStart() {

        if (0 == userManagement.getUsers().size()) {

            Role adminRole = new Role(Role.ADMIN_NAME);
            adminRole.add(CorePermissions.UMT_ADMIN,
                    CorePermissions.NOPERMISSION);

            Role userRole = new Role(Role.USER_NAME);
            userRole.add(CorePermissions.NOPERMISSION);

            userManagement.save(adminRole);
            userManagement.save(userRole);

            User user = new User("admin", "admin@admin.org", "admin");
            user.addRole(adminRole);
            user.addRole(userRole);

            userManagement.save(user);
        }
    }
}
