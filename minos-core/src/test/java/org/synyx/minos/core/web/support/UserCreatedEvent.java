package org.synyx.minos.core.web.support;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.event.Event;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UserCreatedEvent implements Event {

    private User user;


    /**
     * @param name
     */
    public UserCreatedEvent(User user) {

        super();
        this.user = user;
    }


    /**
     * @return the user
     */
    public User getUser() {

        return user;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        // TODO Auto-generated method stub
        return getClass().getSimpleName() + ": " + user.getEmailAddress();
    }

}
