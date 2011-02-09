package org.synyx.minos.umt.web;

/**
 * Used to determine whether a permission is checked or not. Only used for display purposes
 *
 * @author David Linsin
 * @version 0.0.1
 * @since 0.0.1
 */
public class PermissionHolder {
    private String name;
    private Boolean checked;

    public PermissionHolder(String argName, Boolean argChecked) {
        name = argName;
        checked = argChecked;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean argChecked) {
        checked = argChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String argName) {
        name = argName;
    }
}
