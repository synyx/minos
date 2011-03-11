package org.synyx.minos.core.module.support;

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

    public void setApplicationContext(ApplicationContext applicationContext) {

        this.moduleManager = applicationContext.getBean(ModuleManager.class);
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        return bean;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        if (bean instanceof ModuleManagerAware) {
            ((ModuleManagerAware) bean).setModuleManager(moduleManager);
        }

        return bean;
    }
}
