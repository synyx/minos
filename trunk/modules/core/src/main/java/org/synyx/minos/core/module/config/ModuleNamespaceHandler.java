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

    @Override
    public void init() {

        registerBeanDefinitionParser(MODULE, new ModuleBeanDefinitionParser());
    }
}
