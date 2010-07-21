package org.synyx.minos.core.web.menu;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;


/**
 * {@link UrlResolver} that takes a list of {@link MenuItem} of which the first available elements url gets returned.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class PreferredSubMenuItemUrlResolver implements UrlResolver {

    private final List<Menu> preferences;


    /**
     * Creates a new {@link PreferredSubMenuItemUrlResolver}.
     * 
     * @param preferences the sub {@link MenuItem}s whose URLs should be preferred to be used
     */
    public PreferredSubMenuItemUrlResolver(Menu... preferences) {

        Assert.notEmpty(preferences);
        this.preferences = Arrays.asList(preferences);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.UrlResolvingStrategy#resolveUrl(org.synyx.minos.core.web.menu.MenuItem)
     */
    @Override
    public String resolveUrl(Menu item) {

        for (Menu preference : preferences) {
            if (item.hasSubMenuItem(preference)) {
                return preference.getUrl();
            }
        }
        return null;
    }
}
