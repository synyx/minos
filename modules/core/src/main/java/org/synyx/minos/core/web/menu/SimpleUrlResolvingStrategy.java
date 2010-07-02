package org.synyx.minos.core.web.menu;

/**
 * Strategy that simply returns the url from a property.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class SimpleUrlResolvingStrategy implements UrlResolvingStrategy {

    private final String url;


    public SimpleUrlResolvingStrategy(String url) {

        this.url = url;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.UrlResolvingStrategy#resolveUrl(org.synyx.minos.core.web.menu.MenuItem)
     */
    @Override
    public String resolveUrl(MenuItem item) {

        return url;
    }
}
