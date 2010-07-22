package org.synyx.minos.umt.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.test.AbstractDaoIntegrationTest;
import org.synyx.minos.umt.dao.RoleDao;


/**
 * Integration test for <code>RoleDao</code>.
 * 
 * @see RoleDao
 * @author Oliver Gierke
 */
public class RoleDaoIntegrationTest extends AbstractDaoIntegrationTest {

    @Autowired
    private RoleDao roleDao;


    /**
     * Tests creation of roles.
     */
    @Test
    public void testRoleCreation() {

        roleDao.save(new Role("ADMIN"));
    }
}
