package org.synyx.minos.umt.web;

import org.synyx.minos.umt.service.UserManagement;

import java.beans.PropertyEditorSupport;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UserEditor extends PropertyEditorSupport {

    private UserManagement userManagement;

    /**
     *
     */
    public UserEditor(UserManagement userManagement) {

        this.userManagement = userManagement;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        Long id = Long.valueOf(text);
        setValue(userManagement.getUser(id));
    }
}
