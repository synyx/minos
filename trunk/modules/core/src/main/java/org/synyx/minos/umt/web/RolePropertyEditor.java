package org.synyx.minos.umt.web;

import org.synyx.minos.core.domain.Role;
import org.synyx.minos.umt.service.UserManagement;

import java.beans.PropertyEditorSupport;


/**
 * {@link java.beans.PropertyEditor} to map {@link Role}s to {@link String} representations. Expects the {@link String}
 * representation to be the id of the {@link Role} actually.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class RolePropertyEditor extends PropertyEditorSupport {

    private UserManagement userManagement;

    public RolePropertyEditor(UserManagement userManagement) {

        this.userManagement = userManagement;
    }

    @Override
    public void setAsText(String value) {

        setValue(userManagement.getRole(Long.parseLong(value)));
    }
}
