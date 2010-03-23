package org.synyx.minos.im.remoting.rest;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.synyx.minos.im.web.ImUrls.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.im.domain.InstantMessage;
import org.synyx.minos.im.remoting.rest.dto.InstantMessageDto;
import org.synyx.minos.im.remoting.rest.dto.InstantMessagesDto;
import org.synyx.minos.im.service.InstantMessagingService;
import org.synyx.minos.im.web.ImUrls;
import org.synyx.minos.support.remoting.InvalidRequestException;


/**
 * Controller for REST access to {@link InstantMessagingService}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Controller
public class InstantMessagingRestController {

    private final InstantMessagingService instantMessaging;
    private final InstantMessageDtoAssembler dtoAssembler;


    /**
     * Creates a new {@link InstantMessagingRestController}.
     * 
     * @param instantMessaging
     * @param dtoAssembler
     */
    @Autowired
    public InstantMessagingRestController(
            InstantMessagingService instantMessaging,
            InstantMessageDtoAssembler dtoAssembler) {

        this.instantMessaging = instantMessaging;
        this.dtoAssembler = dtoAssembler;
    }


    /**
     * Return GET and POST as options for message URL.
     * 
     * @param response
     */
    @RequestMapping(value = MESSAGES, method = OPTIONS)
    public void messageOptions(HttpServletResponse response) {

        response.addHeader("Allowed", "GET, POST");
    }


    /**
     * Handles a request for a single {@link InstantMessage}
     * 
     * @param id the id of the message to retrieve
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = MESSAGE, method = GET)
    public InstantMessageDto getMessage(
            @PathVariable("id") InstantMessage message,
            HttpServletRequest request) throws IOException {

        return dtoAssembler.toDto(message, request);
    }


    /**
     * Handles a request for all messages.
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = MESSAGES, method = GET)
    public InstantMessagesDto getMessages(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        InstantMessagesDto messagesDto = new InstantMessagesDto();
        List<InstantMessageDto> messages = messagesDto.getInstantMessage();

        for (InstantMessage message : instantMessaging.getAllMessages()) {

            messages.add(dtoAssembler.toDto(message, request));
        }

        return messagesDto;
    }


    /**
     * Creates a new {@link InstantMessage}.
     * 
     * @param request
     * @param response
     * @return
     * @throws InvalidRequestException
     */
    @RequestMapping(value = MESSAGES, method = POST)
    public void createMessage(@RequestBody InstantMessageDto messageDto,
            HttpServletRequest request, HttpServletResponse response)
            throws InvalidRequestException {

        InstantMessage message = dtoAssembler.toDomainObject(messageDto);

        instantMessaging.send(message);

        UrlUtils.markCreated(ImUrls.MESSAGES, request, response, message);
    }


    @RequestMapping(value = MESSAGE, method = DELETE)
    public void deleteMessage(@PathVariable("id") InstantMessage message) {

        instantMessaging.delete(message);
    }
}
