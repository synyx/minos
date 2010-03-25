package org.synyx.minos.core.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.synyx.hades.domain.Order.*;

import java.util.Arrays;

import org.displaytag.properties.SortOrderEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.synyx.hades.domain.Order;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Sort;
import org.synyx.hades.domain.Sort.Property;


/**
 * Unit test for {@link PageWrapper}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class PageWrapperUnitTest {

    @Mock
    private Page<String> page;
    private PageWrapper<String> wrapper;


    @Before
    public void setUp() {

        wrapper = PageWrapper.wrap(page);
    }


    @Test
    public void returnsFirstCriterionIfMultipleAvailable() throws Exception {

        assertSort(new Sort(Order.ASCENDING, "name", "firstname"));
        assertThat(wrapper.getSortCriterion(), is("name"));
    }


    @Test
    public void returnsFirstOrderIfMultipleAvailable() throws Exception {

        assertSort(new Sort(Arrays.asList(new Property(DESCENDING, "name"),
                new Property(ASCENDING, "firstname"))));
        assertThat(wrapper.getSortDirection(), is(SortOrderEnum.DESCENDING));
    }


    @Test
    public void returnsNullForCriterionIfNoneAvailable() throws Exception {

        assertSort(null);

        assertThat(wrapper.getSortCriterion(), is(nullValue()));
        assertThat(wrapper.getSortDirection(), is(PageWrapper.DEFAULT_ORDER));
    }


    private void assertSort(Sort sort) {

        when(page.getSort()).thenReturn(sort);
    }
}
