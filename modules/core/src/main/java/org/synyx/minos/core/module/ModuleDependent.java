package org.synyx.minos.core.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.synyx.minos.core.module.support.ModulePostProcessor;


/**
 * Marks a type as module decorator and defines required modules. Use this in
 * combination with {@link ModulePostProcessor} to prevent decorators being
 * instantiated although they are declared in a <code>BeanFactory</code>.
 * 
 * @author Oliver Gierke
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleDependent {

    String[] value();
}
