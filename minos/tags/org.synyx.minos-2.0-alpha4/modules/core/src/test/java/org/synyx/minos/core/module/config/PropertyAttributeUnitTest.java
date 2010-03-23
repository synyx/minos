package org.synyx.minos.core.module.config;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Unit test for {@link PropertyAttribute}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class PropertyAttributeUnitTest {

    @Test
    public void transformsXmlNamesToCamelCaseCorrectly() throws Exception {

        assertThat(new PropertyAttribute("foo").asCamelCase(), is("foo"));
        assertThat(new PropertyAttribute("foo-bar").asCamelCase(), is("fooBar"));

        assertThat(new PropertyAttribute("foo-bar-ref").asCamelCase(),
                is("fooBar"));
    }


    @Test
    public void detectsReferencesCorrectly() throws Exception {

        assertTrue(new PropertyAttribute("foo-bar-ref").isReference());
    }
}
