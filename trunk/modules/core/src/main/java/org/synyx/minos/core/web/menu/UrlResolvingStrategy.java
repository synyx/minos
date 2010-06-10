package org.synyx.minos.core.web.menu;

import org.synyx.minos.core.domain.User;


/**
 * Interface for resolving an Url for a given {@link MenuItem}
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface UrlResolvingStrategy {

    /**
     * Return the Url for the given {@link MenuItem} and the {@link User} user
     * 
     * @param user the {@link User} to resolve the url for
     * @param item the {@link MenuItem} to resolve the url for
     * @return the Url for the given {@link MenuItem} and {@link User}
     */
    String resolveUrl(User user, MenuItem item);

}
