package org.synyx.minos.im.remoting.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    private InstantMessagingService instantMessaging;
    private InstantMessageDtoAssembler dtoAssembler;


    /**
     * Setter to inject reference to {@link InstantMessagingService}.
     * 
     * @param instantMessaging the instantMessaging to set
     */
    @Required
    public void setInstantMessaging(InstantMessagingService instantMessaging) {

        this.instantMessaging = instantMessaging;
    }


    /**
     * Setter to inject an {@link InstantMessageDtoAssembler} to assemble domain
     * objectrs from DTOs and vice versa.
     * 
     * @param dtoAssembler the dtoAssembler to set
     */
    @Required
    public void setDtoAssembler(InstantMessageDtoAssembler dtoAssembler) {

        this.dtoAssembler = dtoAssembler;
    }


    /**
     * Return GET and POST as options for message URL.
     * 
     * @param response
     */
    @RequestMapping(value = ImUrls.LIST_MESSAGES, method = RequestMethod.OPTIONS)
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
    @RequestMapping(value = ImUrls.LIST_MESSAGES + "/{id}", method = RequestMethod.GET)
    public InstantMessageDto getMessage(@PathVariable("id") Long id,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        InstantMessage message = instantMessaging.getInstantMessage(id);

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
    @RequestMapping(value = { ImUrls.LIST_MESSAGES, ImUrls.INBOX }, method = RequestMethod.GET)
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
    @RequestMapping(value = ImUrls.LIST_MESSAGES, method = RequestMethod.POST)
    public void createMessage(@RequestBody InstantMessageDto messageDto,
            HttpServletRequest request, HttpServletResponse response)
            throws InvalidRequestException {

        InstantMessage message = dtoAssembler.toDomainObject(messageDto);

        instantMessaging.send(message);

        UrlUtils.markCreated(ImUrls.LIST_MESSAGES, request, response, message);
    }


    @RequestMapping(value = ImUrls.LIST_MESSAGES + "{id}", method = RequestMethod.DELETE, params = "id")
    public void deleteMessage(@PathVariable("id") Long id) {

        instantMessaging.delete(id);
    }
}
