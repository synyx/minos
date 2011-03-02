package org.synyx.minos.core.web;

import org.springframework.util.StringUtils;

import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;

import org.synyx.minos.core.web.conversation.ConversationManager;


/**
 * Custom {@link SessionAttributeStore} to prefix session attributes with a conversation key determined via the
 * underlying {@link ConversationManager}.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosSessionAttributeStore extends DefaultSessionAttributeStore {

    private ConversationManager manager;

    /**
     * Creates a new {@link MinosSessionAttributeStore}.
     *
     * @param manager
     */
    public MinosSessionAttributeStore(ConversationManager manager) {

        this.manager = manager;
    }

    /*
     * (non-Javadoc)
     *
     * @seeorg.springframework.web.bind.support.DefaultSessionAttributeStore# getAttributeNameInSession
     * (org.springframework.web.context.request.WebRequest, java.lang.String)
     */
    @Override
    protected String getAttributeNameInSession(WebRequest request, String attributeName) {

        String conversationKey = manager.getConversationKey(request);

        return StringUtils.hasText(conversationKey) ? String.format("%s.%s", conversationKey, attributeName)
                                                    : super.getAttributeNameInSession(request, attributeName);
    }
}
