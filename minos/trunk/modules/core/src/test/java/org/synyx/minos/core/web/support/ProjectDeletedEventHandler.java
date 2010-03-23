package org.synyx.minos.core.web.support;

import org.synyx.minos.core.module.ModuleDependent;
import org.synyx.minos.core.web.event.AbstractEventHandler;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
@ModuleDependent("pmt")
public class ProjectDeletedEventHandler extends
        AbstractEventHandler<ProjectDeletedEvent> {
}
