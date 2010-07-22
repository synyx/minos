package org.synyx.minos.umt.web;

import java.beans.PropertyEditorSupport;

import org.synyx.minos.umt.service.UserManagement;


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
