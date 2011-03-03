package org.synyx.minos.core.web.tags;

import org.springframework.web.servlet.tags.form.AbstractHtmlElementBodyTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import org.synyx.minos.core.Core;
import org.synyx.minos.core.web.Message;

import javax.servlet.jsp.JspException;


/**
 * Tag to display minos system messages. Looks up {@code Message}s under {@value Core#MESSAGE} in the request and
 * renders it if found.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SystemMessageTag extends AbstractHtmlElementBodyTag {

    private static final long serialVersionUID = -3433634638159122629L;

    private static final String MESSAGES_WRAPPER = "ul";
    private static final String MESSAGE_WRAPPER = "li";

    @Override
    protected boolean shouldRender() throws JspException {

        return hasMessage();
    }


    /**
     * Returns if there is a message to be rendered.
     *
     * @return
     */
    private boolean hasMessage() {

        return null != getMessage();
    }


    /**
     * Returns the current UI message.
     *
     * @return the current UI message or {@code null} if none found
     */
    protected Message getMessage() {

        return (Message) pageContext.getRequest().getAttribute(Core.MESSAGE);
    }


    @Override
    protected void renderDefaultContent(TagWriter tagWriter) throws JspException {

        if (!hasMessage()) {
            return;
        }

        Message message = getMessage();

        tagWriter.startTag(MESSAGES_WRAPPER);
        tagWriter.writeAttribute("class", message.getCssClass());
        tagWriter.startTag(MESSAGE_WRAPPER);

        tagWriter.appendValue(getRequestContext().getMessage(message, true));

        tagWriter.endTag();
        tagWriter.endTag();
    }
}
