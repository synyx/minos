package org.synyx.minos.core.module.internal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.ModuleAware;


/**
 * Unit test for {@link ModuleAwareComparator}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class ModuleAwareComparatorUnitTest {

    private ModuleAwareComparator comparator = new ModuleAwareComparator();

    @Mock
    private ModuleAware first;
    @Mock
    private ModuleAware second;

    private Module one = new MinosModule("foo");
    private Module two = new MinosModule("bar");


    @Test
    public void handlesNullForFirstParameter() throws Exception {

        when(second.getModule()).thenReturn(two);
        assertGreater(null, second);
    }


    @Test
    public void handlesNullForSecondParameter() throws Exception {

        when(first.getModule()).thenReturn(one);
        assertLess(first, null);
    }


    @Test
    public void handlesNullForFirstModule() throws Exception {

        when(first.getModule()).thenReturn(null);
        when(second.getModule()).thenReturn(two);

        assertGreater(first, second);
    }


    @Test
    public void handlesNullForSecondModule() throws Exception {

        when(first.getModule()).thenReturn(one);
        when(second.getModule()).thenReturn(null);

        assertLess(first, second);
    }


    @Test
    public void treatsTwoNullsAsEqual() throws Exception {

        when(first.getModule()).thenReturn(null);
        when(second.getModule()).thenReturn(null);

        assertEqual(first, second);
        assertEqual(null, null);
    }


    private void assertEqual(ModuleAware first, ModuleAware second) {

        assertThat(comparator.compare(first, second), is(0));
    }


    private void assertLess(ModuleAware first, ModuleAware second) {

        assertThat(comparator.compare(first, second), is(lessThan(0)));
    }


    private void assertGreater(ModuleAware first, ModuleAware second) {

        assertThat(comparator.compare(first, second), is(greaterThan(0)));
    }
}
