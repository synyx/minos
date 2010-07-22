package org.synyx.minos.core.web.event;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


/**
 * Context to carry a common {@code ModelAndView} instance through an orchestration of {@code EventHandler} executions.
 * Allows registering already executed {@code Controller} instances to avoid circular execution references.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EventContext {

    /**
     * @uml.property name="request"
     */
    private HttpServletRequest request;
    /**
     * @uml.property name="response"
     */
    private HttpServletResponse response;

    /**
     * @uml.property name="modelAndView"
     */
    private ModelAndView modelAndView;

    private List<Object> visitedControllers;


    /**
     * Constructor of {@code EventContext}.
     * 
     * @param request
     * @param response
     * @param modelAndView
     */
    public EventContext(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {

        this.request = request;
        this.response = response;
        this.modelAndView = modelAndView;

        this.visitedControllers = new ArrayList<Object>();
    }


    /**
     * Returns the current {@code HttpServletRequest} .
     * 
     * @return the request
     * @uml.property name="request"
     */
    public HttpServletRequest getRequest() {

        return request;
    }


    /**
     * Returns the current {@code HttpServletResponse} .
     * 
     * @return the response
     * @uml.property name="response"
     */
    public HttpServletResponse getResponse() {

        return response;
    }


    /**
     * Returns the {@code ModelAndView} instance associated with the context.
     * 
     * @return the modelAndView
     * @uml.property name="modelAndView"
     */
    public ModelAndView getModelAndView() {

        return modelAndView;
    }


    /**
     * Returns whether the given controller has already been visited.
     * 
     * @param controller
     * @return
     */
    public boolean alreadyVisited(Object controller) {

        return visitedControllers.contains(controller);
    }


    /**
     * Marks the given controller as visited.
     * 
     * @param controller
     */
    public void visited(Object controller) {

        this.visitedControllers.add(controller);
    }

}
