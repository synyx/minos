package org.synyx.minos.core.web.menu;

/**
 * {@link UrlResolvingStrategy} that delegates the resolving to the first sub {@link MenuItem}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class FirstSubMenuUrlResolvingStrategy implements UrlResolvingStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.UrlResolvingStrategy#resolveUrl(org.synyx.minos.core.web.menu.MenuItem)
     */
    @Override
    public String resolveUrl(MenuItem item) {

        return item.hasSubMenues() ? item.getSubMenues().iterator().next().getUrl() : null;
    }
}
