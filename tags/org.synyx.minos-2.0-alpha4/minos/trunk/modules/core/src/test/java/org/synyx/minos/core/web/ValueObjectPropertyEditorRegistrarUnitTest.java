package org.synyx.minos.core.web;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.synyx.minos.core.domain.EmailAddress;


/**
 * Unit test for {@link ValueObjectPropertyEditor}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ValueObjectPropertyEditorRegistrarUnitTest {

    @Test
    public void registersPropertyEditorForEmailAddress() throws Exception {

        PropertyEditorRegistrar registrar =
                new ValueObjectPropertyEditorRegistrar(
                        "org.synyx.minos.core.domain");
        PropertyEditorRegistry registry = mock(PropertyEditorRegistry.class);

        registrar.registerCustomEditors(registry);

        verify(registry).registerCustomEditor(eq(EmailAddress.class),
                isA(ValueObjectPropertyEditor.class));
    }
}
