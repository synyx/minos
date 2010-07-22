package org.synyx.minos.core.web;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.synyx.minos.core.domain.AbstractNamedEntity;


/**
 * Unit test for {@link ValueObjectPropertyEditor}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ValueObjectPropertyEditorUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void rejectsTypesWithoutStringConstructor() throws Exception {

        new ValueObjectPropertyEditor(ValueObjectPropertyEditor.class);
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsVoidFactoryMethod() throws Exception {

        new ValueObjectPropertyEditor(ValueObject.class, "invalidFactoryMethod");
    }


    @Test(expected = IllegalArgumentException.class)
    public void rejectsNonStaticFactoryMethod() throws Exception {

        new ValueObjectPropertyEditor(ValueObject.class, "setValue");
    }


    @Test
    public void createEditorForFactoryMethod() throws Exception {

        ValueObjectPropertyEditor editor = new ValueObjectPropertyEditor(ValueObject.class, "fromValue");

        assertFooValueObject(editor);
    }


    @Test
    public void bindsStringValueCorrectly() throws Exception {

        ValueObjectPropertyEditor editor = new ValueObjectPropertyEditor(ValueObject.class);

        assertFooValueObject(editor);
    }


    @Test
    public void testname() throws Exception {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("value", "fooValue");

        TestClass target = new TestClass();
        prepareBinder(ValueObject.class, properties, target);

        assertEquals(new ValueObject("fooValue"), target.value);
    }


    @Test
    public void binderCreatesErrorOnRuntimeException() throws Exception {

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("exceptionValue", "fooValue");

        DataBinder binder = prepareBinder(ExceptionValueObject.class, properties, new TestClass());

        BindingResult result = binder.getBindingResult();
        assertTrue(result.hasFieldErrors("exceptionValue"));
    }


    private DataBinder prepareBinder(Class<?> valueObjectClazz, Map<String, String> properties, Object target) {

        WebDataBinder binder = new WebDataBinder(target, "foo");
        binder.registerCustomEditor(valueObjectClazz, new ValueObjectPropertyEditor(valueObjectClazz));

        PropertyValues values = new MutablePropertyValues(properties);
        binder.bind(values);

        return binder;
    }


    /**
     * Asserts that setting a particular {@link String} on the given editor results in a {@link ValueObject} wrapped
     * around.
     * 
     * @param editor
     */
    private void assertFooValueObject(ValueObjectPropertyEditor editor) {

        editor.setAsText("foo");

        assertTrue(editor.getValue() instanceof ValueObject);
        assertEquals("foo", ((ValueObject) editor.getValue()).value);
    }

    /**
     * Sample value object class.
     * 
     * @author Oliver Gierke - gierke@synyx.de
     */
    public static class ValueObject extends AbstractNamedEntity {

        private String value;


        public ValueObject(String value) {

            this.value = value;
        }


        /**
         * No static method that satisfies all other requirements.
         * 
         * @param value
         * @return
         */
        public ValueObject setValue(String value) {

            this.value = value;
            return this;
        }


        /**
         * Correct factory method.
         * 
         * @param value
         * @return
         */
        public static ValueObject fromValue(String value) {

            return new ValueObject(value);
        }


        /**
         * Void factory method to be rejected.
         * 
         * @param value
         */
        public static void invalidFactoryMethod(String value) {

        }


        /*
         * (non-Javadoc)
         * 
         * @see org.synyx.minos.core.domain.AbstractNamedEntity#getValue()
         */
        @Override
        protected String getValue() {

            return value;
        }
    }

    public static class ExceptionValueObject {

        public ExceptionValueObject(String value) {

            throw new IllegalArgumentException(value);
        }
    }

    static class TestClass {

        private ValueObject value;


        public void setValue(ValueObject value) {

            this.value = value;
        }


        public void setExceptionValue(ExceptionValueObject exValue) {

        }
    }
}
