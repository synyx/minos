package org.synyx.minos.core.module.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.synyx.minos.util.Assert;
import org.w3c.dom.Element;


/**
 * Utility methods to ease creating {@link BeanDefinition}s within {@link BeanDefinitionParser} implementations.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class BeanDefinitionParserUtils {

    /**
     * Adds a property value to the given {@link BeanDefinitionBuilder} if the given {@link Element} contains a value at
     * the attribute with the given name.
     * 
     * @param builder
     * @param propertyName
     * @param element
     * @param xmlAttribute
     */
    public static void addPropertyValue(BeanDefinitionBuilder builder, String propertyName, Element element,
            String xmlAttribute) {

        String value = element.getAttribute(xmlAttribute);

        if (StringUtils.hasText(value)) {
            builder.addPropertyValue(propertyName, value);
        }
    }


    /**
     * Adds a property reference to the given {@link BeanDefinitionBuilder} if the given {@link Element} contains a
     * value at the attribute with the given name.
     * 
     * @param builder
     * @param propertyName
     * @param element
     * @param xmlAttribute
     */
    public static void addPropertyReference(BeanDefinitionBuilder builder, String propertyName, Element element,
            String xmlAttribute) {

        String reference = element.getAttribute(xmlAttribute);

        if (StringUtils.hasText(reference)) {
            builder.addPropertyReference(propertyName, reference);
        }
    }


    /**
     * Adds the value or reference of the given {@link PropertyAttribute} to the given {@link BeanDefinitionBuilder} if
     * it contains a value inside the given {@link Element}.
     * 
     * @param builder
     * @param element
     * @param xmlAttribute
     */
    public static void addPropertyIfSet(BeanDefinitionBuilder builder, Element element, PropertyAttribute xmlAttribute) {

        Assert.notNull(builder);
        Assert.notNull(element);
        Assert.notNull(xmlAttribute);

        String value = element.getAttribute(xmlAttribute.toString());

        if (!StringUtils.hasText(value)) {
            return;
        }

        String property = xmlAttribute.asCamelCase();

        if (xmlAttribute.isReference()) {
            builder.addPropertyReference(property, value);
        } else {
            builder.addPropertyValue(property, value);
        }
    }
}
