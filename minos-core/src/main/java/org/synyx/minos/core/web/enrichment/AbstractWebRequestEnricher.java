package org.synyx.minos.core.web.enrichment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;


/**
 * Basic implementation of {@link WebRequestEnricher}. Allows activation by
 * providing a path pattern. Provides empty implementations of
 * {@link WebRequestEnricher} methods to allow dedicated overriding of certain
 * methods.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractWebRequestEnricher implements WebRequestEnricher {

    private PathMatcher matcher = new AntPathMatcher();


    /**
     * Setter to inject a {@link PathMatcher} to resolve path patterns. Defaults
     * to an {@link AntPathMatcher}.
     * 
     * @param matcher the matcher to set
     */
    public void setMatcher(PathMatcher matcher) {

        this.matcher = matcher;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.web.enrichment.WebRequestEnricher#preHandle(javax
     * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return true;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.web.enrichment.WebRequestEnricher#postHandle(javax
     * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, ModelAndView modelAndView)
            throws Exception {

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hera.core.Plugin#supports(java.lang.Object)
     */
    @Override
    public boolean supports(HttpServletRequest request) {

        return matcher.match(getPathPattern(), request.getRequestURI());
    }


    /**
     * Returns the URL path pattern the enricher is interested in.
     * 
     * @return
     */
    protected abstract String getPathPattern();
}
