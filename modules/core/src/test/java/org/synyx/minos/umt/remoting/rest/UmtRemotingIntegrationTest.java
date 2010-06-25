package org.synyx.minos.umt.remoting.rest;

import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.HttpHeaders;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synyx.minos.test.integration.AbstractJUnit4JettyIntegrationTest;
import org.synyx.minos.umt.remoting.rest.dto.UserDto;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HeaderOnlyWebRequest;
import com.meterware.httpunit.HttpNotFoundException;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.PutMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Integration test for UMT REST remoting.
 * 
 * @author Oliver Gierke
 */
public class UmtRemotingIntegrationTest extends
        AbstractJUnit4JettyIntegrationTest {

    private static final String USER_URL = "http://localhost:"
            + System.getProperty("jetty.testport", "7070")
            + "/rest/v1/umt/users";

    private Jaxb2Marshaller unmarshaller;

    private WebConversation conversation;

    private UserDto user;


    /**
     * Initialize {@link Jaxb2Marshaller} to verify returned results.
     * 
     * @throws Exception
     */
    @Before
    public void initMarshaller() throws Exception {

        unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setContextPath("org.synyx.minos.umt.remoting.rest.dto");
        unmarshaller.afterPropertiesSet();

        conversation = new WebConversation();
    }


    @After
    public void tearDown() throws MalformedURLException, IOException,
            SAXException {

        if (user == null) {
            return;
        }

        String url = user.getHref();

        if (url != null) {
            delete(url);
        }
    }


    @Test
    public void userLifecycle() throws MalformedURLException, IOException,
            SAXException {

        // Set up user instance
        user = new UserDto();
        user.setUsername("user");
        user.setEmailAddress("mail");

        // Post to webservice
        WebResponse response = postAndAssert(user);

        // Get from webservice
        user = getUserInternal(response.getHeaderField(HttpHeaders.LOCATION));

        // Modify and put
        user.setLastname("lastname");
        putUserInternal(user);
    }


    @Test(expected = HttpNotFoundException.class)
    public void getUnavailableUser() throws MalformedURLException, IOException,
            SAXException {

        conversation.getResponse(USER_URL + "/4711");
    }


    private void putUserInternal(UserDto user) throws MalformedURLException,
            IOException, SAXException {

        String selfUrl = user.getHref();

        WebResponse response =
                conversation.getResponse(new PutMethodWebRequest(selfUrl,
                        fromUser(user), "application/xml"));

        assertThat(response.getResponseCode(), is(SC_OK));
    }


    private WebResponse postAndAssert(UserDto user)
            throws MalformedURLException, IOException, SAXException {

        PostMethodWebRequest request =
                new PostMethodWebRequest(USER_URL, fromUser(user),
                        "application/xml");

        WebResponse response = conversation.getResponse(request);

        assertThat(response.getResponseCode(), is(SC_CREATED));
        assertThat(response.getHeaderField(HttpHeaders.LOCATION),
                is(notNullValue()));

        return response;
    }


    private void delete(String url) throws MalformedURLException, IOException,
            SAXException {

        WebResponse response =
                conversation.getResponse(new HeaderOnlyWebRequest(url) {

                    @Override
                    public String getMethod() {

                        return RequestMethod.DELETE.toString();
                    }
                });

        assertThat(response.getResponseCode(), is(SC_OK));
    }


    private UserDto getUserInternal(String url) throws MalformedURLException,
            IOException, SAXException {

        WebResponse response = conversation.getResponse(url);

        UserDto user =
                (UserDto) unmarshaller.unmarshal(new StreamSource(response
                        .getInputStream()));

        assertThat(user, is(notNullValue()));

        return user;
    }


    /**
     * Marshals the given {@link UserDto} to an {@link InputStream} to act as
     * payload for {@link WebRequest}s.
     * 
     * @param user
     * @return
     */
    private InputStream fromUser(UserDto user) {

        StreamResult result = new StreamResult(new ByteArrayOutputStream());
        unmarshaller.marshal(user, result);

        return new ByteArrayInputStream(result.getOutputStream().toString()
                .getBytes());
    }
}
