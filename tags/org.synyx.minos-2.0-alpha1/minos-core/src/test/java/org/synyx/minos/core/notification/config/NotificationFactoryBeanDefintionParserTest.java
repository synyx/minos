package org.synyx.minos.core.notification.config;

import org.dom4j.dom.DOMElement;
import static org.easymock.classextension.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.synyx.minos.core.notification.MessageSourceNotificationFactory;
import org.synyx.minos.core.notification.SimpleMessageNotificationFactory;
import org.synyx.minos.core.notification.config.NotificationFactoryBeanDefintionParser;

import static org.synyx.minos.core.notification.config.NotificationFactoryBeanDefintionParser.*;

import org.w3c.dom.Element;

/**
 * Testing {@link NotificationFactoryBeanDefintionParser}
 *
 * @author David Linsin - linsin@synyx.de
 */
public class NotificationFactoryBeanDefintionParserTest {
    private NotificationFactoryBeanDefintionParser classUnderTest;
    private BeanDefinitionBuilder mockBeanDefinitionBuilder;
    private XmlReaderContext mockXmlReaderContext;
    private Object[] MOCKS;

    @Before
    public void setUp() {
        classUnderTest = new NotificationFactoryBeanDefintionParser();
        mockBeanDefinitionBuilder = createMock(BeanDefinitionBuilder.class);
        mockXmlReaderContext = createMock(XmlReaderContext.class);
        MOCKS = new Object[]{mockBeanDefinitionBuilder, mockXmlReaderContext};
    }

    @After
    public void tearDown() {
        reset(MOCKS);
    }

    @Test
    public void get_bean_class_with_message() {
        Element dummyElement = fillDummyElementWithMessage();
        assertEquals(SimpleMessageNotificationFactory.class, classUnderTest.getBeanClass(dummyElement));
    }

    @Test
    public void get_bean_class_with_key() {
        Element dummyElement = fillDummyElementWithKey();
        assertEquals(MessageSourceNotificationFactory.class, classUnderTest.getBeanClass(dummyElement));
    }

    private Element fillDummyElementWithKey() {
        Element element = new DOMElement("");
        element.setAttribute(ATTR_KEY, "minos.core.umt.new_pw");
        return element;
    }

    @Test
    public void doparse_with_message() {
        Element dummyElement = fillDummyElementWithMessage();
        expect(mockBeanDefinitionBuilder.addConstructorArgValue(dummyElement.getAttribute(ATTR_MESSAGE))).andReturn(mockBeanDefinitionBuilder);
        replay(MOCKS);
        classUnderTest.doParse(dummyElement, new ParserContext(null, null), mockBeanDefinitionBuilder);
        verify(MOCKS);
    }

    @Test
    public void doparse_with_key() {
        Element dummyElement = fillDummyElementWithKey();
        expect(mockBeanDefinitionBuilder.addConstructorArgValue(dummyElement.getAttribute(ATTR_KEY))).andReturn(mockBeanDefinitionBuilder);
        replay(MOCKS);
        classUnderTest.doParse(dummyElement, new ParserContext(null, null), mockBeanDefinitionBuilder);
        verify(MOCKS);
    }

    @Test
    public void doparse_with_neither() {
        Element dummyElement = new DOMElement("");
        mockXmlReaderContext.error((String)anyObject(), anyObject());
        replay(MOCKS);
        classUnderTest.doParse(dummyElement, new ParserContext(mockXmlReaderContext, null), mockBeanDefinitionBuilder);
        verify(MOCKS);
    }

    @Test
    public void doparse_with_both() {
        Element dummyElement = fillDummyElementWithKey();
        dummyElement.setAttribute(ATTR_MESSAGE, "User %s has password %s now!");
        mockXmlReaderContext.error((String)anyObject(), anyObject());
        replay(MOCKS);
        classUnderTest.doParse(dummyElement, new ParserContext(mockXmlReaderContext, null), mockBeanDefinitionBuilder);
        verify(MOCKS);
    }

    private Element fillDummyElementWithMessage() {
        Element element = new DOMElement("");
        element.setAttribute(ATTR_MESSAGE, "User %s has password %s now!");
        return element;
    }
}