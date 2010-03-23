package org.synyx.minos.core.bootstrap;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.ModuleLifecycleException;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.umt.UmtPermissions;
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
public class MinosCoreInstaller extends SimpleNoOpLifecycle {

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
     * @see org.synyx.minos.core.module.SimpleNoOpLifecycle#install()
     */
    @Override
    public void install() throws ModuleLifecycleException {

        Role adminRole = createAdmin();
        Role userRole = createUser();
        createSuperuser(adminRole, userRole);
    }


    protected void createSuperuser(Role argAdminRole, Role argUserRole) {

        User user = new User("admin", "admin@admin.org", "admin");
        user.addRole(argAdminRole);
        user.addRole(argUserRole);

        userManagement.save(user);
    }


    protected Role createUser() {

        Role userRole = new Role(Role.USER_NAME);
        userManagement.save(userRole);
        return userRole;
    }


    protected Role createAdmin() {

        Role adminRole = new Role(Role.ADMIN_NAME);
        adminRole.add(UmtPermissions.UMT_ADMIN);
        userManagement.save(adminRole);
        return adminRole;
    }
}
