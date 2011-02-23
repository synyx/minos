package org.synyx.minos.core.web.tags.menu;

/**
 * Simple bean encapsulating information about a Menu instance
 *
 * @author David Linsin
 * @version 0.0.1
 * @since 0.0.1
 */
public class MenuMetaInfo {
    private boolean isActive;
    private int level;
    private String id;
    private String url;
    private String title;
    private String description;
    private boolean subMenu;

    public MenuMetaInfo() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String argDescription) {
        description = argDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String argId) {
        id = argId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean argActive) {
        isActive = argActive;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int argLevel) {
        level = argLevel;
    }

    public boolean isSubMenu() {
        return subMenu;
    }

    public void setSubMenu(boolean argSubMenu) {
        subMenu = argSubMenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String argTitle) {
        title = argTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String argUrl) {
        url = argUrl;
    }
}
