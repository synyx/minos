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


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        // TODO Auto-generated method stub
        return getClass().getSimpleName() + ": " + name;
    }

}
