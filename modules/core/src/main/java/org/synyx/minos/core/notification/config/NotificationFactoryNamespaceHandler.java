package org.synyx.minos.core.notification.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@code NamespaceHandler} for notification-factory.xsd
 *
 * @author David Linsin - linsin@synyx.de
 */
public class NotificationFactoryNamespaceHandler extends NamespaceHandlerSupport {
    static final String NOTIFICATION_FACTORY = "notification-factory";

    /**
     * Registers {@link NotificationFactoryBeanDefintionParser} under the name denoted by
     * {@code NOTIFICATION_FACTORY} constant
     */
    public void init() {
        registerBeanDefinitionParser(NOTIFICATION_FACTORY, new NotificationFactoryBeanDefintionParser());
    }
}
