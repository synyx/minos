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


    @Override
    public String toString() {

        return getClass().getSimpleName() + ": " + user.getEmailAddress();
    }
}
