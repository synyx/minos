package org.synyx.minos.core.message;

import org.springframework.context.support.ResourceBundleMessageSource;

import org.synyx.minos.core.module.Module;
import org.synyx.minos.util.Assert;


/**
 * {@link MessageSourcePlugin} to be configured based on a configured {@link Module}.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class ModuleMessageSourceImpl extends ResourceBundleMessageSource implements ModuleMessageSource {

    private final Module module;

    private boolean lenient;

    /**
     * Creates a new {@link ModuleMessageSourceImpl} with the given {@link Module}.
     *
     * @param  module  the module to pull message resources from
     */
    public ModuleMessageSourceImpl(Module module) {

        Assert.notNull(module);
        this.module = module;

        setBasename(module.getModuleResourcePath("messages"));
    }

    /**
     * Sets whether the {@link MessageSourcePlugin} shall act lenient on support requests.
     *
     * @param  lenient  the lenient to set
     */
    public void setLenient(boolean lenient) {

        this.lenient = lenient;
    }


    @Override
    public Module getModule() {

        return module;
    }


    @Override
    public boolean supports(String delimiter) {

        return lenient || module.getIdentifier().equals(delimiter);
    }
}
