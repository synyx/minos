package org.synyx.minos.core.web;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;
import org.synyx.hades.domain.Persistable;
import org.synyx.minos.util.Assert;


/**
 * URL utility class easing and safing URL and view name construction.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class UrlUtils {

    private static final String URL_POSTFIX = "";
    private static final String REDIRECT_PREFIX = "redirect:";


    /**
     * Returns the view name to cause a redirect for a given destination view.
     * Absolute view names (starting with an "/") will be turned into relative
     * ones.
     * 
     * @param viewName
     * @return
     */
    public static String redirect(String viewName) {

        viewName = viewName.startsWith("/") ? viewName.substring(1) : viewName;

        return REDIRECT_PREFIX + viewName + URL_POSTFIX;
    }


    /**
     * Returns the absolute URL to the given module.
     * 
     * @param moduleUrl
     * @param request
     * @return
     */
    public static String toUrl(String moduleUrl, HttpServletRequest request,
            boolean preserveSuffix) {

        Assert.notNull(moduleUrl, "Module url must not be null!");
        Assert.notNull(request, "Request must not be null!");

        StringBuilder builder = new StringBuilder("http://");

        builder.append(request.getServerName());

        if (80 != request.getServerPort()) {
            builder.append(":").append(request.getServerPort());
        }

        builder.append(getServletBase(request)).append(moduleUrl);

        if (preserveSuffix) {
            String extension = getRequestSuffix(request);

            if (null != extension) {
                builder.append(".").append(extension);
            }
        }

        return builder.toString();
    }


    public static String toUrl(String moduleUrl, HttpServletRequest request) {

        return toUrl(moduleUrl, request, false);
    }


    /**
     * Creates a url to the given module appending the provided parameters.
     * 
     * @param moduleUrl
     * @param request
     * @param parameters
     * @return
     */
    public static String toUrl(String moduleUrl, HttpServletRequest request,
            Map<String, Object> parameters) {

        StringBuilder builder = new StringBuilder(toUrl(moduleUrl, request));

        // Append parameters only if given
        if (null != parameters && !parameters.isEmpty()) {

            builder.append("?");

            for (Entry<String, Object> entry : parameters.entrySet()) {
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append("&");
            }

            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }


    /**
     * Returns the url to the given module action adding the id of the given
     * {@link Persistable} to the link. This can be used to create URLs to
     * handle the given entity, e.g. edit or delete it. If {@code null} is
     * provided as {@code entity} this will return {@see #toUrl(String,
     * HttpServletRequest)}.
     * 
     * @param moduleUrl
     * @param request
     * @param entity
     * @return
     */
    public static String toUrl(String moduleUrl, HttpServletRequest request,
            Persistable<?> entity) {

        if (null == entity) {
            return toUrl(moduleUrl, request);
        }

        return toUrl(moduleUrl + "/" + entity.getId(), request, true);

    }


    /**
     * Modifies the given response to tell the client that the given entity has
     * been successfully created.
     * 
     * @param url the URL the entity can be retrieved under
     * @param request
     * @param response
     * @param entity the entity that was created successfully
     */
    public static void markCreated(String url, HttpServletRequest request,
            HttpServletResponse response, Persistable<?> entity) {

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", toUrl(url, request, entity));
    }


    /**
     * Returns the base URL of the servlet the request was made to.
     * 
     * @param request
     * @return
     */
    public static String getServletBase(HttpServletRequest request) {

        return request.getContextPath() + request.getServletPath();
    }


    private static String getRequestSuffix(HttpServletRequest request) {

        String requestUri = new UrlPathHelper().getRequestUri(request);
        String filename = WebUtils.extractFullFilenameFromUrlPath(requestUri);

        return StringUtils.getFilenameExtension(filename);
    }
}
