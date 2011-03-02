package org.synyx.minos.core.web.event;

/**
 * Interface for handlers, that can deal with an event.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface EventHandler<T extends Event> {

    /**
     * Central handler triggering method.
     *
     * @param event
     * @param context
     * @throws Exception
     */
    void handleEvent(T event, EventContext context) throws Exception;


    /**
     * Returns, if the event handler is interested in certain types of events.
     *
     * @param eventClazz
     * @return
     */
    boolean supports(Class<? extends T> eventClazz);
}
