package org.synyx.minos.core.web.tags.menu;

/**
 * Default implementation of MenuRenderer
 *
 * @author David Linsin
 * @version 0.0.1
 * @since 0.0.1
 */
public class DefaultMenuRenderer implements MenuRenderer {

    private Integer levels = 0;
    private boolean alwaysRenderSubmenus = false;
    private String rootId = null;
    int remainingLevel = 0;

    @Override
    public String beforeMenu(MenuMetaInfo info) {

        remainingLevel = levels;

        StringBuilder builder = new StringBuilder();
        builder.append("<div id='");

        // unless id provided default to "menu" as id
        if (info.getId() != null) {
            builder.append(rootId);
        } else {
            builder.append("menu");
        }

        builder.append("'><ul class=\"menu\">");

        return builder.toString();
    }


    @Override
    public String afterMenu(MenuMetaInfo info) {

        return "</ul></div>";
    }


    @Override
    public String beforeMenuItem(MenuMetaInfo info) {

        if (info != null && info.isSubMenu()) {
            return "<ul class='submenu'>";
        }

        return "";
    }


    @Override
    public String afterMenuItem(MenuMetaInfo info) {

        if (info != null && info.isSubMenu()) {
            return "</ul>";
        }

        return "";
    }


    @Override
    public String renderItem(MenuMetaInfo info) {

        if (info != null && info.getUrl() != null) {
            String aClass = info.isActive() ? " class='active'" : "";

            return String.format("<li%s><a id='%s' href='%s' title='%s'%s>%s</a></li>", aClass, info.getId(),
                    info.getUrl(), info.getDescription(), aClass, info.getTitle());
        }

        return "";
    }


    @Override
    public boolean proceedWithRenderingSubmenus(MenuMetaInfo info) {

        if (!info.isSubMenu() && (info.isActive() || isAlwaysRenderSubmenus())
                && (isLevelRestrictionActive() || remainingLevel > 1)) {
            remainingLevel--;

            return info.isParent();
        }

        return false;
    }


    public boolean isAlwaysRenderSubmenus() {

        return alwaysRenderSubmenus;
    }


    public void setAlwaysRenderSubmenus(boolean argAlwaysRenderSubmenus) {

        alwaysRenderSubmenus = argAlwaysRenderSubmenus;
    }


    public String getRootId() {

        return rootId;
    }


    public void setRootId(String argRootId) {

        rootId = argRootId;
    }


    public Integer getLevels() {

        return levels;
    }


    public void setLevels(Integer argLevels) {

        levels = argLevels;
    }


    /**
     * If the attribute levels is set, the tag renders submenues until the given level. This method checks if the
     * level-feature is deactivated : if <minos:menu levels="0"> is used, the number of levels rendered are unlimited.
     * This is also true for negative values of levels.
     *
     * @return
     */
    private boolean isLevelRestrictionActive() {

        return levels <= 0;
    }
}
