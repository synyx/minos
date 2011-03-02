package org.synyx.minos.core.notification.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;

import org.springframework.util.StringUtils;

import org.synyx.minos.core.notification.MessageSourceNotificationFactory;
import org.synyx.minos.core.notification.SimpleMessageNotificationFactory;

import org.w3c.dom.Element;


/**
 * Initializes a {@link org.synyx.minos.core.notification.Notification} implementation based on attributes specified in
 * XML
 *
 * @author David Linsin - linsin@synyx.de
 */
public class NotificationFactoryBeanDefintionParser extends AbstractSingleBeanDefinitionParser {

    static final String ATTR_MESSAGE = "message";
    static final String ATTR_KEY = "key";

    /**
     * @param argElement {@link Element} which is used to retrieve attributes
     * @return Class which is a {@link org.synyx.minos.core.notification.Notification} implementation
     * @see AbstractSingleBeanDefinitionParser#getBeanClass(org.w3c.dom.Element)
     */
    @Override
    protected Class<?> getBeanClass(Element argElement) {

        String key = argElement.getAttribute(ATTR_KEY);

        if (StringUtils.hasText(key)) {
            return MessageSourceNotificationFactory.class;
        } else {
            return SimpleMessageNotificationFactory.class;
        }
    }


    /**
     * @param element {@link Element} which is used to retrieve attributes
     * @param parserContext {@link ParserContext} used to attach errors
     * @param builder {@link BeanDefinitionBuilder } filled with attributes
     * @see AbstractSingleBeanDefinitionParser#doParse(Element, BeanDefinitionBuilder)
     */
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {

        if (isValid(element, parserContext)) {
            String message = element.getAttribute(ATTR_MESSAGE);
            String key = element.getAttribute(ATTR_KEY);

            if (StringUtils.hasText(key)) {
                builder.addConstructorArgValue(key);
            } else {
                builder.addConstructorArgValue(message);
            }
        }
    }


    private boolean isValid(Element argElement, ParserContext argParserContext) {

        String message = argElement.getAttribute(ATTR_MESSAGE);
        String key = argElement.getAttribute(ATTR_KEY);

        if (StringUtils.hasText(message) && StringUtils.hasText(key)) {
            argParserContext.getReaderContext().error(String.format(
                    "Error creating bean: don't specify \"%1$s\" and \"%2$s\" at the same time!", ATTR_KEY,
                    ATTR_MESSAGE), argElement);

            return false;
        } else if (!StringUtils.hasText(message) && !StringUtils.hasText(key)) {
            argParserContext.getReaderContext().error(String.format(
                    "Error creating bean: you must specify either \"%1$s\" or \"%2$s\"", ATTR_KEY, ATTR_MESSAGE),
                argElement);

            return false;
        }

        return true;
    }
}
