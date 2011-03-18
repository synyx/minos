package org.synyx.minos.core.module.config;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.util.Assert;


/**
 * {@link BeanFactoryPostProcessor} to transparently replace a referenced {@link org.synyx.minos.core.module.Lifecycle}
 * with a {@link org.springframework.aop.target.LazyInitTargetSource}.
 *
 * @author Jochen Schalanda
 */
public class ModuleBeanFactoryPostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static final String LIFECYCLE_PROPERTY = "lifecycle";
    private static final String TARGET_BEAN_NAME_PROPERTY = "targetBeanName";
    private static final String TARGET_SOURCE_PROPERTY = "targetSource";

    private static final String LAZY_INIT_TEMPLATE = "LazyInit%s";
    private static final String PROXY_FACTORY_TEMPLATE = "%sProxy";

    private ApplicationContext applicationContext;

    /**
     * Transparently replace the defined module life cycles with a {@link org.springframework.aop.framework.ProxyFactoryBean}
     * pointing to a {@link org.springframework.aop.target.LazyInitTargetSource} in order to force lazy initialization
     * of the {@link org.synyx.minos.core.module.Lifecycle} classes.
     *
     * @param context
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory context) throws BeansException {

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context;
        String[] moduleBeanNames = context.getBeanNamesForType(Module.class, false, false);

        for (String moduleBeanName : moduleBeanNames) {
            BeanDefinition moduleBeanDefinition = context.getBeanDefinition(moduleBeanName);

            MutablePropertyValues modulePropertyValues = moduleBeanDefinition.getPropertyValues();

            PropertyValue lifecyclePropertyValue = modulePropertyValues.getPropertyValue(LIFECYCLE_PROPERTY);

            if (lifecyclePropertyValue != null) {
                String originalLifecycleName = getLifecycleBeanName(lifecyclePropertyValue);
                String lazyInitTargetSourceName = String.format(LAZY_INIT_TEMPLATE, originalLifecycleName);
                String proxyFactoryBeanName = String.format(PROXY_FACTORY_TEMPLATE, lazyInitTargetSourceName);

                setBeanLazyInit(context.getBeanDefinition(originalLifecycleName));

                /**
                 * Create and register a {@link LazyInitTargetSource}pointing to the original
                 * {@link org.synyx.minos.core.module.Lifecycle} class.
                 */
                BeanDefinition lazyInitTargetSource =
                        BeanDefinitionBuilder.genericBeanDefinition(LazyInitTargetSource.class)
                                .addPropertyValue(TARGET_BEAN_NAME_PROPERTY, originalLifecycleName)
                                .getBeanDefinition();

                beanFactory.registerBeanDefinition(lazyInitTargetSourceName, lazyInitTargetSource);

                /**
                 * Create and register a {@link ProxyFactoryBean} pointing to the created {@link LazyInitTargetSource}
                 */
                BeanDefinition proxyFactoryBean =
                        BeanDefinitionBuilder.genericBeanDefinition(ProxyFactoryBean.class)
                                .addPropertyValue(TARGET_SOURCE_PROPERTY, new RuntimeBeanReference(lazyInitTargetSourceName))
                                .getBeanDefinition();

                beanFactory.registerBeanDefinition(proxyFactoryBeanName, proxyFactoryBean);

                // Replace the original module life cycle by our ProxyFactoryBean
                modulePropertyValues.add(LIFECYCLE_PROPERTY, new RuntimeBeanReference(proxyFactoryBeanName));
            }
        }
    }


    private String getLifecycleBeanName(PropertyValue lifecyclePropertyValue) {

        if (lifecyclePropertyValue != null) {
            RuntimeBeanReference value = (RuntimeBeanReference) lifecyclePropertyValue.getValue();

            if (value != null) {
                return value.getBeanName();
            }
        }

        return null;
    }


    private void setBeanLazyInit(BeanDefinition beanDefinition) {

        Assert.notNull(beanDefinition);

        beanDefinition.setLazyInit(true);
    }


    /**
     * Set the {@link ApplicationContext} instance
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }
}
