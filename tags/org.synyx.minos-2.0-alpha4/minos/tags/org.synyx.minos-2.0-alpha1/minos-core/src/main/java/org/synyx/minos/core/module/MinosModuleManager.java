package org.synyx.minos.core.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Module manager to notify {@link Lifecycle}s of the appropriate application
 * events. Will consider the order of {@link Lifecycle} implementatinos if the
 * user {@link Order} or implement {@link Ordered}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosModuleManager implements
        ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware,
        DisposableBean {

    private static final Log LOG = LogFactory.getLog(MinosModuleManager.class);

    private static final Comparator<Object> ORDER_COMPARATOR =
            new AnnotationAwareOrderComparator();

    private ApplicationContext context;
    private List<Lifecycle> lifecycles = Collections.emptyList();


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        this.context = applicationContext;
    }


    /**
     * @param lifecycles the lifecycles to set
     */
    public void setLifecycles(List<Lifecycle> lifecycles) {

        this.lifecycles = new ArrayList<Lifecycle>(lifecycles);
        Collections.sort(this.lifecycles, ORDER_COMPARATOR);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationListener#onApplicationEvent(org
     * .springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (isStartEvent(event)) {

            authenticateAsAdmin();

            for (Lifecycle lifecycle : lifecycles) {

                LOG.info("Starting module: " + lifecycle);
                lifecycle.onStart();
            }

            revokeAuthentication();
        }

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {

        List<Lifecycle> copy = new ArrayList<Lifecycle>(lifecycles);
        Collections.reverse(copy);

        authenticateAsAdmin();

        for (Lifecycle lifecycle : copy) {
            lifecycle.onStop();
        }

        revokeAuthentication();
    }


    /**
     * Returns whether the given {@link ApplicationEvent} is actually from the
     * {@link ApplicationContext} that contains the {@link MinosModuleManager}.
     * 
     * @param applicationEvent
     * @return
     */
    private boolean isStartEvent(ContextRefreshedEvent event) {

        return event.getApplicationContext().equals(context);
    }


    /**
     * Authenticates the installer as admin.
     */
    private void authenticateAsAdmin() {

        List<GrantedAuthority> authorities =
                Arrays.asList((GrantedAuthority) new GrantedAuthorityImpl(
                        "ROLE_ADMIN"));

        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(null, null,
                                authorities));
    }


    /**
     * Revoke the authentication.
     */
    private void revokeAuthentication() {

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
