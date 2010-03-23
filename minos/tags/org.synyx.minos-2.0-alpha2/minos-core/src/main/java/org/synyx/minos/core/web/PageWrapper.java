package org.synyx.minos.core.web;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.synyx.hades.domain.Order;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Sort;
import org.synyx.hades.domain.Sort.Property;


/**
 * Adaptor for a {@link Page} object to work with Displaytag tag library.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class PageWrapper<T> implements PaginatedList {

    public static final SortOrderEnum DEFAULT_ORDER = SortOrderEnum.ASCENDING;

    private Page<T> page;


    /**
     * Creates a new wrapper for a {@link Page} to be accessible by the
     * Displaytag tag library.
     * 
     * @param page
     */
    private PageWrapper(Page<T> page) {

        this.page = page;
    }


    /**
     * Wraps the given {@link Page} into a {@link PageWrapper}.
     * 
     * @param <T>
     * @param page
     * @return
     */
    public static <T> PageWrapper<T> wrap(Page<T> page) {

        return new PageWrapper<T>(page);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.pagination.PaginatedList#getFullListSize()
     */
    public int getFullListSize() {

        return Long.valueOf(page.getTotalElements()).intValue();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.pagination.PaginatedList#getList()
     */
    public List<?> getList() {

        return page.asList();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.pagination.PaginatedList#getObjectsPerPage()
     */
    public int getObjectsPerPage() {

        return page.getSize();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.pagination.PaginatedList#getPageNumber()
     */
    public int getPageNumber() {

        return page.getNumber() + 1;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.pagination.PaginatedList#getSearchId()
     */
    public String getSearchId() {

        return null;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.pagination.PaginatedList#getSortCriterion()
     */
    public String getSortCriterion() {

        Sort sort = page.getSort();

        if (null == sort) {
            return null;
        }

        Property property = sort.iterator().next();

        return property.getName();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.pagination.PaginatedList#getSortDirection()
     */
    public SortOrderEnum getSortDirection() {

        Sort sort = page.getSort();

        if (null == sort) {
            return DEFAULT_ORDER;
        }

        Property property = sort.iterator().next();

        return Order.ASCENDING == property.getOrder() ? SortOrderEnum.ASCENDING
                : SortOrderEnum.DESCENDING;
    }
}