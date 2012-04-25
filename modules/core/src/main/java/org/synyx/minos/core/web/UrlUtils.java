package org.synyx.minos.core.web;

import org.synyx.hades.domain.Persistable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * URL utility class easing and saving URL and view name construction.
 *
 * @author Oliver Gierke - gierke@synyx.de
 * @author Aljona Murygina - murygina@synyx.de
 */
public final class UrlUtils extends org.synyx.util.UrlUtils {

    private UrlUtils() {
    }


    /**
     * Returns the url to the given module action adding the id of the given {@link Persistable} to the link. This can
     * be used to create URLs to handle the given entity, e.g. edit or delete it. If {@code null} is provided as {@code
     * entity} this will return {@see #toUrl(String, HttpServletRequest)}.
     *
     * @param moduleUrl
     * @param request
     * @param entity
     * @return
     */
    public static String toUrl(String moduleUrl, HttpServletRequest request, Persistable<?> entity) {

        if (null == entity) {
            return toUrl(moduleUrl, request);
        }

        return toUrl(moduleUrl + "/" + entity.getId(), request, true);
    }


    /**
     * Modifies the given response to tell the client that the given entity has been successfully created.
     *
     * @param url the URL the entity can be retrieved under
     * @param request
     * @param response
     * @param entity the entity that was created successfully
     */
    public static void markCreated(String url, HttpServletRequest request, HttpServletResponse response,
        Persistable<?> entity) {

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", toUrl(url, request, entity));
    }

}
