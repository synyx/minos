package org.synyx.minos.core.web.menu;

import org.synyx.minos.core.domain.User;


/**
 * Interface for resolving an Url for a given {@link MenuItem}
 *
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface UrlResolver {

    /**
     * Return the Url for the given {@link Menu}
     *
     * @param item the {@link Menu} to resolve the url for
     * @return the Url for the given {@link Menu} and {@link User}
     */
    String resolveUrl(Menu item);
}
