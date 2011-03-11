package org.synyx.minos.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.synyx.minos.core.message.ModuleMessageSource;
import org.synyx.minos.core.module.Module;

import java.util.Locale;


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

    @Override
    public String getMessage(MessageSourceResolvable arg0, Locale arg1) {

        return target.getMessage(arg0, arg1);
    }


    @Override
    public String getMessage(String arg0, Object[] arg1, Locale arg2) {

        return target.getMessage(arg0, arg1, arg2);
    }



    @Override
    public String getMessage(String arg0, Object[] arg1, String arg2, Locale arg3) {

        return target.getMessage(arg0, arg1, arg2, arg3);
    }


    @Override
    public boolean supports(String delimiter) {

        return lenient || module.getIdentifier().equals(delimiter);
    }


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
