package org.synyx.minos.core.web;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.config.BeanDefinition;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import org.springframework.core.type.filter.AnnotationTypeFilter;

import org.springframework.util.ClassUtils;

import org.synyx.minos.core.domain.ValueObject;

import java.util.Collection;
import java.util.HashSet;


/**
 * Scans the configured base package (Ant style patterns supported) for classes annotated with {@link ValueObject} an
 * registers an appropriate {@link ValueObjectPropertyEditor}for each type found.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 * @see  ValueObject
 */
public class ValueObjectPropertyEditorRegistrar implements PropertyEditorRegistrar {

    private final Collection<Class<?>> valueObjectClasses;

    /**
     * Creates a new {@link ValueObjectPropertyEditorRegistrar} scanning the given base package for classes annotated
     * with {@link ValueObject}.
     *
     * @param  basePackage  a java package
     */
    public ValueObjectPropertyEditorRegistrar(String basePackage) {

        this.valueObjectClasses = new HashSet<Class<?>>();

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(ValueObject.class));

        for (BeanDefinition beanDefinition : provider.findCandidateComponents(basePackage)) {
            ClassLoader loader = ValueObjectPropertyEditorRegistrar.class.getClassLoader();
            String className = beanDefinition.getBeanClassName();

            if (ClassUtils.isPresent(className, loader)) {
                this.valueObjectClasses.add(ClassUtils.resolveClassName(className, loader));
            }
        }
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {

        for (Class<?> clazz : valueObjectClasses) {
            registry.registerCustomEditor(clazz, new ValueObjectPropertyEditor(clazz));
        }
    }
}
