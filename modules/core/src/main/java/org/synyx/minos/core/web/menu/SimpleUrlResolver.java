package org.synyx.minos.core.web.menu;

/**
 * Strategy that simply returns the url from a property.
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class SimpleUrlResolver implements UrlResolver {

    private final String url;

    public SimpleUrlResolver(String url) {

        this.url = url;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.synyx.minos.core.web.menu.UrlResolvingStrategy#resolveUrl(org.synyx.minos.core.web.menu.MenuItem)
     */
    @Override
    public String resolveUrl(Menu item) {

        return url;
    }
}
