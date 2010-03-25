package org.synyx.minos.support.remoting;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;


/**
 * Custom {@link ContentNegotiatingViewResolver} that returns a view rendering
 * to {@code 406 Not Acceptable} indicating no supported media type for the
 * current request is available. This means it acts rather strict in contrast to
 * the superclass that always returns {@literal null} and thus allows other view
 * resolvers to kick in.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class StrictContentNegotiatingViewResolver extends
        ContentNegotiatingViewResolver {

    private static final View NOT_ACCEPTABLE_VIEW = new NotAcceptableView();

    private boolean beStrict = false;


    /**
     * Set this to {@literal true} if you want to let act the
     * {@link ViewResolver} in a strict way meaning creating a {@code 406 Not
     * Acceptable} view instead of return no view at all which would cause other
     * {@link ViewResolver}s configured to be asked to resolve the view.
     * Defaults to {@literal false}.
     * 
     * @param beStrict
     */
    public void setBeStrict(boolean beStrict) {

        this.beStrict = beStrict;
    }


    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.web.servlet.view.ContentNegotiatingViewResolver#
     * resolveViewName(java.lang.String, java.util.Locale)
     */
    @Override
    public View resolveViewName(String viewName, Locale locale)
            throws Exception {

        View view = super.resolveViewName(viewName, locale);

        return (view == null && beStrict) ? NOT_ACCEPTABLE_VIEW : view;
    }

    private static class NotAcceptableView implements View {

        @Override
        public String getContentType() {

            return null;
        }


        @Override
        public void render(Map<String, ?> model, HttpServletRequest request,
                HttpServletResponse response) throws Exception {

            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }
}
