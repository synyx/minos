package org.synyx.minos.core.message;

import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.ModuleAware;


/**
 * A {@link MessageSourcePlugin} that is configured according to a {@link Module}.
 *
 * @author  Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface ModuleMessageSource extends MessageSourcePlugin, ModuleAware {
}
