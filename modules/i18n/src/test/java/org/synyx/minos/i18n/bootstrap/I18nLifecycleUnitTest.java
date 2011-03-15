package org.synyx.minos.i18n.bootstrap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.i18n.service.MessageTransferService;
import org.synyx.minos.umt.service.UserManagement;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class I18nLifecycleUnitTest {

    private I18nLifecycle i18nLifecycle;

    @Mock
    private UserManagement userManagement;

    @Mock
    private MessageTransferService messageTransferService;

    @Before
    public void setUp() {

        i18nLifecycle = new I18nLifecycle(userManagement, messageTransferService);
    }

    @Test
    public void testOnStart() {

        i18nLifecycle.onStart();

        verify(messageTransferService, atLeastOnce()).importMessages();
    }

    @Test
    public void testInstall() {
        Role role = mock(Role.class);

        when(userManagement.getRole(Role.ADMIN_NAME)).thenReturn(role);

        i18nLifecycle.install();

        verify(userManagement, atLeastOnce()).save(role);
    }

}
