package org.synyx.minos.core.module.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.synyx.minos.core.module.ModuleManager;
import org.synyx.minos.core.module.ModuleManagerAware;


/**
 * {@link BeanPostProcessor} that injects a {@link ModuleManager} into any bean implementing {@link ModuleManagerAware}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleManagerAwareBeanPostProcessor implements ApplicationContextAware, BeanPostProcessor {

    private ModuleManager moduleManager;


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) {

        this.moduleManager = applicationContext.getBean(ModuleManager.class);
    }


    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.beans.factory.config.BeanPostProcessor# postProcessAfterInitialization(java.lang.Object,
     * java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }


    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.beans.factory.config.BeanPostProcessor# postProcessBeforeInitialization(java.lang.Object,
     * java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof ModuleManagerAware) {
            ((ModuleManagerAware) bean).setModuleManager(moduleManager);
        }

        return bean;
    }
}
