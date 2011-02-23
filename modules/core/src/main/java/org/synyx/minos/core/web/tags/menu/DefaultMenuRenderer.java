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
        if (info != null && info.isSubMenu()) {
            return "<ul class='submenu'>";
        }
        return "";
    }

    @Override
    public String afterMenu(MenuMetaInfo info) {
        if (info != null && info.isSubMenu()) {
            return "</ul>";
        }
        return "";
    }

    @Override
    public String beforeMenuItem(MenuMetaInfo info) {
        return "";
    }

    @Override
    public String afterMenuItem(MenuMetaInfo info) {
        return "</li>";
    }

    @Override
    public String renderItem(MenuMetaInfo info) {
        if (info != null) {
            String aClass = info.isActive() ? " class='active'" : "";
            return String.format("<li%s><a id='%s' href='%s' title='%s'%s>%s</a>", aClass, info.getId(), info.getUrl(), info.getDescription(), aClass, info.getTitle());
        }
        return "";
    }
}
