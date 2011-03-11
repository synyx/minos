package org.synyx.minos.umt.web;

import org.synyx.minos.umt.service.UserManagement;

import java.beans.PropertyEditorSupport;


/**
 * @author Oliver Gierke - gierke@synyx.de
 */
public class UserEditor extends PropertyEditorSupport {

    private UserManagement userManagement;

    public UserEditor(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    @Override
    public void setAsText(String text) {

        Long id = Long.valueOf(text);
        setValue(userManagement.getUser(id));
    }
}
