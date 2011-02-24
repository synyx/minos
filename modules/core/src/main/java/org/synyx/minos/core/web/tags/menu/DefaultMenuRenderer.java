package org.synyx.minos.core.web.tags.menu;

/**
 * Default implementation of MenuRenderer
 *
 * @author David Linsin
 * @version 0.0.1
 * @since 0.0.1
 */
public class DefaultMenuRenderer implements MenuRenderer {

    @Override
    public String beforeMenu(MenuMetaInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div id='");

        // unless id provided default to "menu" as id
        if (info.getId() != null) {
            builder.append(info.getId());
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
        if (info != null) {
            String aClass = info.isActive() ? " class='active'" : "";
            return String.format("<li%s><a id='%s' href='%s' title='%s'%s>%s</a></li>", aClass, info.getId(), info.getUrl(), info.getDescription(), aClass, info.getTitle());
        }
        return "";
    }
}
