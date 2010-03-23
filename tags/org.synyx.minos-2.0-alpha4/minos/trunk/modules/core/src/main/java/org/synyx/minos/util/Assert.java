package org.synyx.minos.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.synyx.hades.domain.Persistable;


/**
 * Simple wrapper for Spring's {@link org.springframework.util.Assert} class to
 * not directly depend on it in client classes.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class Assert {

    /**
     * Private constructor to prevent instantiation.
     */
    private Assert() {

    }


    /**
     * @see org.springframework.util.Assert#notNull(Object)
     */
    public static void notNull(Object object) {

        org.springframework.util.Assert.notNull(object);
    }


    /**
     * @see org.springframework.util.Assert#notNull(Object, String)
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {

        org.springframework.util.Assert.notNull(object, message);
    }


    /**
     * @see org.springframework.util.Assert#notEmpty(Collection)
     * @param collection
     */
    public static void notEmpty(Collection<?> collection) {

        org.springframework.util.Assert.notEmpty(collection);
    }


    /**
     * @see StringUtils#isBlank(String)
     * @param collection
     */
    public static void notBlank(String string) {

        isFalse(StringUtils.isBlank(string));
    }


    /**
     * Returns whether the given expression is {@literal true}.
     * 
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {

        org.springframework.util.Assert.isTrue(expression, message);
    }


    /**
     * Asserts that the the given expression is {@literal false}.
     * 
     * @param expression
     */
    public static void isFalse(boolean expression) {

        org.springframework.util.Assert.isTrue(!expression);
    }


    /**
     * Returns whether the given {@link String} is not {@literal null} and has
     * text at all. Returns {@literal true} for whitespace only {@link String}s,
     * too.
     * 
     * @param text
     * @param message
     */
    public static void hasText(String text, String message) {

        org.springframework.util.Assert.hasText(text, message);
    }


    /**
     * Asserts that the given {@link Persistable} is not {@literal null} and not
     * new.
     * 
     * @param persistable
     */
    public static void notNew(Persistable<?> persistable) {

        org.springframework.util.Assert.notNull(persistable);
        org.springframework.util.Assert.isTrue(!persistable.isNew());
    }
}
