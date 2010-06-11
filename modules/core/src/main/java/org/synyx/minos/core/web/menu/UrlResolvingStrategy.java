package org.synyx.minos.core.web.menu;

import org.synyx.minos.core.domain.User;


/**
 * Interface for resolving an Url for a given {@link MenuItem}
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public interface UrlResolvingStrategy {

    public static final String USER_PLACEHOLDER = "{username}";


    /**
     * Return the Url for the given {@link MenuItem} and the {@link User} user. Replaces eventually contained
     * placeholders for the user with the given {@link User}s username. Cleans up the URL if no {@link User} is given.
     * 
     * @param user the {@link User} to resolve the url for
     * @param item the {@link MenuItem} to resolve the url for
     * @return the Url for the given {@link MenuItem} and {@link User}
     */
    String resolveUrl(User user, MenuItem item);

}
