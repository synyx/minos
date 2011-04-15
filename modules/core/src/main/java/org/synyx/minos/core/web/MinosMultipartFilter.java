package org.synyx.minos.core.web;

import org.springframework.beans.factory.BeanFactory;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * Custom {@link MultipartFilter} to lookup the {@link MultipartResolver} from the
 * {@link org.springframework.web.servlet.DispatcherServlet}'s
 * {@link org.springframework.web.context.WebApplicationContext} the request actually is targeted to. Looks up beans
 * named {@code multipartResolver} as suggested by the Spring reference documentation.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosMultipartFilter extends MultipartFilter {

    /**
     * Creates a new {@link MinosMultipartFilter}.
     */
    public MinosMultipartFilter() {

        setMultipartResolverBeanName("multipartResolver");
    }

    @Override
    protected MultipartResolver lookupMultipartResolver(HttpServletRequest request) {

        BeanFactory context = RequestContextUtils.getWebApplicationContext(request);

        return context.getBean(getMultipartResolverBeanName(), MultipartResolver.class);
    }
}
