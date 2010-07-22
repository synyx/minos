package org.synyx.minos.core.message;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.ModuleAware;
import org.synyx.minos.util.Assert;


/**
 * {@link MessageSourcePlugin} to be configured based on a configured {@link Module}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleMessageSource extends ResourceBundleMessageSource implements MessageSourcePlugin, ModuleAware {

    private final Module module;

    private boolean lenient;


    /**
     * Creates a new {@link ModuleMessageSource} with the given {@link Module}.
     * 
     * @param module
     */
    public ModuleMessageSource(Module module) {

        Assert.notNull(module);
        this.module = module;

        setBasename(module.getModuleResourcePath("messages"));
    }


    /**
     * Sets whether the {@link MessageSourcePlugin} shall act lenient on support requests.
     * 
     * @param lenient the lenient to set
     */
    public void setLenient(boolean lenient) {

        this.lenient = lenient;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.ModuleAware#getModule()
     */
    @Override
    public Module getModule() {

        return module;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hera.core.Plugin#supports(java.lang.Object)
     */
    @Override
    public boolean supports(String delimiter) {

        return lenient || module.getIdentifier().equals(delimiter);
    }
}
