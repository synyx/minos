package org.synyx.minos.core.bootstrap;

import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.umt.dao.RoleDao;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Installer for development purposes. Creates 100 dummy {@link User}s.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class MinosCoreDevInstaller extends MinosCoreInstaller {

    private RoleDao roleDao;


    /**
     * Creates a new {@link MinosCoreDevInstaller} instance.
     * 
     * @param roleDao
     */
    public MinosCoreDevInstaller(RoleDao roleDao) {

        this.roleDao = roleDao;
    }



    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.bootstrap.MinosCoreInstaller#onStart()
     */
    @Override
    public void onStart() {

        super.onStart();

        UserManagement userManagement = getUserManagement();

        if (1 <= userManagement.getUsers().size()) {

            for (int i = 0; i < 100; i++) {

                boolean isAdmin = 0 == i % 2;

                User user =
                        new User("user" + i, "user" + i + "@synyx.de", "foo");

                if (isAdmin) {
                    user.addRole(roleDao.findAdminRole());
                } else {
                    user.addRole(roleDao.findUserRole());
                }

                userManagement.save(user);
            }
        }
    }
}
