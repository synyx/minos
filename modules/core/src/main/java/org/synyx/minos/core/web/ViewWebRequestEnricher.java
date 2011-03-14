package org.synyx.minos.core.web;

import org.springframework.web.servlet.ModelAndView;
import org.synyx.minos.core.web.enrichment.WebRequestEnricher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * {@link WebRequestEnricher} that adds the view name that will be displayed to the request so that
 * {@link SitemeshDecoratorMapper} can resolve its view based on this information.
 * 
 * @author Marc Kannegiesser, kannegiesser@synyx.de
 */
public class ViewWebRequestEnricher implements WebRequestEnricher {

    public static final String VIEWNAME_ATTRIBUTE = "ViewWebRequestEnricher_viewName";


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView)
            throws Exception {

        if (modelAndView != null) {
            String viewName = modelAndView.getViewName();
            request.setAttribute(VIEWNAME_ATTRIBUTE, viewName);
        }
    }


    @Override
    public boolean supports(HttpServletRequest request) {

        return true;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return true;
    }

}
