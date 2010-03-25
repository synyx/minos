package org.synyx.minos.core.authentication;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.test.AbstractModuleIntegrationTest;


/**
 * Integration test for {@link AuthenticationService}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class AuthenticationServiceIntegrationTest extends
        AbstractModuleIntegrationTest {

    @Autowired
    private AuthenticationService authenticationService;


    @Test
    public void changingUsernameDoesNotBreakPassword() throws Exception {

        User user = new User("username", "foo@bar.de", "password");
        String password = authenticationService.getEncryptedPasswordFor(user);

        user.setUsername("newUsername");

        assertThat(authenticationService.getEncryptedPasswordFor(user),
                is(password));
    }
}
