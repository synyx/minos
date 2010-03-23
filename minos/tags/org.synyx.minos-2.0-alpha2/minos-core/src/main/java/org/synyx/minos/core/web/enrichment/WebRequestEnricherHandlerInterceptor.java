package org.synyx.minos.core.web.enrichment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.synyx.hera.core.PluginRegistry;
import org.synyx.hera.core.SimplePluginRegistry;


/**
 * Handler interceptor that allows registration of {@link WebRequestEnricher}s
 * that are executed if they support the current {@link HttpServletRequest}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class WebRequestEnricherHandlerInterceptor extends
        HandlerInterceptorAdapter {

    private PluginRegistry<WebRequestEnricher, HttpServletRequest> enrichers =
            SimplePluginRegistry.create();


    /**
     * Setter to inject {@link WebRequestEnricher}s.
     * 
     * @param enrichers the enrichers to set
     */
    public void setEnrichers(
            PluginRegistry<WebRequestEnricher, HttpServletRequest> enrichers) {

        this.enrichers = enrichers;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet
     * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        for (WebRequestEnricher enricher : enrichers.getPluginsFor(request)) {
            if (!enricher.preHandle(request, response)) {
                return false;
            }
        }

        return true;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet
     * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        for (WebRequestEnricher enricher : enrichers.getPluginsFor(request)) {
            enricher.postHandle(request, response, modelAndView);
        }
    }
}