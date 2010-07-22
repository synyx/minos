package org.synyx.minos.core.web.enrichment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.synyx.hera.core.Plugin;


/**
 * Inteface for classes enriching web requests. Using this facility one can enrich requests handled by controller
 * methods in case one wants to provide an extended view and therefore has to enrich the request or the calculated
 * model.
 * <p>
 * This one is heavily inspired by {@link HandlerInterceptor} but provides a slightly less technical API.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface WebRequestEnricher extends Plugin<HttpServletRequest> {

    /**
     * Invoked before the request is handed to the actual controller method.
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception;


    /**
     * Invoked after the controller method has done its work. Usually implementations will enrich or tweak the provided
     * model calculated by the original controller.
     * 
     * @param request
     * @param response
     * @param modelAndView
     * @throws Exception
     */
    void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView)
            throws Exception;
}
