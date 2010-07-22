package org.synyx.minos.core.remoting.rest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.internal.MinosModule;
import org.synyx.minos.umt.remoting.rest.dto.UserDto;


/**
 * Unit test for {@link ModuleAwareMarshallerAdapter}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ModuleAwareMarshallerAdapterUnitTest {

    private ModuleAwareMarshallerAdapter adapter;

    @Mock
    private UnMarshaller unmarshaller;
    private Module module;


    @Before
    public void setUp() {

        module = new MinosModule("umt");

        adapter = new ModuleAwareMarshallerAdapter(unmarshaller, module);
    }


    @Test(expected = IllegalArgumentException.class)
    public void preventsNullMarshaller() throws Exception {

        new ModuleAwareMarshallerAdapter((UnMarshaller) null, module);
    }


    @Test(expected = IllegalArgumentException.class)
    public void preventsNullModule() throws Exception {

        new ModuleAwareMarshallerAdapter(unmarshaller, null);
    }


    @Test
    public void invokesDelegateMarshaller() throws Exception {

        adapter.marshal(null, null);

        verify(unmarshaller, times(1)).marshal(null, null);
    }


    @Test
    public void invokesDelegateUnmarshaller() throws Exception {

        adapter.unmarshal(null);

        verify(unmarshaller, times(1)).unmarshal(null);
    }


    @Test
    public void supportsClassIfMarshallerAndModuleSupport() throws Exception {

        when(unmarshaller.supports(UserDto.class)).thenReturn(true);

        assertTrue(adapter.supports(UserDto.class));
    }


    @Test
    public void doesNotSupportClassIfUnMarshallerDoesNot() throws Exception {

        when(unmarshaller.supports(UserDto.class)).thenReturn(false);

        assertFalse(adapter.supports(UserDto.class));
    }


    @Test
    public void doesNotSupportClassIfNotFromModule() throws Exception {

        when(unmarshaller.supports(UserDto.class)).thenReturn(true);

        Marshaller adapter = new ModuleAwareMarshallerAdapter(unmarshaller, new MinosModule("core"));

        assertFalse(adapter.supports(UserDto.class));
    }

    private interface UnMarshaller extends Marshaller, Unmarshaller {

    }
}
