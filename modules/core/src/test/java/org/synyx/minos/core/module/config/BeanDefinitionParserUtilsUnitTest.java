package org.synyx.minos.core.module.config;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.core.module.config.BeanDefinitionParserUtils.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;


/**
 * Unit tests for {@link BeanDefinitionParserUtils}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class BeanDefinitionParserUtilsUnitTest {

    private static final String VALUE = "foo";

    @Mock
    private Element element;
    @Mock
    private BeanDefinitionBuilder builder;
    @Mock
    private PropertyAttribute attribute;


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullBuilder() throws Exception {

        addPropertyIfSet(null, element, attribute);
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullElement() throws Exception {

        addPropertyIfSet(builder, null, attribute);
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullAttribute() throws Exception {

        addPropertyIfSet(builder, element, null);
    }


    @Test
    public void addsValueAsReferenceIfAttributeIsReference() throws Exception {

        when(attribute.isReference()).thenReturn(true);
        when(element.getAttribute(anyString())).thenReturn(VALUE);

        addPropertyIfSet(builder, element, attribute);

        verify(builder).addPropertyReference(anyString(), eq(VALUE));
    }


    @Test
    public void addsValueAsValueIfAttributeIsNotAReference() throws Exception {

        when(attribute.isReference()).thenReturn(false);
        when(element.getAttribute(anyString())).thenReturn(VALUE);

        addPropertyIfSet(builder, element, attribute);

        verify(builder).addPropertyValue(anyString(), eq(VALUE));
    }


    @Test
    public void doesNotSetValueOrReferenceWhenAttributeValueIsEmpty()
            throws Exception {

        assertNoPropertySetForAttributeValue(null);
        assertNoPropertySetForAttributeValue("");
        assertNoPropertySetForAttributeValue(" ");
    }


    private void assertNoPropertySetForAttributeValue(String value) {

        when(element.getAttribute(anyString())).thenReturn(value);

        addPropertyIfSet(builder, element, attribute);

        verify(builder, never()).addPropertyValue(anyString(), anyString());
        verify(builder, never()).addPropertyReference(anyString(), anyString());
    }
}
