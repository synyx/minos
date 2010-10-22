package org.synyx.minos.core.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.synyx.minos.core.domain.User;


/**
 * Annotation to get the currently authenticated {@link User} injected into web controller methods.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CurrentUser {

}
