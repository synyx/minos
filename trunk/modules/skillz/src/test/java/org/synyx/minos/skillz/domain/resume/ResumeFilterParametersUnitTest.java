package org.synyx.minos.skillz.domain.resume;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.synyx.minos.skillz.domain.resume.ResumeFilterParameters.Builder;


/**
 * Unit test for {@link ResumeFilterParameters}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ResumeFilterParametersUnitTest {

    @Mock
    private ConversionService conversionService;


    @Before
    public void setUp() {

        when(conversionService.convert(isA(String.class), eq(Long.class)))
                .thenReturn(1L);
    }


    @Test
    public void convertsValuesCorrectly() throws Exception {

        ResumeFilterParameters parameters =
                new Builder().add("foo", String.class).add("bar", Long.class)
                        .build();

        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("foo", new String[] { "fooValue" });
        params.put("bar", new String[] { "barValue" });

        Map<String, Object> result =
                parameters.getTypedParameters(params, conversionService);

        Object value = result.get("bar");
        assertThat(value, is(instanceOf(Long.class)));
        assertThat((Long) value, is(1L));
    }
}
