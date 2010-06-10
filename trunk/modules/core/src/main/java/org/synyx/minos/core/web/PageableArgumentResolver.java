package org.synyx.minos.core.web;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.synyx.hades.domain.Order;
import org.synyx.hades.domain.PageRequest;
import org.synyx.hades.domain.Pageable;
import org.synyx.hades.domain.Sort;


/**
 * Extracts paging information from web requests and thus allows injecting
 * {@link Pageable} instances into controller methods. Request properties to be
 * parsed can be configured. Default configuration uses request properties
 * beginning with {@link #DEFAULT_PREFIX}{@link #DEFAULT_SEPARATOR}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class PageableArgumentResolver implements WebArgumentResolver {

    private static final Pageable DEFAULT_PAGE_REQUEST = new PageRequest(0, 10);
    private static final String DEFAULT_PREFIX = "page";
    private static final String DEFAULT_SEPARATOR = ".";

    private Pageable fallbackPagable = DEFAULT_PAGE_REQUEST;
    private String prefix = DEFAULT_PREFIX;
    private String separator = DEFAULT_SEPARATOR;


    /**
     * Setter to configure a fallback instance of {@link Pageable} that is being
     * used to back missing parameters. Defaults to
     * {@value #DEFAULT_PAGE_REQUEST}.
     * 
     * @param fallbackPagable the fallbackPagable to set
     */
    public void setFallbackPagable(Pageable fallbackPagable) {

        this.fallbackPagable =
                null == fallbackPagable ? DEFAULT_PAGE_REQUEST
                        : fallbackPagable;
    }


    /**
     * Setter to configure the prefix of request parameters to be used to
     * retrieve paging information. Defaults to {@link #DEFAULT_PREFIX}.
     * 
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {

        this.prefix = null == prefix ? DEFAULT_PREFIX : prefix;
    }


    /**
     * Setter to configure the separator between prefix and actual property
     * value. Defaults to {@link #DEFAULT_SEPARATOR}.
     * 
     * @param separator the separator to set
     */
    public void setSeparator(String separator) {

        this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.bind.support.WebArgumentResolver#resolveArgument
     * (org.springframework.core.MethodParameter,
     * org.springframework.web.context.request.NativeWebRequest)
     */
    public Object resolveArgument(MethodParameter methodParameter,
            NativeWebRequest webRequest) throws Exception {

        if (methodParameter.getParameterType().equals(Pageable.class)) {

            assertPageableUniqueness(methodParameter);

            Pageable request =
                    getDefaultFromAnnotationOrFallback(methodParameter);

            ServletRequest servletRequest =
                    (ServletRequest) webRequest.getNativeRequest();

            PropertyValues propertyValues =
                    new ServletRequestParameterPropertyValues(servletRequest,
                            getPrefix(methodParameter), separator);

            DataBinder binder = new ServletRequestDataBinder(request);

            binder.initDirectFieldAccess();
            binder.registerCustomEditor(Sort.class, new SortPropertyEditor(
                    "sort.dir", propertyValues));
            binder.bind(propertyValues);

            if (request.getPageNumber() > 0) {

                request =
                        new PageRequest(request.getPageNumber() - 1, request
                                .getPageSize(), request.getSort());
            }

            return request;
        }

        return UNRESOLVED;
    }


    private Pageable getDefaultFromAnnotationOrFallback(
            MethodParameter methodParameter) {

        // search for PageableDefaults annotation
        for (Annotation annotation : methodParameter.getParameterAnnotations()) {
            if (annotation instanceof PageableDefaults) {
                PageableDefaults defaults = (PageableDefaults) annotation;
                // +1 is because we substract 1 later
                return new PageRequest(defaults.pageNumber() + 1, defaults
                        .value());
            }
        }

        // Construct request with fallback request to ensure sensible
        // default values. Create fresh copy as Spring will manipulate the
        // instance under the covers
        return new PageRequest(fallbackPagable.getPageNumber(), fallbackPagable
                .getPageSize(), fallbackPagable.getSort());

    }


    /**
     * Resolves the prefix to use to bind properties from. Will prepend a
     * possible {@link Qualifier} if available or return the configured prefix
     * otherwise.
     * 
     * @param parameter
     * @return
     */
    private String getPrefix(MethodParameter parameter) {

        for (Annotation annotation : parameter.getParameterAnnotations()) {

            if (annotation instanceof Qualifier) {

                return new StringBuilder(((Qualifier) annotation).value())
                        .append("_").append(prefix).toString();
            }
        }

        return prefix;
    }


    /**
     * Asserts uniqueness of all {@link Pageable} parameters of the method of
     * the given {@link MethodParameter}.
     * 
     * @param parameter
     */
    private void assertPageableUniqueness(MethodParameter parameter) {

        Method method = parameter.getMethod();

        Class<?>[] parameterTypes = method.getParameterTypes();

        if (CollectionUtils.cardinality(Pageable.class, Arrays
                .asList(parameterTypes)) > 1) {

            Annotation[][] annotations = method.getParameterAnnotations();
            assertQualifiersFor(parameterTypes, annotations);
        }
    }


    /**
     * Asserts that every {@link Pageable} parameter of the given parameters
     * carries an {@link Qualifier} annotation to distinguish them from each
     * other.
     * 
     * @param parameterTypes
     * @param annotations
     */
    private void assertQualifiersFor(Class<?>[] parameterTypes,
            Annotation[][] annotations) {

        Set<String> values = new HashSet<String>();

        for (int i = 0; i < annotations.length; i++) {

            if (Pageable.class.equals(parameterTypes[i])) {

                Qualifier qualifier = findAnnotation(annotations[i]);

                if (null == qualifier) {
                    throw new IllegalStateException(
                            "Ambiguous Pageable arguments in handler method. If you use multiple parameters of type Pageable you need to qualify them with @Qualifier");
                }

                if (values.contains(qualifier.value())) {
                    throw new IllegalStateException(
                            "Values of the user Qualifiers must be unique!");
                }

                values.add(qualifier.value());
            }
        }
    }


    /**
     * Returns a {@link Qualifier} annotation from the given array of
     * {@link Annotation}s. Returns {@literal null} if the array does not
     * contain a {@link Qualifier} annotation.
     * 
     * @param annotations
     * @return
     */
    private Qualifier findAnnotation(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            if (annotation instanceof Qualifier) {
                return (Qualifier) annotation;
            }
        }

        return null;
    }

    /**
     * {@link PropertyEditor} to create {@link Sort} instances from textual
     * representations. The implementation interprets the string as a comma
     * separated list where the first entry is the sort direction ({@code asc},
     * {@code desc}) followed by the properties to sort by.
     * 
     * @author Oliver Gierke - gierke@synyx.de
     */
    private class SortPropertyEditor extends PropertyEditorSupport {

        private String orderProperty;
        private PropertyValues values;


        /**
         * Creates a new {@link SortPropertyEditor}.
         * 
         * @param orderProperty
         * @param values
         */
        public SortPropertyEditor(String orderProperty, PropertyValues values) {

            this.orderProperty = orderProperty;
            this.values = values;
        }


        /*
         * (non-Javadoc)
         * 
         * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
         */
        @Override
        public void setAsText(String text) throws IllegalArgumentException {

            PropertyValue rawOrder = values.getPropertyValue(orderProperty);
            Order order =
                    null == rawOrder ? Order.ASCENDING : Order
                            .fromJpaValue(rawOrder.getValue().toString());

            setValue(new Sort(order, text));
        }
    }
}
