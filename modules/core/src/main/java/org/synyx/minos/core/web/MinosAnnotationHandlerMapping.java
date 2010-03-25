package org.synyx.minos.core.web;

import java.util.Set;

import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;


/**
 * Custom {@link DefaultAnnotationHandlerMapping} implementation that also adds
 * trailing slash URL pattern for a given path when
 * {@link #setUseDefaultSuffixPattern(boolean)} is set to false. This way
 * setting the property to {@literal true} will still map URLs with a trailing
 * slash but <em>not</em> strip any suffix.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosAnnotationHandlerMapping extends
        DefaultAnnotationHandlerMapping {

    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.web.servlet.mvc.annotation.
     * DefaultAnnotationHandlerMapping#addUrlsForPath(java.util.Set,
     * java.lang.String)
     */
    @Override
    protected void addUrlsForPath(Set<String> urls, String path) {

        super.addUrlsForPath(urls, path);

        urls.add(path + "/");
    }
}
