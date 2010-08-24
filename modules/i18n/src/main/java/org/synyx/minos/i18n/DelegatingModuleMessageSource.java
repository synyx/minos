/**
 * 
 */
package org.synyx.minos.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.synyx.minos.core.message.ModuleMessageSource;
import org.synyx.minos.core.module.Module;


/**
 * Implementation of {@link ModuleMessageSource} that delegates to any {@link MessageSource}. This can be used to adapt
 * {@link ModuleMessageSource} to any {@link MessageSource}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class DelegatingModuleMessageSource implements ModuleMessageSource {

    private MessageSource target;
    private Module module;

    private boolean lenient = false;


    /**
     * Create a new instance if {@link DelegatingModuleMessageSource}
     * 
     * @param module the {@link Module} this is for
     * @param target the {@link MessageSource} to delegate resolving-requests to
     */
    public DelegatingModuleMessageSource(Module module, MessageSource target) {

        this.target = target;
        this.module = module;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSource#getMessage(org.springframework.context.MessageSourceResolvable,
     * java.util.Locale)
     */
    @Override
    public String getMessage(MessageSourceResolvable arg0, Locale arg1) throws NoSuchMessageException {

        return target.getMessage(arg0, arg1);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.util.Locale)
     */
    @Override
    public String getMessage(String arg0, Object[] arg1, Locale arg2) throws NoSuchMessageException {

        return target.getMessage(arg0, arg1, arg2);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.lang.String,
     * java.util.Locale)
     */
    @Override
    public String getMessage(String arg0, Object[] arg1, String arg2, Locale arg3) {

        return target.getMessage(arg0, arg1, arg2, arg3);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.message.MessageSourcePlugin#supports(java.lang.String)
     */
    @Override
    public boolean supports(String delimiter) {

        return lenient || module.getIdentifier().equals(delimiter);
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


    /**
     * Setter for the lenient-option. If set to true the MessageSource will request resolving all message-codes not only
     * the ones prefixed with its modules name
     * 
     * @param lenient If set to true the MessageSource will request resolving all message-codes not only the ones
     *            prefixed with its modules name
     */
    public void setLenient(boolean lenient) {

        this.lenient = lenient;
    }

}
