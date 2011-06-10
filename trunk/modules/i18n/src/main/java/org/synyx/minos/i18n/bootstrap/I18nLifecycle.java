package org.synyx.minos.i18n.bootstrap;

import org.springframework.transaction.annotation.Transactional;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.i18n.I18nPermissions;
import org.synyx.minos.i18n.service.MessageTransferService;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Lifecycle of I18n Module
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Transactional
public class I18nLifecycle extends SimpleNoOpLifecycle {

    private final UserManagement userManagement;
    private final MessageTransferService messageTransferService;

    public I18nLifecycle(UserManagement userManagement, MessageTransferService messageTransferService) {

        this.userManagement = userManagement;
        this.messageTransferService = messageTransferService;
    }

    @Override
    public void onStart() {

        messageTransferService.importMessages();
    }


    @Override
    public void install() {

        initPermissions();
    }


    /**
     * Initializes basic module permissions to the default roles.
     */
    private void initPermissions() {

        Role adminRole = userManagement.getRole(Role.ADMIN_NAME);
        adminRole.add(I18nPermissions.I18N_ADMINISTRATION);

        userManagement.save(adminRole);
    }
}
