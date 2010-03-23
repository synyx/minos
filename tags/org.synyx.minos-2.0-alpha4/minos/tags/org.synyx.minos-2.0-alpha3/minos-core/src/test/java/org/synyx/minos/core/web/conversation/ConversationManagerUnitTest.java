package org.synyx.minos.core.web.conversation;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.synyx.minos.core.web.conversation.ConversationManager.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


/**
 * Unit test for {@link ConversationManager}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ConversationManagerUnitTest {

    private ConversationManager manager;

    private MockHttpServletRequest request;

    private HttpServletRequest mock;


    @Before
    public void setUp() {

        manager = new ConversationManager();
        request = new MockHttpServletRequest();
        mock = mock(HttpServletRequest.class);
    }


    @Test
    public void honorsParameterOverAttribute() throws Exception {

        assertResultForParameterAndAttribute("barfoo", "foobar", "barfoo");
    }


    @Test
    public void returnsAttributeIfNoParameter() throws Exception {

        assertResultForParameterAndAttribute(null, "barfoo", "barfoo");
    }


    @Test
    public void setParameterAsAttributeIfAvailable() throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put(DEFAULT_CONVERSATION_KEY_ID, "foo");

        when(mock.getParameterMap()).thenReturn(map);
        when(mock.getParameter(DEFAULT_CONVERSATION_KEY_ID)).thenReturn("foo");

        manager.setConversationKey(mock);

        verify(mock, times(1)).setAttribute(DEFAULT_CONVERSATION_KEY_ID, "foo");
    }


    @Test
    public void doesNotSetAttributeIfAlreadySet() throws Exception {

        when(mock.getParameter(DEFAULT_CONVERSATION_KEY_ID)).thenReturn(null);
        when(mock.getAttribute(DEFAULT_CONVERSATION_KEY_ID)).thenReturn("foo");

        manager.setConversationKey(mock);

        verify(mock, never()).setAttribute(eq(DEFAULT_CONVERSATION_KEY_ID),
                any());
    }


    @Test
    public void setsAttributeIfNotAlreadySet() throws Exception {

        when(mock.getParameter(DEFAULT_CONVERSATION_KEY_ID)).thenReturn(null);
        when(mock.getAttribute(DEFAULT_CONVERSATION_KEY_ID)).thenReturn(null);

        manager.setConversationKey(mock);

        verify(mock, times(1)).setAttribute(eq(DEFAULT_CONVERSATION_KEY_ID),
                anyString());
    }


    private void assertResultForParameterAndAttribute(String parameter,
            String attribute, String result) {

        request.addParameter(DEFAULT_CONVERSATION_KEY_ID, parameter);
        request.setAttribute(DEFAULT_CONVERSATION_KEY_ID, attribute);

        WebRequest webRequest = new ServletWebRequest(request);

        assertThat(manager.getConversationKey(webRequest), is(result));
    }
}
