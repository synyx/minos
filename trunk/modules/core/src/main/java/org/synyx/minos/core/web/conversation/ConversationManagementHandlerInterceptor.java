package org.synyx.minos.core.web.conversation;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * {@link HandlerInterceptor} to poulate a {@link HttpServletRequest} with a conversation key.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ConversationManagementHandlerInterceptor extends HandlerInterceptorAdapter {

    private ConversationManager conversationManager;

    /**
     * Creates a new {@link ConversationManagementHandlerInterceptor}.
     *
     * @param conversationManager
     */
    public ConversationManagementHandlerInterceptor(ConversationManager conversationManager) {

        this.conversationManager = conversationManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        conversationManager.setConversationKey(request);

        return true;
    }
}
