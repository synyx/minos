package org.synyx.minos.core.web;

import org.springframework.beans.factory.annotation.Required;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.util.WebUtils;

import org.synyx.tagsupport.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * This interceptor transparently transfers {@code Message}s through a redirect. It uses the {@code HttpSession} to do
 * this and cares for session cleanup after the redirect.
 *
 * <p>Messages that are added in the redirect action transparently override the session message. This ensures to always
 * see the latest system message but implies the disadvantage, that the old message is never being seen.</p>
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class MessageViaRedirectInterceptor extends HandlerInterceptorAdapter {

    private String messageKey;

    /**
     * Configures the message key that is used to lookup messages in the {@link HttpSession} and
     * {@link HttpServletRequest}. This has to be the key under which controllers register application messages.
     *
     * @param  messageKey  the messageKey to set
     */
    @Required
    public void setMessageKey(String messageKey) {

        this.messageKey = messageKey;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {

        Message oldMessage = (Message) WebUtils.getSessionAttribute(request, messageKey);

        // Cleanup session
        HttpSession session = request.getSession();
        session.removeAttribute(messageKey);

        // Skip everything else if no model provided
        if (null == modelAndView) {
            return;
        }

        // Restore old message, if no new one found
        if (null != oldMessage && !hasNewMessage(modelAndView)) {
            modelAndView.addObject(messageKey, oldMessage);
        }

        // Redirect containing a message?
        if (isRedirect(modelAndView) && hasNewMessage(modelAndView)) {
            // Store message in session
            Message message = (Message) modelAndView.getModel().get(messageKey);
            session.setAttribute(messageKey, message);
        }
    }


    /**
     * Returns whether we have a new message in the current model.
     *
     * @param  modelAndView
     *
     * @return
     */
    private boolean hasNewMessage(ModelAndView modelAndView) {

        return modelAndView.getModel().containsKey(messageKey);
    }


    /**
     * Returns whether the current request results in a redirect.
     *
     * @param  modelAndView
     *
     * @return
     */
    private boolean isRedirect(ModelAndView modelAndView) {

        String viewName = modelAndView.getViewName();

        if (null != viewName && viewName.startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX)) {
            return true;
        }

        return false;
    }
}
