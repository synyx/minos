package org.synyx.minos.core.web.menu;

import org.synyx.minos.core.domain.User;


/**
 * Interface for resolving an URL for a given {@link MenuItem}
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface UrlResolver {

    /**
     * Return the URL for the given {@link Menu} item.
     *
     * @param item the {@link Menu} item to resolve the URL for
     * @return the URL for the given {@link Menu} and {@link User}
     */
    String resolveUrl(Menu item);
}
