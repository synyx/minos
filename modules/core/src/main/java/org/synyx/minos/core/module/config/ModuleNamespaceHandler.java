package org.synyx.minos.core.module.config;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * {@link NamespaceHandler} for module elements.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleNamespaceHandler extends NamespaceHandlerSupport {

    private static final String MODULE = "module";
    private static final String REST_MODULE = "rest-module";


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
     */
    @Override
    public void init() {

        registerBeanDefinitionParser(MODULE, new ModuleBeanDefinitionParser());
        registerBeanDefinitionParser(REST_MODULE, new RestModuleBeanDefinitionParser());
    }

}
