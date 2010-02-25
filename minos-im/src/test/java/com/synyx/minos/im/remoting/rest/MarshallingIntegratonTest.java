package com.synyx.minos.im.remoting.rest;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.synyx.minos.core.remoting.rest.dto.IdentifyableDto;
import org.synyx.minos.im.remoting.rest.dto.InstantMessageDto;
import org.synyx.minos.im.remoting.rest.dto.InstantMessagesDto;
import org.synyx.minos.test.AbstractModuleIntegrationTest;


/**
 * Integration test to check marshallign configuration.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@ContextConfiguration(locations = { "classpath*:META-INF/minos/**/rest-context.xml" })
public class MarshallingIntegratonTest extends AbstractModuleIntegrationTest {

    @Autowired
    @Qualifier("imMarshaller")
    private Jaxb2Marshaller imMarshaller;


    /**
     * Simply builds an IM object graph and invokes the marshaller to see if it
     * works correctly.
     * 
     * @throws Exception
     */
    @Test
    public void testMarshalling() throws Exception {

        InstantMessageDto message = new InstantMessageDto();
        message.setId(5L);

        IdentifyableDto sender = new IdentifyableDto();
        sender.setId(1L);
        sender.setHref("/foo");

        message.setSender(sender);
        message.setReceipient(sender);
        message.setText("");
        message.setSendDate(new DateTime());
        message.setRead(false);

        InstantMessagesDto messages = new InstantMessagesDto();
        messages.getInstantMessage().add(message);
        messages.getInstantMessage().add(message);
        messages.getInstantMessage().add(message);

        Result result = new StreamResult(new ByteArrayOutputStream());
        imMarshaller.marshal(messages, result);

        System.out.println(result.toString());
    }
}
