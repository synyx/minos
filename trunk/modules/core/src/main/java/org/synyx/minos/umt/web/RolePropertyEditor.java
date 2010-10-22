package org.synyx.minos.umt.web;

import java.beans.PropertyEditorSupport;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.umt.service.UserManagement;


/**
 * {@link java.beans.PropertyEditor} to map {@link Role}s to {@link String} representations. Expects the {@link String}
 * representation to be the id of the {@link Role} actually.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class RolePropertyEditor extends PropertyEditorSupport {

    private UserManagement userManagement;


    /**
     * Creates a new {@link RolePropertyEditor}.
     * 
     * @param userManagement
     */
    public RolePropertyEditor(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String value) throws IllegalArgumentException {

        setValue(userManagement.getRole(Long.parseLong(value)));
    }
}
