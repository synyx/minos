package org.synyx.minos.core.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import org.synyx.tagsupport.Message;

import javax.servlet.http.HttpSession;


/**
 * Unit test for {@link MessageViaRedirectInterceptor}.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class MessageViaRedirectInterceptorUnitTest {

    private static final String MESSAGE_KEY = "messageKey";
    private static final String NON_REDIRECT_VIEW = "view";
    private static final String REDIRECT_VIEW = UrlBasedViewResolver.REDIRECT_URL_PREFIX + NON_REDIRECT_VIEW;

    private MessageViaRedirectInterceptor interceptor;

    private ModelAndView modelAndView;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    private Message message;

    @Before
    public void setUp() {

        interceptor = new MessageViaRedirectInterceptor();
        interceptor.setMessageKey(MESSAGE_KEY);

        modelAndView = new ModelAndView();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();

        request.setSession(session);

        message = Message.success("resource.bundle.key");
    }


    /**
     * Tests whether the interceptor stores an available {@link Message} in the session on redirect calls.
     *
     * @throws  Exception
     */
    @Test
    public void storesMessageOnRedirect() throws Exception {

        // Set message and redirect view
        modelAndView.addObject(MESSAGE_KEY, message);
        modelAndView.setViewName(REDIRECT_VIEW);

        interceptor.postHandle(request, response, null, modelAndView);

        Object result = session.getAttribute(MESSAGE_KEY);
        assertNotNull("Message not stored in session!", result);
        assertEquals("Result is not the message put into the model!", message, result);
    }


    /**
     * Tests whether the interceptor retrieves the old message if the controller did not produce a new one on a
     * non-redirect view.
     *
     * @throws  Exception
     */
    @Test
    public void retrievesMessageIfNoneNewSet() throws Exception {

        assertMessageFound(message, null, message);
    }


    /**
     * Tests whether the interceptor uses the new message if the controller call produced a new message, even if there
     * is a old message stored.
     *
     * @throws  Exception
     */
    @Test
    public void usesNewMessageIfNewSet() throws Exception {

        Message newMessage = Message.error("error");

        assertMessageFound(newMessage, newMessage, message);
    }


    /**
     * Tests, whether the interceptor cleans up the {@link HttpSession} on non-redirect calls.
     *
     * @throws  Exception
     */
    @Test
    public void cleansUpIfOldMessageFound() throws Exception {

        retrievesMessageIfNoneNewSet();
        assertNull(session.getAttribute(MESSAGE_KEY));
    }


    /**
     * Asserts that the interceptor simply does nothing on {@code null} models.
     *
     * @throws  Exception
     */
    @Test
    public void handlesNullModelAndViewCorrectly() throws Exception {

        interceptor.postHandle(request, response, null, null);
    }


    /**
     * Asserts that the message {@code toBeFound} is found in the model after a controller invocation that produces the
     * new message while already having the {@code oldMessage}. This can be used to verify overwriting behaviour of the
     * {@link MessageViaRedirectInterceptor}.
     *
     * @param  toBeFound
     * @param  newMessage
     * @param  oldMessage
     *
     * @throws  Exception
     */
    private void assertMessageFound(Message toBeFound, Message newMessage, Message oldMessage) throws Exception {

        // Suppose old message being stored
        session.setAttribute(MESSAGE_KEY, oldMessage);

        // Simulate a non-redirect call, that should trigger mesage restoring
        modelAndView.setViewName(NON_REDIRECT_VIEW);

        // Decide whether the simulated controller call has produced a message
        // itself
        if (null != newMessage) {
            modelAndView.addObject(MESSAGE_KEY, newMessage);
        }

        interceptor.postHandle(request, response, null, modelAndView);

        // Assert the given message to be found in the model
        assertTrue(modelAndView.getModel().containsKey(MESSAGE_KEY));
        assertEquals(toBeFound, modelAndView.getModel().get(MESSAGE_KEY));
    }
}
