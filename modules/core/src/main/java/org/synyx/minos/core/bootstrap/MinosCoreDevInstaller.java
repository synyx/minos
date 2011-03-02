package org.synyx.minos.core.bootstrap;

import org.springframework.transaction.annotation.Transactional;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Installer for development purposes. Creates 100 dummy {@link User}s.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class MinosCoreDevInstaller extends MinosCoreInstaller {

    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.core.bootstrap.MinosCoreInstaller#onStart()
     */
    @Override
    public void onStart() {

        Role admin = createAdmin();
        Role user = createUser();
        createSuperuser(admin, user);

        UserManagement userManagement = getUserManagement();

        if (1 <= userManagement.getUsers().size()) {
            for (int i = 0; i < 100; i++) {
                boolean isAdmin = 0 == i % 2;

                User foo = new User("user" + i, "user" + i + "@synyx.de", "foo");

                if (isAdmin) {
                    foo.addRole(admin);
                } else {
                    foo.addRole(user);
                }

                userManagement.save(foo);
            }
        }
    }
}
