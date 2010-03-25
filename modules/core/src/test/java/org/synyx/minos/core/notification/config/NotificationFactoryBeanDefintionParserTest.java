package org.synyx.minos.core.notification.config;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.core.notification.config.NotificationFactoryBeanDefintionParser.*;

import org.dom4j.dom.DOMElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.synyx.minos.core.notification.MessageSourceNotificationFactory;
import org.synyx.minos.core.notification.SimpleMessageNotificationFactory;
import org.w3c.dom.Element;


/**
 * Testing {@link NotificationFactoryBeanDefintionParser}
 * 
 * @author David Linsin - linsin@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationFactoryBeanDefintionParserTest {

    private NotificationFactoryBeanDefintionParser classUnderTest;

    @Mock
    private BeanDefinitionBuilder mockBeanDefinitionBuilder;

    @Mock
    private XmlReaderContext mockXmlReaderContext;


    @Before
    public void setUp() {

        classUnderTest = new NotificationFactoryBeanDefintionParser();
    }


    @Test
    public void get_bean_class_with_message() {

        Element dummyElement = fillDummyElementWithMessage();

        assertEquals(SimpleMessageNotificationFactory.class, classUnderTest
                .getBeanClass(dummyElement));
    }


    @Test
    public void get_bean_class_with_key() {

        Element dummyElement = fillDummyElementWithKey();

        assertEquals(MessageSourceNotificationFactory.class, classUnderTest
                .getBeanClass(dummyElement));
    }


    private Element fillDummyElementWithKey() {

        Element element = new DOMElement("");
        element.setAttribute(ATTR_KEY, "minos.core.umt.new_pw");
        return element;
    }


    @Test
    public void doparse_with_message() {

        Element dummyElement = fillDummyElementWithMessage();

        classUnderTest.doParse(dummyElement, new ParserContext(null, null),
                mockBeanDefinitionBuilder);

        verify(mockBeanDefinitionBuilder).addConstructorArgValue(
                dummyElement.getAttribute(ATTR_MESSAGE));
    }


    @Test
    public void doparse_with_key() {

        Element dummyElement = fillDummyElementWithKey();

        classUnderTest.doParse(dummyElement, new ParserContext(null, null),
                mockBeanDefinitionBuilder);

        verify(mockBeanDefinitionBuilder).addConstructorArgValue(
                dummyElement.getAttribute(ATTR_KEY));
    }


    @Test
    public void doparse_with_neither() {

        Element dummyElement = new DOMElement("");

        classUnderTest.doParse(dummyElement, new ParserContext(
                mockXmlReaderContext, null), mockBeanDefinitionBuilder);

        verify(mockXmlReaderContext).error((String) anyObject(), anyObject());
    }


    @Test
    public void doparse_with_both() {

        Element dummyElement = fillDummyElementWithKey();
        dummyElement.setAttribute(ATTR_MESSAGE, "User %s has password %s now!");

        classUnderTest.doParse(dummyElement, new ParserContext(
                mockXmlReaderContext, null), mockBeanDefinitionBuilder);

        verify(mockXmlReaderContext).error((String) anyObject(), anyObject());
    }


    private Element fillDummyElementWithMessage() {

        Element element = new DOMElement("");
        element.setAttribute(ATTR_MESSAGE, "User %s has password %s now!");
        return element;
    }
}