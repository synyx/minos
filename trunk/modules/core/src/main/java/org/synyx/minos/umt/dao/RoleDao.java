package org.synyx.minos.umt.dao;

import org.synyx.hades.dao.GenericDao;
import org.synyx.hades.dao.Query;

import org.synyx.minos.core.domain.Role;


/**
 * Interface for a DAO for {@code Role}. Defines no further methods as it is just needed to ensure type safety.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface RoleDao extends GenericDao<Role, Long> {

    /**
     * Returns a role by its name.
     *
     * @param name
     * @return
     */
    Role findByName(String name);


    /**
     * Returns the admin role.
     *
     * @return
     */
    @Query("select r from Role r where r.name = '" + Role.ADMIN_NAME + "'")
    Role findAdminRole();


    /**
     * Returns the user role.
     *
     * @return
     */
    @Query("select r from Role r where r.name = '" + Role.USER_NAME + "'")
    Role findUserRole();
}
