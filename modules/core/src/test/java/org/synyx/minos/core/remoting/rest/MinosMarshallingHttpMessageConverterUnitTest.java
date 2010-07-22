package org.synyx.minos.core.remoting.rest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.xml.transform.Result;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.synyx.hera.core.PluginRegistry;
import org.synyx.minos.umt.remoting.rest.dto.UserDto;
import org.synyx.minos.umt.web.UserForm;


/**
 * Unit test for {@link MinosMarshallingHttpMessageConverter}. TODO: add tests for
 * {@link MinosMarshallingHttpMessageConverter#readFromSource(Class, org.springframework.http.HttpHeaders, javax.xml.transform.Source)}
 * .
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class MinosMarshallingHttpMessageConverterUnitTest {

    private MinosMarshallingHttpMessageConverter converter;

    @Mock
    private ModuleAwareMarshaller marshaller;
    @Mock
    private PluginRegistry<ModuleAwareMarshaller, Class<?>> marshallers;


    @Before
    public void setUp() {

        this.converter = new MinosMarshallingHttpMessageConverter(marshallers);
    }


    @Test
    public void doesNotSupportMarshallingIfNoMarshallerDoes() throws Exception {

        when(marshallers.hasPluginFor(UserForm.class)).thenReturn(false);

        assertFalse(converter.supports(UserForm.class));
    }


    @Test
    public void invokesMarshallerCorrectly() throws Exception {

        when(marshallers.getPluginFor(eq(UserDto.class), any(Exception.class))).thenReturn(marshaller);

        UserDto dto = new UserDto();
        Result result = mock(Result.class);

        converter.writeToResult(dto, new HttpHeaders(), result);

        verify(marshaller, times(1)).marshal(dto, result);
    }
}
