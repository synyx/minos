package org.synyx.minos.core.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;


/**
 * Custom {@link WebBindingInitializer} that binds an
 * {@link IllegalToNullPropertyEditor} to all configured property-type-mappings.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosWebBindingIntializer extends
        ConfigurableWebBindingInitializer {

    private Map<String, Set<Class<?>>> params =
            new HashMap<String, Set<Class<?>>>();


    /**
     * Setter to configure which properties of which types shall be mapped with
     * an {@link IllegalToNullPropertyEditor}.
     * 
     * @param parameters
     */
    public void setParameters(Map<String, Set<Class<?>>> parameters) {

        this.params = parameters;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.bind.support.ConfigurableWebBindingInitializer
     * #initBinder(org.springframework.web.bind.WebDataBinder,
     * org.springframework.web.context.request.WebRequest)
     */
    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {

        super.initBinder(binder, request);

        for (Map.Entry<String, Set<Class<?>>> entry : params.entrySet()) {

            for (Class<?> type : entry.getValue()) {

                String property = entry.getKey();

                binder
                        .registerCustomEditor(
                                type,
                                property,
                                new IllegalToNullPropertyEditor(type,
                                        ArrayUtils.contains(binder
                                                .getRequiredFields(), property)));
            }
        }
    }
}
