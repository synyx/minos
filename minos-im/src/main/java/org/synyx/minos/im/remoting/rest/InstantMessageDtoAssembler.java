package org.synyx.minos.im.remoting.rest;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Required;
import org.synyx.minos.core.remoting.rest.AbstractDtoAssembler;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.im.domain.InstantMessage;
import org.synyx.minos.im.remoting.rest.dto.InstantMessageDto;
import org.synyx.minos.im.service.InstantMessagingService;
import org.synyx.minos.im.web.ImUrls;
import org.synyx.minos.support.remoting.InvalidRequestException;
import org.synyx.minos.umt.web.UmtUrls;



/**
 * Data transfer object assembler for instant messaging module. Allows mapping
 * of DTOs to domain objects and vice versa.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class InstantMessageDtoAssembler extends AbstractDtoAssembler {

    private InstantMessagingService instantMessagingService;


    /**
     * Setter to inject a {@link InstantMessagingService}. Used to lookup
     * existing {@link InstantMessage}s for assembling.
     * 
     * @param instantMessagingService the instantMessagingService to set
     */
    @Required
    public void setInstantMessagingService(
            InstantMessagingService instantMessagingService) {

        this.instantMessagingService = instantMessagingService;
    }


    public InstantMessageDto toDto(InstantMessage message,
            HttpServletRequest request) {

        if (null == message) {
            return null;
        }

        InstantMessageDto messageDto = new InstantMessageDto();

        messageDto.setId(message.getId());
        messageDto.setHref(UrlUtils.toUrl(ImUrls.LIST_MESSAGES, request,
                message));
        messageDto.setSender(toIdentifyable(message.getSender(),
                UmtUrls.EDIT_USER, request));
        messageDto.setReceipient(toIdentifyable(message.getReceipient(),
                UmtUrls.EDIT_USER, request));
        messageDto.setText(message.getText());
        messageDto.setSendDate(message.getSendDate());
        messageDto.setRead(message.isRead());

        return messageDto;
    }


    /**
     * Creates an {@link InstantMessage} from the given
     * {@link InstantMessageDto}.
     * 
     * @param messageDto
     * @return
     * @throws InvalidRequestException
     */
    public InstantMessage toDomainObject(InstantMessageDto messageDto)
            throws InvalidRequestException {

        InstantMessage message =
                0 == messageDto.getId() ? new InstantMessage()
                        : instantMessagingService.getInstantMessage(messageDto
                                .getId());

        if (null == message) {
            throw new InvalidRequestException("Invalid message content!");
        }

        message.setText(messageDto.getText());
        message.setSender(toDomainObject(messageDto.getSender()));
        message.setReceipient(toDomainObject(messageDto.getReceipient()));
        message.setSendDate(new DateTime(messageDto.getSendDate()));

        return message;
    }
}
