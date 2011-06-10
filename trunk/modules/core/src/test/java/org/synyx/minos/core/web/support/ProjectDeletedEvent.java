package org.synyx.minos.core.web.support;

import org.synyx.minos.core.web.event.Event;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ProjectDeletedEvent implements Event {

    private String name;

    /**
     * @param name
     */
    public ProjectDeletedEvent(String name) {

        super();
        this.name = name;
    }

    @Override
    public String toString() {

        return getClass().getSimpleName() + ": " + name;
    }
}
