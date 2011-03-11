package org.synyx.minos.core.web;

import org.displaytag.pagination.PaginatedList;

import org.displaytag.properties.SortOrderEnum;

import org.synyx.hades.domain.Order;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Sort;
import org.synyx.hades.domain.Sort.Property;

import java.util.List;


/**
 * Adaptor for a {@link Page} object to work with Displaytag tag library.
 *
 * @param  <T>  the type of objects in the page
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public final class PageWrapper<T> implements PaginatedList {

    /** By default page content is sorted in ascending order. */
    public static final SortOrderEnum DEFAULT_ORDER = SortOrderEnum.ASCENDING;

    private Page<T> page;

    /**
     * Creates a new wrapper for a {@link Page} to be accessible by the Displaytag tag library.
     *
     * @param  page  the wrapped page
     */
    private PageWrapper(Page<T> page) {

        this.page = page;
    }

    /**
     * Wraps the given {@link Page} into a {@link PageWrapper}.
     *
     * @param  page  a page
     *
     * @return  the wrapped page
     */
    public static <T> PageWrapper<T> wrap(Page<T> page) {

        return new PageWrapper<T>(page);
    }


    @Override
    public int getFullListSize() {

        return Long.valueOf(page.getTotalElements()).intValue();
    }


    @Override
    public List<?> getList() {

        return page.asList();
    }


    @Override
    public int getObjectsPerPage() {

        return page.getSize();
    }


    @Override
    public int getPageNumber() {

        return page.getNumber() + 1;
    }


    @Override
    public String getSearchId() {

        return null;
    }


    @Override
    public String getSortCriterion() {

        Sort sort = page.getSort();

        if (null == sort) {
            return null;
        }

        Property property = sort.iterator().next();

        return property.getName();
    }


    @Override
    public SortOrderEnum getSortDirection() {

        Sort sort = page.getSort();

        if (null == sort) {
            return DEFAULT_ORDER;
        }

        Property property = sort.iterator().next();

        return Order.ASCENDING == property.getOrder() ? SortOrderEnum.ASCENDING : SortOrderEnum.DESCENDING;
    }
}
