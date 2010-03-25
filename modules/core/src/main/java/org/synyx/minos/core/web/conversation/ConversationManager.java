package org.synyx.minos.core.web.conversation;

import static org.springframework.web.util.WebUtils.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;


/**
 * Managing component to provide and lookup conversation keys for web request.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ConversationManager {

    static final String DEFAULT_CONVERSATION_KEY_ID = "_conversationId";
    private String conversationKeyId = DEFAULT_CONVERSATION_KEY_ID;


    /**
     * Sets the id under which conversation keys can be found.
     * 
     * @param conversationKeyId
     */
    public void setConversationKeyId(String conversationKeyId) {

        this.conversationKeyId =
                conversationKeyId == null ? DEFAULT_CONVERSATION_KEY_ID
                        : conversationKeyId;
    }


    /**
     * Will lookup the conversation key from the given {@link WebRequest}. Will
     * favour parameters over attributes.
     * 
     * @param request
     * @return
     */
    public String getConversationKey(WebRequest request) {

        String conversationKey = request.getParameter(conversationKeyId);

        if (StringUtils.hasText(conversationKey)) {
            return conversationKey;
        }

        return (String) request.getAttribute(conversationKeyId,
                WebRequest.SCOPE_REQUEST);
    }


    /**
     * Equips the given {@link HttpServletRequest} with a conversation key. Will
     * favour an existing one in a parameter over one in an attribute. Will only
     * create a new one if none is found at all.
     * 
     * @param request
     */
    public void setConversationKey(HttpServletRequest request) {

        // Prefer request parameter in any case
        if (hasConversationKeyAsParameter(request)) {
            request.setAttribute(conversationKeyId, findParameterValue(request,
                    conversationKeyId));
            return;
        }

        // Create key if none already set
        if (!StringUtils.hasText((String) request
                .getAttribute(conversationKeyId))) {
            request.setAttribute(conversationKeyId, createConversationKey());
        }
    }


    /**
     * Returns whether the given request is equipped with a conversation id as
     * parameter.
     * 
     * @param request
     * @return
     */
    private boolean hasConversationKeyAsParameter(HttpServletRequest request) {

        return findParameterValue(request, conversationKeyId) != null;
    }


    /**
     * Creates a fresh conversation key. This implementation returns a 32
     * character alphanumeric string.
     * 
     * @return
     */
    protected String createConversationKey() {

        return RandomStringUtils.randomAlphanumeric(32);
    }
}
