package org.synyx.minos.core.message;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.core.Ordered;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * {@link MessageSourcePlugin} that determines its usage by inspecting the given message code prefix. If it is
 * configured without any prefix it all it will always be invoked for message resolution.
 *
 * <p>{@link PrefixAwareMessageSource}s implement {@link Ordered} to allow decision which MessageSource shall try to
 * resolve messages first.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class PrefixAwareMessageSource extends ResourceBundleMessageSource implements MessageSourcePlugin, Ordered {

    private int order;
    private List<String> prefixes = new ArrayList<String>();

    /** Creates a new {@link PrefixAwareMessageSource}. Sets the basename to {@code messages} by default. */
    public PrefixAwareMessageSource() {

        setBasename("messages");
    }

    /**
     * Inject all prefixes the {@link MessageSource} handles messages for. If this is set, the {@link MessageSource}
     * will only be considered when resolving keys beginning with one of the given prefixes.
     *
     * @param  prefixes  the prefixes this {@link MessageSource} handles messages for
     */
    public void setPrefixes(List<String> prefixes) {

        this.prefixes = null == prefixes ? new ArrayList<String>() : prefixes;
    }


    /**
     * Inject the prefix the {@link MessageSource} handles messages for in case it handles only one. Setting
     * {@literal null} will reset the {@link MessageSource} to support all prefixes.
     *
     * @param  prefix  the prefix this {@link MessageSource} handles messages for
     */
    public void setPrefix(String prefix) {

        this.prefixes = StringUtils.hasText(prefix) ? Arrays.asList(prefix) : new ArrayList<String>();
    }


    @Override
    public int getOrder() {

        return order;
    }


    /**
     * Set the order this {@link MessageSource} should have in case there are multiple ones. This will result in
     * {@link MessageSource} with a higher order (lower numbers) to be tried first to resolve a message.
     *
     * @param  order  the order to set
     */
    public void setOrder(int order) {

        this.order = order;
    }


    @Override
    public boolean supports(String prefix) {

        if (prefixes.isEmpty()) {
            return true;
        }

        return prefixes.contains(prefix);
    }
}
