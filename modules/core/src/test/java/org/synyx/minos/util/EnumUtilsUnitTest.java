package org.synyx.minos.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.synyx.minos.util.EnumUtils;


/**
 * Unit test for {@link EnumUtils}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EnumUtilsUnitTest {

    /**
     * Asserts that the class rejects {@code null} values as default values.
     */
    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullValuesForDefaultValue() {

        SampleEnum defaultValue = null;
        EnumUtils.valueOf("foo", defaultValue);
    }


    /**
     * Asserts that the class parses correct enum values.
     */
    @Test
    public void parsesStandardEnum() {

        assertEquals(SampleEnum.ONE, EnumUtils.valueOf("ONE", SampleEnum.TWO));

    }


    /**
     * Asserts that the class can parse enum values from lowercase strings.
     */
    @Test
    public void parsesLowerCaseValuesCorrectly() {

        assertEquals(SampleEnum.ONE, EnumUtils.valueOf("one", SampleEnum.TWO));
    }


    /**
     * Asserts that the class returns the given default value if the value to parse is null.
     */
    @Test
    public void usesDefaultValueForNullValues() {

        assertEquals(SampleEnum.TWO, EnumUtils.valueOf(null, SampleEnum.TWO));
    }
}
