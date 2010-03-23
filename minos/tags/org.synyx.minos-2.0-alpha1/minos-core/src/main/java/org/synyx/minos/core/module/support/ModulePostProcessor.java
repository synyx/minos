package org.synyx.minos.core.module.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.synyx.minos.core.module.Modules;



/**
 * This PostProcessor removes beans annotated with {@link ModuleDependent}, if
 * no beans are found whose type is annotated with {@link Module}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModulePostProcessor implements BeanFactoryPostProcessor {

    private static final Log log = LogFactory.getLog(ModulePostProcessor.class);

    private List<Modules> modules;
    private Map<String, List<Modules>> decorators;


    /**
     * Constructor of {@code ModulePostProcessor}.
     */
    public ModulePostProcessor() {

        modules = new ArrayList<Modules>();
        decorators = new HashMap<String, List<Modules>>();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
     */
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {

        if (!(beanFactory instanceof BeanDefinitionRegistry)) {
            return;
        }

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

        // Trigger search for modules and decorators
        findModulesAndDecorators(beanFactory);

        // Visit all decorators
        for (String decoratorName : decorators.keySet()) {

            // Retrieve modules required by the decorator
            List<Modules> requiredModules = decorators.get(decoratorName);

            Boolean hasDependencies = 0 != beanFactory
                    .getDependentBeans(decoratorName).length;

            if (hasDependencies) {
                throw new BeanDefinitionValidationException(
                        "Cannot remove bean definition "
                                + decoratorName
                                + " because at least one other bean depends on it!");
            }

            if (!modules.containsAll(requiredModules)) {

                if (log.isDebugEnabled()) {
                    log.debug("Removing module decorator " + decoratorName
                            + " because it requires module " + requiredModules
                            + " and only " + modules + " were found!");
                }

                registry.removeBeanDefinition(decoratorName);
            }
        }
    }


    /**
     * Retrieves all modules (interfaces annotated with <code>Module</code>)
     * and decorators (classes annotated with <code>ModuleDecorator</code>)
     * from the given bean factory and populates member variables.
     * 
     * @param beanFactory
     */
    private void findModulesAndDecorators(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {

        String[] beanNames = beanFactory.getBeanDefinitionNames();

        for (String beanName : beanNames) {

            BeanDefinition beanDefinition = beanFactory
                    .getBeanDefinition(beanName);

            String beanClassName = beanDefinition.getBeanClassName();

            if (null == beanClassName) {
                continue;
            }

            try {

                Class<?> beanClass = Class.forName(beanClassName);

                // Module bean?
                Module module = AnnotationUtils.findAnnotation(beanClass,
                        Module.class);

                if (null != module) {
                    modules.add(module.value());
                }

                // Decorator bean?
                ModuleDependent decorator = AnnotationUtils.findAnnotation(
                        beanClass, ModuleDependent.class);

                if (null != decorator) {
                    decorators.put(beanName, Arrays.asList(decorator.value()));
                }

            } catch (ClassNotFoundException e) {
                continue;
            }
        }
    }
}
