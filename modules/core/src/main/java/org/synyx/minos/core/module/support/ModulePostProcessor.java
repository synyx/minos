package org.synyx.minos.core.module.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;

import org.springframework.core.annotation.AnnotationUtils;

import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.ModuleDependent;
import org.synyx.minos.core.module.internal.MinosModule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This PostProcessor removes beans annotated with {@link ModuleDependent}, if the appropriate modules are not
 * available.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModulePostProcessor implements BeanFactoryPostProcessor {

    private static final Log log = LogFactory.getLog(ModulePostProcessor.class);

    private Set<String> modules;
    private Map<String, List<String>> decorators;

    /**
     * Constructor of {@code ModulePostProcessor}.
     */
    public ModulePostProcessor() {

        modules = new HashSet<String>();
        decorators = new HashMap<String, List<String>>();
    }

    /*
     * (non-Javadoc)
     *
     * @seeorg.springframework.beans.factory.config.BeanFactoryPostProcessor# postProcessBeanFactory
     * (org.springframework.beans.factory.config.ConfigurableListableBeanFactory )
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        if (!(beanFactory instanceof BeanDefinitionRegistry)) {
            return;
        }

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

        // Trigger search for modules and decorators
        findModulesAndDecorators(beanFactory);

        // Visit all decorators
        for (String decoratorName : decorators.keySet()) {
            // Retrieve modules required by the decorator
            List<String> requiredModules = decorators.get(decoratorName);

            Boolean hasDependencies = 0 != beanFactory.getDependentBeans(decoratorName).length;

            if (hasDependencies) {
                throw new BeanDefinitionValidationException("Cannot remove bean definition " + decoratorName
                    + " because at least one other bean depends on it!");
            }

            if (!modules.containsAll(requiredModules)) {
                if (log.isDebugEnabled()) {
                    log.debug("Removing module decorator " + decoratorName + " because it requires module "
                        + requiredModules + " and only " + modules + " were found!");
                }

                registry.removeBeanDefinition(decoratorName);
            }
        }
    }


    /**
     * Retrieves all modules (interfaces annotated with <code>Module</code>) and decorators (classes annotated with
     * <code>ModuleDecorator</code>) from the given bean factory and populates member variables.
     *
     * @param beanFactory
     */
    private void findModulesAndDecorators(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        String[] beanNames = beanFactory.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            for (Module moduleInformation : beanFactory.getBeansOfType(MinosModule.class, false, false).values()) {
                modules.add(moduleInformation.getIdentifier());
            }

            String beanClassName = beanDefinition.getBeanClassName();

            if (null == beanClassName) {
                continue;
            }

            try {
                Class<?> beanClass = Class.forName(beanClassName);

                // Decorator bean?
                ModuleDependent decorator = AnnotationUtils.findAnnotation(beanClass, ModuleDependent.class);

                if (null != decorator) {
                    decorators.put(beanName, Arrays.asList(decorator.value()));
                }
            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }
}
