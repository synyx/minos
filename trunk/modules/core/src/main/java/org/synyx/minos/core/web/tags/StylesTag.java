package org.synyx.minos.core.web.tags;

import org.springframework.web.servlet.tags.form.AbstractHtmlElementTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import java.util.Set;

import javax.servlet.jsp.JspException;


/**
 * TODO: Aufhuebschen...
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class StylesTag extends AbstractHtmlElementTag {

    private static final long serialVersionUID = -5181967572325213615L;

    private static final String CSS_FOLDER = "/css";
    private static final String EXTENSION = "css";

    @Override
    @SuppressWarnings("unchecked")
    protected int writeTagContent(TagWriter tagWriter) throws JspException {

        Set<String> paths = pageContext.getServletContext().getResourcePaths(CSS_FOLDER);

        for (String path : paths) {
            if (!path.endsWith(EXTENSION)) {
                continue;
            }

            tagWriter.startTag("link");
            tagWriter.writeAttribute("type", "text/css");
            tagWriter.writeAttribute("href", getUrl(path));
            tagWriter.writeAttribute("rel", "stylesheet");
            tagWriter.writeAttribute("title", "default");
            tagWriter.endTag();
        }

        return 0;
    }


    private String getUrl(String path) {

        StringBuilder builder = new StringBuilder();
        builder.append(pageContext.getServletContext().getContextPath());
        builder.append(path);

        return builder.toString();
    }
}
