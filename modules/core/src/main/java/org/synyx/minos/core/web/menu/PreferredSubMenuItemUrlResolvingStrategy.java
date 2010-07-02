package org.synyx.minos.core.web.menu;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;


/**
 * {@link UrlResolvingStrategy} that takes a list of {@link MenuItem} of which the first available elements url gets
 * returned.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke
 */
public class PreferredSubMenuItemUrlResolvingStrategy implements UrlResolvingStrategy {

    private final List<MenuItem> preferences;


    /**
     * Creates a new {@link PreferredSubMenuItemUrlResolvingStrategy}.
     * 
     * @param preferences the sub {@link MenuItem}s whose URLs should be preferred to be used
     */
    public PreferredSubMenuItemUrlResolvingStrategy(MenuItem... preferences) {

        Assert.notEmpty(preferences);
        this.preferences = Arrays.asList(preferences);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.web.menu.UrlResolvingStrategy#resolveUrl(org.synyx.minos.core.web.menu.MenuItem)
     */
    @Override
    public String resolveUrl(MenuItem item) {

        for (MenuItem preference : preferences) {
            if (item.hasSubMenuItem(preference)) {
                return preference.getUrl();
            }
        }
        return null;
    }
}
