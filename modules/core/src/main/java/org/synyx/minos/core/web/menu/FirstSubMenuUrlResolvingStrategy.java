package org.synyx.minos.core.web.menu;

import java.util.List;


/**
 * {@link UrlResolvingStrategy} that delegates the resolving to the first sub- {@link MenuItem}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class FirstSubMenuUrlResolvingStrategy implements UrlResolvingStrategy {

    @Override
    public String resolveUrl(MenuItem item) {

        List<MenuItem> subMenues = item.getSubMenues();
        if (subMenues == null || subMenues.isEmpty()) {
            return null;
        }

        return subMenues.get(0).getUrl();

    }

}
