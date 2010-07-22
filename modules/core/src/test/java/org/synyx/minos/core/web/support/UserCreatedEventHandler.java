package org.synyx.minos.core.web.support;

import org.synyx.minos.core.module.ModuleDependent;
import org.synyx.minos.core.web.event.AbstractEventHandler;
import org.synyx.minos.umt.Umt;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@ModuleDependent(Umt.IDENTIFIER)
public class UserCreatedEventHandler extends AbstractEventHandler<UserCreatedEvent> {

}
