package org.synyx.minos.core.web.menu;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;
import org.synyx.minos.core.domain.User;


/**
 * {@link UrlResolvingStrategy} that takes a list of {@link MenuItem} of which the first available elements url gets
 * returned.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class PreferredSubMenuItemUrlResolvingStrategy implements UrlResolvingStrategy {

    private List<MenuItem> preferences;


    public PreferredSubMenuItemUrlResolvingStrategy(MenuItem... preferences) {

        super();
        Assert.notEmpty(preferences);
        this.preferences = Arrays.asList(preferences);
    }


    @Override
    public String resolveUrl(User user, MenuItem item) {

        for (MenuItem preference : preferences) {
            if (item.getSubMenues().contains(preference)) {
                return preference.getUrl(user);
            }
        }
        return null;
    }

}
