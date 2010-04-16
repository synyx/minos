package org.synyx.minos.core.web.menu;

import java.util.List;

import org.synyx.minos.core.domain.User;


/**
 * {@link UrlResolvingStrategy} that delegates the resolving to the first sub- {@link MenuItem}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class FirstSubMenuUrlResolvingStrategy implements UrlResolvingStrategy {

    @Override
    public String resolveUrl(User user, MenuItem item) {

        List<MenuItem> subMenues = item.getSubMenues();
        if (subMenues == null || subMenues.isEmpty()) {
            return null;
        }

        return subMenues.get(0).getUrl(user);

    }

}
