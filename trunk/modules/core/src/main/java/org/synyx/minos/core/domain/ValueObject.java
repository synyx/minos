package org.synyx.minos.core.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to designate value objects. Classes marked with this annotation might be treated in a special way as they
 * are typically immutable.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueObject {

    /**
     * Defines an error code to be appended when binding fails. Useful if you want to reject certain {@link String}s and
     * have control over the error code showed in the UI.
     *
     * @return an error code
     */
    String value() default "";
}
