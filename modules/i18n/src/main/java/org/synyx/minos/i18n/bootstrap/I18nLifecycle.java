package org.synyx.minos.i18n.bootstrap;

import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.core.domain.Role;
import org.synyx.minos.core.module.ModuleLifecycleException;
import org.synyx.minos.core.module.SimpleNoOpLifecycle;
import org.synyx.minos.i18n.I18nPermissions;
import org.synyx.minos.i18n.service.MessageService;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Livecycle of I18n Module
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
@Transactional
public class I18nLifecycle extends SimpleNoOpLifecycle {

    private final UserManagement userManagement;
    private final MessageService messageService;


    /**
     * Creates a new {@link I18nLifecycle}.
     * 
     * @param skillManagement
     * @param resumeManagement
     * @param userManagement
     */
    public I18nLifecycle(UserManagement userManagement, MessageService messageService) {

        this.userManagement = userManagement;
        this.messageService = messageService;
    }


    @Override
    public void onStart() throws ModuleLifecycleException {

        messageService.importMessages();
    }


    @Override
    public void onStop() throws ModuleLifecycleException {

        messageService.exportMessages();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.SimpleNoOpLifecycle#install()
     */
    @Override
    public void install() {

        initPermissions();

    }


    /**
     * Initializes basic skill module permissions to the default roles.
     */
    private void initPermissions() {

        // Role userRole = userManagement.getRole(Role.USER_NAME);
        // userRole.add(I18nPermissions.);
        // userManagement.save(userRole);

        Role adminRole = userManagement.getRole(Role.ADMIN_NAME);
        adminRole.add(I18nPermissions.I18N_ADMINISTRATION);

        userManagement.save(adminRole);
    }

}
