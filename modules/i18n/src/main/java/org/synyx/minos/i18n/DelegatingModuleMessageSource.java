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
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class DelegatingModuleMessageSource implements ModuleMessageSource {

    private MessageSource target;
    private Module module;

    private boolean lenient = false;


    public DelegatingModuleMessageSource(Module module, MessageSource target) {

        this.target = target;
        this.module = module;
        // TODO Auto-generated constructor stub
    }


    /**
     * @param arg0
     * @param arg1
     * @return
     * @throws NoSuchMessageException
     * @see org.springframework.context.MessageSource#getMessage(org.springframework.context.MessageSourceResolvable,
     *      java.util.Locale)
     */
    public String getMessage(MessageSourceResolvable arg0, Locale arg1) throws NoSuchMessageException {

        return target.getMessage(arg0, arg1);
    }


    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @throws NoSuchMessageException
     * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.util.Locale)
     */
    public String getMessage(String arg0, Object[] arg1, Locale arg2) throws NoSuchMessageException {

        return target.getMessage(arg0, arg1, arg2);
    }


    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @return
     * @see org.springframework.context.MessageSource#getMessage(java.lang.String, java.lang.Object[], java.lang.String,
     *      java.util.Locale)
     */
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
     * @param lenient the lenient to set
     */
    public void setLenient(boolean lenient) {

        this.lenient = lenient;
    }

}
