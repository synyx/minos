package org.synyx.minos.umt.remoting.rest;

import static javax.servlet.http.HttpServletResponse.*;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.support.remoting.NoContentException;
import org.synyx.minos.umt.remoting.rest.dto.UserDto;
import org.synyx.minos.umt.remoting.rest.dto.UsersDto;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.service.UserNotFoundException;
import org.synyx.minos.umt.web.UmtUrls;


/**
 * Controller handling REST requests for user management module. The controller
 * is mapped to {@code $ RestUrl}
 * /umt/**}. Instance methods specify detailed urls, where the functionality can
 * be found.
 * <p>
 * The implementation is rather ugly as the SpringMVC annotation support is not
 * designed for REST sytle of use. Also the handling of result values is not as
 * sophisticated as it could be. There is already an open ticket in the Spring
 * bugtracker adressing the issue:
 * 
 * @see http://jira.springframework.org/browse/SPR-4419
 * @author Oliver Gierke
 */
@Controller
@RequestMapping("/umt/**")
public class UmtRestController {

    private UserManagement userManagement;
    private UmtDtoAssembler dtoAssembler;


    /**
     * Setter to inject dependency {@link UserManagement} .
     * 
     * @param userManagement the userManagement to set
     */
    @Required
    public void setUserManagement(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    /**
     * @param dtoAssembler the dtoAssembler to set
     */
    public void setDtoAssembler(UmtDtoAssembler dtoAssembler) {

        this.dtoAssembler = dtoAssembler;
    }


    /**
     * Maps method {@code #getUsers} to {@value UmtUrls#LIST_USERS}.
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "users", method = { RequestMethod.GET })
    public UsersDto getUsers(HttpServletResponse response,
            HttpServletRequest request) throws IOException {

        List<User> users = userManagement.getUsers();
        return dtoAssembler.toDto(users, request);
    }


    /**
     * Receives HTTP requests to retrieve a user.
     * 
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "users/{id}", method = { RequestMethod.GET })
    public UserDto getUser(@PathVariable("id") User user,
            HttpServletResponse response, HttpServletRequest request)
            throws IOException {

        if (null == user) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return dtoAssembler.toDto(user, request);
    }


    /**
     * Receives HTTP requests to create users.
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "users", method = RequestMethod.POST)
    public void createUser(@RequestBody UserDto userDto,
            HttpServletRequest request, HttpServletResponse response) {

        User user = dtoAssembler.toDomainObject(userDto);

        if (null == user) {
            throw new NoContentException();
        }

        // TODO - this is not a save operation as we have unique fields on the
        // User class. Thus we somehow have to check the relevant values and
        // return 409 CONFLICT then
        userManagement.save(user);

        UrlUtils.markCreated("/umt/users", request, response, user);
    }


    /**
     * Updates a particular {@link User}. Returns 404 if the given id was not
     * found.
     * 
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value = "users/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable("id") User user,
            @RequestBody UserDto userDto, HttpServletRequest request,
            HttpServletResponse response) {

        if (null == user) {

            response.setStatus(SC_NOT_FOUND);
            return;
        }

        user = dtoAssembler.toDomainObject(userDto, user);

        userManagement.save(user);
    }


    /**
     * Receives HTTP requests to delete user.
     * 
     * @param id
     */
    @RequestMapping(value = "users/{id}", method = { RequestMethod.DELETE })
    @ResponseBody
    public void deleteUser(@PathVariable("id") Long id,
            HttpServletResponse response) {

        try {
            userManagement.deleteUser(id);

            response.setStatus(SC_OK);

        } catch (UserNotFoundException e) {
            // Silently ignore the exception as HTTP DELETE is considered to be
            // idempotent
        }
    }
}
