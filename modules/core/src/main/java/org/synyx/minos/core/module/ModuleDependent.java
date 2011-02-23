package org.synyx.minos.core.module;

import org.synyx.minos.core.module.support.ModulePostProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Marks a type as a module decorator and defines required modules. Use this in combination with
 * {@link ModulePostProcessor} to prevent decorators being instantiated although they are declared in a <code>
 * BeanFactory</code>.
 *
 * @author  Oliver Gierke
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleDependent {

    /**
     * The list of required modules for this module decorator.
     * @return list of required modules for this module decorator
     */
    String[] value();
}
