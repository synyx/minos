package org.synyx.minos.core.module.config;

import org.springframework.util.StringUtils;

import org.synyx.minos.util.Assert;


/**
 * Simple value object to capture XML attributes to be bound to Java bean properties. The class expects the name wrapped
 * to be in dash-style and allows determining the according Java bean property from it. Furthermore it will consider
 * names ending with {@value #REF} as references.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class PropertyAttribute {

    /** Postfix indicating a reference. */
    public static final String REF = "-ref";

    private final String name;

    /**
     * Creates a new {@link PropertyAttribute} with the given name.
     *
     * @param  name  a name for the property attribute
     */
    public PropertyAttribute(String name) {

        Assert.notNull(name);
        this.name = name;
    }

    /**
     * Returns whether the attribute represents a reference.
     *
     * @return  whether the attribute represents a reference
     */
    public boolean isReference() {

        return name.endsWith(REF);
    }


    /**
     * Returns the attribute in camel case form.
     *
     * @return  the attribute in camel case form
     */
    public String asCamelCase() {

        String source = isReference() ? name.substring(0, name.indexOf(REF)) : name;

        String[] parts = source.split("-");

        if (parts.length == 1) {
            return parts[0];
        }

        StringBuilder builder = new StringBuilder(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            builder.append(StringUtils.capitalize(parts[i]));
        }

        return builder.toString();
    }


    @Override
    public String toString() {

        return name;
    }
}
