package org.synyx.minos.umt.remoting.rest;

import org.springframework.beans.factory.annotation.Autowired;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.remoting.rest.AbstractDtoAssembler;
import org.synyx.minos.core.remoting.rest.dto.LinkDto;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.umt.remoting.rest.dto.UserDto;
import org.synyx.minos.umt.remoting.rest.dto.UsersDto;
import org.synyx.minos.umt.service.UserManagement;
import static org.synyx.minos.umt.web.UmtUrls.USERS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Assembler to build Data Transfer Objects for user management module.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UmtDtoAssembler extends AbstractDtoAssembler {

    /**
     * Creates a new {@link UmtDtoAssembler};
     *
     * @param userManagement
     */
    @Autowired
    public UmtDtoAssembler(UserManagement userManagement) {

        super(userManagement);
    }

    public UserDto toDto(User user) {

        return toDto(user, null);
    }


    /**
     * Creates a new {@link UserDto} from the given {@link User}.
     *
     * @param user
     * @param request
     * @return
     */
    public UserDto toDto(User user, HttpServletRequest request) {

        if (null == user) {
            return null;
        }

        UserDto userDto = mapBasicProperties(user, new UserDto(), request, USERS);
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setEmailAddress(user.getEmailAddress());

        LinkDto linkDto = new LinkDto();
        linkDto.setHref(UrlUtils.toUrl(USERS, request, user));
        linkDto.setRel("self");

        userDto.getLink().add(linkDto);

        return userDto;
    }


    public UsersDto toDto(List<User> users) {

        return toDto(users, null);
    }


    /**
     * Transforms the given {@link List} of {@link User}s into a {@link UsersDto} instance allowing it to marshalled.
     *
     * @param users
     * @param request
     * @return
     */
    public UsersDto toDto(List<User> users, HttpServletRequest request) {

        UsersDto usersDto = new UsersDto();

        for (User user : users) {
            usersDto.getUser().add(toDto(user, request));
        }

        return usersDto;
    }


    /**
     * Creates a new domain object from the given {@link UserDto}.
     *
     * @param userDto
     * @return
     */
    public User toDomainObject(UserDto userDto) {

        return toDomainObject(userDto, new User(userDto.getUsername(), userDto.getEmailAddress(), null), false);
    }


    /**
     * Maps the given DTOs properties to the given domain object.
     *
     * @param userDto
     * @param user
     * @return
     */
    public User toDomainObject(UserDto userDto, User user) {

        return toDomainObject(userDto, user, true);
    }


    /**
     * Maps the given DTOs properties to the given domain object.
     *
     * @param userDto
     * @param user
     * @param mapId whether to map the id property of the DTO. Should be {@code false} for {@link User} instances to be
     *            newly created.
     * @return
     */
    private User toDomainObject(UserDto userDto, User user, boolean mapId) {

        if (null == userDto) {
            return null;
        }

        User mappedUser = mapBasicProperties(userDto, user, mapId);

        mappedUser.setFirstname(userDto.getFirstname());
        mappedUser.setLastname(userDto.getLastname());
        mappedUser.setUsername(userDto.getUsername());
        mappedUser.setEmailAddress(userDto.getEmailAddress());

        return mappedUser;
    }
}
