package org.synyx.minos.core.web;

import org.springframework.beans.BeanUtils;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import org.synyx.minos.core.domain.ValueObject;
import org.synyx.minos.core.domain.ValueObjectInstantiationException;
import org.synyx.minos.util.Assert;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * {@link PropertyEditor} to create value objects. The classes to be bound with this {@link PropertyEditor} are required
 * to offer a constructor with a single {@link String} argument.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class ValueObjectPropertyEditor extends PropertyEditorSupport {

    private final Class<?> type;
    private final Method method;

    /**
     * Creates a {@link ValueObjectPropertyEditor} for the given type. Expects a constructor with a single
     * {@link String} argument to be able to create the value objects from.
     *
     * @param  type  a value class
     */
    public ValueObjectPropertyEditor(Class<?> type) {

        Assert.isTrue(ClassUtils.hasConstructor(type, String.class),
            "The given type must have a constructor with a single String argument!");

        this.type = type;
        this.method = null;
    }


    /**
     * Creates a {@link ValueObjectPropertyEditor} for the given type from a static factory method. The method is
     * provided by name and is looked up via reflection. It has to be static and has one parameter.
     *
     * @param  type  a value class
     * @param  factoryMethodName  name of the factory method
     */
    public ValueObjectPropertyEditor(Class<?> type, String factoryMethodName) {

        Method method = ClassUtils.getMethodIfAvailable(type, factoryMethodName, String.class);

        Assert.notNull(method, String.format("Method %s does not exist!", factoryMethodName));
        Assert.isTrue(Modifier.isStatic(method.getModifiers()),
            String.format("Method %s is not static!", factoryMethodName));
        Assert.isTrue(!method.getReturnType().equals(void.class), "Method must not be void!");

        this.type = type;
        this.method = method;
    }

    @Override
    public String getAsText() {

        return null == getValue() ? "" : getValue().toString();
    }


    @Override
    public void setAsText(String text) {

        if (!StringUtils.hasText(text)) {
            setValue(null);

            return;
        }

        try {
            if (method == null) {
                Constructor<?> constructor = ClassUtils.getConstructorIfAvailable(type, String.class);
                setValue(BeanUtils.instantiateClass(constructor, text));
            } else {
                setValue(ReflectionUtils.invokeMethod(method, null, text));
            }
        } catch (Exception e) {
            ValueObject annotation = type.getAnnotation(ValueObject.class);
            String errorCode = null == annotation ? null : annotation.value();

            throw new ValueObjectInstantiationException(String.format(
                    "Could not create an instance of value object %s!", type), e, errorCode);
        }
    }
}
