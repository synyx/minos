#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.core.annotation.Order;

import org.springframework.util.Assert;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.module.ModuleLifecycleException;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.umt.service.UserManagement;
import ${package}.dao.ItemDao;



@Order(0)
public class SampleLifecycle extends SimpleNoOpLifecycle {

    private ItemDao itemDao;
    private UserManagement userManagement;

    public SampleLifecycle(ItemDao itemDao, UserManagement userManagement) {

        this.itemDao = itemDao;
        this.userManagement = userManagement;
    }

    @Override
    public void install() throws ModuleLifecycleException {

        Assert.notNull(itemDao, "We need an item dao to work");

        // We can ensure that admins always have the ITEMS_DELETE permission
        Role adminRole = userManagement.getRole("ADMIN");
        adminRole.add(SamplePermissions.ITEMS_DELETE);
        userManagement.save(adminRole);

        // ... or create other non-admin users
        if (userManagement.getUser("user") == null) {
            User user = new User("user", "user@example.com", "user");
            Role userRole = userManagement.getRole("USER");
            user.addRole(userRole);
            userManagement.save(user);
        }
    }
}
