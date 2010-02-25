package org.synyx.minos.core.web.support;

import org.synyx.minos.core.module.ModuleDependent;
import org.synyx.minos.core.module.Modules;
import org.synyx.minos.core.web.event.AbstractEventHandler;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@ModuleDependent(value = { Modules.UMT })
public class UserCreatedEventHandler extends
        AbstractEventHandler<UserCreatedEvent> {

}
