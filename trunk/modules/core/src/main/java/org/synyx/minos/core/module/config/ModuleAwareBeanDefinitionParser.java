package org.synyx.minos.core.module.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;

import org.springframework.util.StringUtils;

import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.internal.MinosModule;
import org.synyx.minos.util.Assert;
import org.synyx.minos.util.ClassUtils;

import org.w3c.dom.Element;


/**
 * Base class to create beans that need to be made aware of a module by adding a reference to the {@link MinosModule}
 * bean with the module identifier configured.
 *
 * <p>In the simplest case it is sufficient to instantiate an instance of this class and hand it the bean class to
 * instantiate. If there is further configuration required use
 * {@link #parseAdditionalAttributes(Element, BeanDefinitionBuilder)}. As the class is primarily used to create
 * anonymous beans picked up by type, {@link #shouldGenerateIdAsFallback()} is retuning {@literal true} by default.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class ModuleAwareBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    private static final String DEFAULT_MODULE_PROPERTY = "module";

    private static final String CONSTRUCTOR_TEMPLATE =
        "Bean class %s has to provide a constructor with an %s argument!";
    private static final String SETTER_TEMPLATE = "Bean class %s does not have a method set%s accepting an %s!";

    private final Class<?> beanClass;
    private final String moduleId;
    private final String moduleProperty;

    private boolean useConstructor = false;

    /**
     * Creates a new {@link ModuleAwareBeanDefinitionParser} to bind the module with the given id. Expects the bean
     * class to have a setter for {@value #DEFAULT_MODULE_PROPERTY} to accept a {@link Module} as parameter.
     *
     * @param  beanClass  a bean class
     * @param  moduleId  a module identifier
     */
    public ModuleAwareBeanDefinitionParser(Class<?> beanClass, String moduleId) {

        this(beanClass, moduleId, false);
    }


    /**
     * Creates a new {@link ModuleAwareBeanDefinitionParser} to bind the module with the given id. Expects the bean
     * class to either have a setter for {@value #DEFAULT_MODULE_PROPERTY} to accept a {@link Module} as parameter or a
     * constructor accepting a module as a parameter (if {@code useConstructor} is {@code true}).
     *
     * @param  beanClass  a bean class
     * @param  moduleId  a module identifier
     * @param  useConstructor  whether to supply the module via the constructor
     */
    public ModuleAwareBeanDefinitionParser(Class<?> beanClass, String moduleId, boolean useConstructor) {

        this(beanClass, moduleId, null, useConstructor);
    }


    /**
     * Creates a new {@link ModuleAwareBeanDefinitionParser} to bind the module with the given id and a custom module
     * property. Expects the bean class to either have a setter for the given property accepting a {@link Module} as
     * parameter or a constructor accepting a module as a parameter (if {@code useConstructor} is {@code true}).
     *
     * @param  beanClass  a bean class
     * @param  moduleId  a module identifier
     * @param  moduleProperty  the bean's module property
     * @param  useConstructor  whether to supply the module via the constructor
     */
    public ModuleAwareBeanDefinitionParser(Class<?> beanClass, String moduleId, String moduleProperty,
        boolean useConstructor) {

        this.beanClass = beanClass;
        this.moduleId = moduleId;
        this.moduleProperty = moduleProperty == null ? DEFAULT_MODULE_PROPERTY : moduleProperty;
        this.useConstructor = useConstructor;

        if (useConstructor) {
            Assert.isTrue(org.springframework.util.ClassUtils.hasConstructor(beanClass, Module.class),
                String.format(CONSTRUCTOR_TEMPLATE, beanClass, Module.class));
        } else {
            Assert.isTrue(ClassUtils.hasSetter(beanClass, this.moduleProperty, Module.class),
                String.format(SETTER_TEMPLATE, beanClass, StringUtils.capitalize(this.moduleProperty), Module.class));
        }
    }

    @Override
    protected Class<?> getBeanClass(Element element) {

        return beanClass;
    }


    @Override
    protected boolean shouldGenerateId() {

        return true;
    }


    @Override
    protected final void doParse(Element element, BeanDefinitionBuilder builder) {

        if (useConstructor) {
            builder.addConstructorArgReference(moduleId);
        } else {
            builder.addPropertyReference(moduleProperty, moduleId);
        }

        parseAdditionalAttributes(element, builder);
    }


    /**
     * Method to do further configuration on the bean to build. Implementations can expect the module already wired.
     *
     * @param  element
     * @param  builder
     */
    protected void parseAdditionalAttributes(Element element, BeanDefinitionBuilder builder) {
    }


    /**
     * Returns the module identifier.
     *
     * @return  the moduleId
     */
    protected String getModuleId() {

        return moduleId;
    }
}
