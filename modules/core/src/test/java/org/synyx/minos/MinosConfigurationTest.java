package org.synyx.minos;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.test.AbstractModuleIntegrationTest;
import org.synyx.minos.umt.dao.RoleDao;
import org.synyx.minos.umt.service.UserManagement;



/**
 * Simple unit test to launch a basic Minos instance. Mainly to check
 * configuration integrity.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Transactional
public class MinosConfigurationTest extends AbstractModuleIntegrationTest {

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private RoleDao roleDao;


    @Test
    public void testname() throws Exception {
        
        Role adminRole = roleDao.findAdminRole();
        
        assertNotNull(adminRole);
        assertNotNull(roleDao.findUserRole());

        List<User> users = userManagement.getUsers();
        
        assertEquals(1, users.size());
        assertTrue(users.get(0).has(adminRole));
    }
}
