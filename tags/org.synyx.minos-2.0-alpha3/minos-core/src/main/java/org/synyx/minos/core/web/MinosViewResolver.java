package org.synyx.minos.core.web;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Custom {@link ViewResolver} that treats redirect views in a special way by
 * prepending the servlet path to redirects if the given target URL is neither
 * external nor already starts with the servlet path.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosViewResolver extends InternalResourceViewResolver {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.view.UrlBasedViewResolver#createView(
     * java.lang.String, java.util.Locale)
     */
    @Override
    protected View createView(String viewName, Locale locale) throws Exception {

        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            String redirectUrl =
                    viewName.substring(REDIRECT_URL_PREFIX.length());
            return new ServletMappingAwareRedirectView(redirectUrl,
                    isRedirectContextRelative(), isRedirectHttp10Compatible());
        }

        return super.createView(!viewName.startsWith("/") ? "/" + viewName
                : viewName, locale);
    }

    /**
     * Custom {@link RedirectView} that prepends the servlet pa
     * 
     * @author Oliver Gierke - gierke@synyx.de
     */
    static class ServletMappingAwareRedirectView extends RedirectView {

        /**
         * @param url
         * @param contextRelative
         * @param http10Compatible
         */
        public ServletMappingAwareRedirectView(String url,
                boolean contextRelative, boolean http10Compatible) {

            super(url, contextRelative, http10Compatible);
        }


        /*
         * (non-Javadoc)
         * 
         * @see
         * org.springframework.web.servlet.view.RedirectView#renderMergedOutputModel
         * (java.util.Map, javax.servlet.http.HttpServletRequest,
         * javax.servlet.http.HttpServletResponse)
         */
        @Override
        protected void renderMergedOutputModel(Map<String, Object> model,
                HttpServletRequest request, HttpServletResponse response)
                throws IOException {

            String targetUrl = getUrl();

            if (isExternal(targetUrl) || isRelative(targetUrl)) {
                super.renderMergedOutputModel(model, request, response);
                return;
            }

            String path = request.getServletPath();

            if (hasServletPath(request)
                    && !targetUrl.startsWith(request.getServletPath())) {
                targetUrl = path + targetUrl;
            }

            setUrl(targetUrl);
            super.renderMergedOutputModel(model, request, response);
        }


        private boolean hasServletPath(HttpServletRequest request) {

            return !"".equals(request.getServletPath());
        }


        /**
         * Returns whether the given target URL is an external one.
         * 
         * @param targetUrl
         * @return
         */
        private boolean isExternal(String targetUrl) {

            return -1 != targetUrl.indexOf("://");
        }


        private boolean isRelative(String url) {

            return !url.startsWith("/");
        }
    }
}
