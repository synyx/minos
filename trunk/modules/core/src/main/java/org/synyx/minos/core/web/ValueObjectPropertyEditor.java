package org.synyx.minos.core.web;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.synyx.minos.core.domain.ValueObject;
import org.synyx.minos.core.domain.ValueObjectInstantiationException;
import org.synyx.minos.util.Assert;


/**
 * {@link PropertyEditor} to create value objects. The classes to be bound with this {@link PropertyEditor} are required
 * to offer a constructor with a single {@link String} argument.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ValueObjectPropertyEditor extends PropertyEditorSupport {

    private final Class<?> type;
    private final Method method;


    /**
     * Creates a {@link ValueObjectPropertyEditor} for the given type. Expects a constructor with a single
     * {@link String} argument to be able to create the value objects from.
     * 
     * @param type
     */
    public ValueObjectPropertyEditor(Class<?> type) {

        Assert.isTrue(ClassUtils.hasConstructor(type, String.class),
                "The given type must have a constructor with a single String argument!");

        this.type = type;
        this.method = null;
    }


    public ValueObjectPropertyEditor(Class<?> type, String factoryMethodName) {

        Method method = ClassUtils.getMethodIfAvailable(type, factoryMethodName, String.class);

        Assert.notNull(method, String.format("Method %s does not exist!", factoryMethodName));
        Assert.isTrue(Modifier.isStatic(method.getModifiers()), String.format("Method %s is not static!",
                factoryMethodName));
        Assert.isTrue(!method.getReturnType().equals(void.class), "Method must not be void!");

        this.type = type;
        this.method = method;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {

        return null == getValue() ? "" : getValue().toString();
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

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
