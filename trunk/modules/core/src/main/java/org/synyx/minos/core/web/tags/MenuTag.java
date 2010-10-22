package org.synyx.minos.core.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.menu.Menu;
import org.synyx.minos.core.web.menu.MenuItem;
import org.synyx.minos.core.web.menu.MenuItems;
import org.synyx.minos.core.web.menu.MenuProvider;


/**
 * Tag rendering the applications menu using {@link MenuProvider} to get the needed {@link MenuItems}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MenuTag extends RequestContextAwareTag {

    private static final long serialVersionUID = 3562560895559874960L;

    private Integer levels = 0;

    private String menuId = "MAIN";

    private boolean alwaysRenderSubmenus = false;

    private String id = null;


    /*
     * (non-Javadoc)
     * 
     * @seeorg.springframework.web.servlet.tags.RequestContextAwareTag# doStartTagInternal()
     */
    @Override
    protected int doStartTagInternal() throws Exception {

        MenuProvider menuProvider = getMenuProvider();
        if (menuProvider == null) {
            return 0;
        }

        MenuItems menuItems = menuProvider.getMenu(getMenuId());

        if (menuItems.isEmpty()) {
            return 0;
        }

        StringBuilder builder = new StringBuilder();
        buildHtmlMenu(menuItems, builder, false, levels);

        if (0 != builder.length()) {

            JspWriter out = pageContext.getOut();

            out.append("<div id='");

            // unless id provided default to "menu" as id
            if (id != null) {
                out.append(id);
            } else {
                out.append("menu");
            }

            out.append("'><ul class=\"menu\">");
            out.append(builder.toString());
            out.append("</ul></div>");
        }

        return 0;
    }


    /**
     * Builds an HTML menu from the given {@link MenuItem}s. Traverses the items down to the leafs, if they contain
     * submenues.
     * 
     * @param menuItems
     * @param builder
     * @throws IOException
     */
    private void buildHtmlMenu(MenuItems menuItems, StringBuilder builder, boolean submenu, Integer levelsRemaining)
            throws IOException {

        String path = getPathWithinApplication(getRequest());

        for (Menu item : menuItems) {

            String url = item.getUrl();
            if (url == null) {
                continue;
            }
            String id = item.getTitle().replace('.', '_');

            if (submenu) {

                boolean isActive = item.isActiveFor(path);

                String aClass = isActive ? " class='active'" : "";

                builder.append(String.format("<li%s><a id='%s' href='%s' title='%s'%s>%s</a>", "", id, UrlUtils.toUrl(
                        url, getRequest()), resolveMessage(item.getDesciption()), aClass, resolveMessage(item
                        .getTitle())));

            } else {

                boolean isActive = item.isActiveFor(path);

                String aClass = isActive ? " class='active'" : "";

                builder.append(String.format("<li%s><a id='%s' href='%s' title='%s'%s>%s</a>", aClass, id, UrlUtils
                        .toUrl(url, getRequest()), resolveMessage(item.getDesciption()), aClass, resolveMessage(item
                        .getTitle())));

                if ((isActive || alwaysRenderSubmenus) && (isLevelRestrictionActive() || levelsRemaining > 1)) {

                    if (item.hasSubMenues()) {

                        builder.append("<ul class='submenu'>");
                        buildHtmlMenu(item.getSubMenues(), builder, true, levelsRemaining - 1);
                        builder.append("</ul>");

                    }
                }
            }

            builder.append("</li>");
        }
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


    private String getPathWithinApplication(HttpServletRequest request) {

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        return uri.substring(contextPath.length() + servletPath.length(), uri.length());
    }


    /**
     * Resolves the given resource bundle key to a localized message. Returns the key if no message can be found.
     * 
     * @param key
     * @return
     */
    private String resolveMessage(String key) {

        return getApplicationContext().getMessage(key, null, getRequestContext().getLocale());
    }


    /**
     * Returns the current {@link HttpServletRequest}.
     * 
     * @return
     */
    private HttpServletRequest getRequest() {

        return (HttpServletRequest) this.pageContext.getRequest();
    }


    /**
     * Returns the {@link WebApplicationContext}.
     * 
     * @return
     */
    private WebApplicationContext getApplicationContext() {

        return getRequestContext().getWebApplicationContext();
    }


    private MenuProvider getMenuProvider() {

        try {
            return getApplicationContext().getBean("menuProvider", MenuProvider.class);
        } catch (RuntimeException ex) {
            return null;
        }

    }


    public Integer getLevels() {

        return levels;
    }


    public void setLevels(Integer levels) {

        this.levels = levels;
    }


    /**
     * Set whether submenus should always be rendered. Standard is 'false'. If false submenus are only rendered for
     * active menu items. True is useful for dropdown-submenus.
     */
    public void setAlwaysRenderSubmenus(boolean alwaysRenderSubmenus) {

        this.alwaysRenderSubmenus = alwaysRenderSubmenus;
    }


    @Override
    public void setId(String id) {

        this.id = id;
    }


    public void setMenuId(String menuId) {

        this.menuId = menuId != null ? menuId : "MAIN";
    }


    public String getMenuId() {

        return menuId;
    }

}
