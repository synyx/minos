package org.synyx.minos.core.web.menu;

import org.synyx.minos.core.domain.User;


/**
 * Strategy that simply returns the url from a property.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class SimpleUrlResolvingStrategy implements UrlResolvingStrategy {

    private String url;


    public SimpleUrlResolvingStrategy(String url) {

        super();
        this.url = url;
    }


    public String getUrl() {

        return url;
    }


    public void setUrl(String url) {

        this.url = url;
    }


    @Override
    public String resolveUrl(User user, MenuItem item) {

        return url;
    }

}
