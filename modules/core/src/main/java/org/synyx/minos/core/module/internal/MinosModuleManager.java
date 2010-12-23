package org.synyx.minos.core.module.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.synyx.hera.core.SimplePluginRegistry;
import org.synyx.minos.core.module.Lifecycle;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.core.module.ModuleLifecycleException;
import org.synyx.minos.core.module.ModuleManager;


/**
 * Module manager to notify {@link Lifecycle}s of the appropriate application events. Will consider the order of
 * {@link Lifecycle} implementations if they use {@link Order} or implement {@link Ordered}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosModuleManager implements ModuleManager, ApplicationContextAware, DisposableBean {

    private static final Log LOG = LogFactory.getLog(MinosModuleManager.class);

    private ApplicationContext context;
    private ModuleDescriptorDao moduleDescriptorDao;
    private List<Module> modules;
    private List<Module> startedModules;


    /**
     * Creates a new {@link MinosModuleManager} using the given {@link ModuleDescriptorDao} to persist module
     * installation information.
     * 
     * @param moduleDescriptorDao
     */
    public MinosModuleManager(ModuleDescriptorDao moduleDescriptorDao) {

        this.moduleDescriptorDao = moduleDescriptorDao;
        this.modules = new ArrayList<Module>();
        this.startedModules = new ArrayList<Module>();
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.context = applicationContext;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.ModuleManager#getModules()
     */
    @Override
    public List<Module> getModules() {

        return Collections.unmodifiableList(modules);
    }


    /**
     * @param modules the modules to set
     */
    public void setModules(List<? extends Module> modules) {

        for (Module module : modules) {

            if (module != null && !this.modules.contains(module)) {
                this.modules.add(module);
            }
        }

        // Apply natural ordering and revert it
        Collections.sort(this.modules);
        Collections.reverse(this.modules);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org
     * .springframework.context.ApplicationEvent)
     */
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (isStartEvent(event)) {

            LOG.info(String.format("Registered modules: %s", StringUtils.collectionToCommaDelimitedString(modules)));

            authenticateAsAdmin();

            installModules();
            startModules();

            revokeAuthentication();
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() {

        stopModules();
    }


    /**
     * Installs all registered modules and
     * 
     * @return
     */
    private void installModules() {

        execute(modules, new Callback<Module>() {

            @Override
            public boolean executionRequired(Module module) {

                ModuleDescriptor descriptor = getDescriptorFor(module);

                boolean alreadyInstalled = descriptor == null ? false : descriptor.isInstalled();

                if (alreadyInstalled) {
                    LOG.debug(String.format("Module %s already installed!", module));
                }

                return !alreadyInstalled;
            }


            @Override
            public void doWith(Module module) {

                ModuleDescriptor descriptor = getDescriptorFor(module);

                if (null == descriptor) {
                    descriptor = new ModuleDescriptor(module);
                }

                module.getLifecycle().install();

                descriptor.setInstalled();
                moduleDescriptorDao.save(descriptor);
            }

        }, "Installation of module %s failed!", "Installing module %s!", "Successfully installed module %s!");
    }


    /**
     * Returns the {@link ModuleDescriptor} for the given {@link Module} or {@literal null} if none found.
     * 
     * @param module
     * @return
     */
    private ModuleDescriptor getDescriptorFor(Module module) {

        String identifier = module.getIdentifier();
        return moduleDescriptorDao.findByIdentifier(identifier);
    }


    private boolean isInstalled(Module module) {

        ModuleDescriptor descriptor = getDescriptorFor(module);

        return descriptor != null && descriptor.isInstalled();
    }


    /**
     * Starts all modules.
     */
    private synchronized void startModules() {

        execute(modules, new Callback<Module>() {

            @Override
            public boolean executionRequired(Module module) {

                if (!isInstalled(module)) {
                    return false;
                }

                boolean alreadyStarted = startedModules.contains(module);

                if (alreadyStarted) {
                    LOG.debug(String.format("Module %s already started!", module));
                }

                return !alreadyStarted;
            }


            @Override
            public void doWith(Module module) {

                module.getLifecycle().onStart();

                startedModules.add(module);
            }
        }, "Starting module %s failed!", "Starting module %s", "Successfully started module %s");
    }


    /**
     * Stops all started modules in the reverse order.
     */
    private synchronized void stopModules() {

        List<Module> modulesToShutdown = new ArrayList<Module>(startedModules);
        Collections.reverse(modulesToShutdown);

        execute(modulesToShutdown, new Callback<Module>() {

            @Override
            public boolean executionRequired(Module module) {

                return startedModules.contains(module);
            }


            @Override
            public void doWith(Module module) {

                module.getLifecycle().onStop();
                startedModules.remove(module);
            }
        }, "Stopping module %s failed!", "Stopping module %s!", "Successfully stopped module %s!");
    }


    /**
     * Returns whether the given {@link ApplicationEvent} is actually from the {@link ApplicationContext} that contains
     * the {@link MinosModuleManager}.
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

        List<GrantedAuthority> authorities = Arrays.asList((GrantedAuthority) new GrantedAuthorityImpl("ROLE_ADMIN"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(null, null, authorities));
    }


    /**
     * Revoke the authentication.
     */
    private void revokeAuthentication() {

        SecurityContextHolder.getContext().setAuthentication(null);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.ModuleManager#isAvailable(java.lang.String)
     */
    @Override
    public boolean isAvailable(String identifier) {

        return SimplePluginRegistry.create(startedModules).hasPluginFor(identifier);
    }


    /**
     * Callback executing method to implement common behaviour required for all executions.
     * 
     * @param modules
     * @param callback
     * @param failureInformation
     * @param startInfo
     * @param finishInfo
     * @return all {@link Module}s the callback could be invoked successfully
     */
    private <T> void execute(Iterable<? extends T> modules, Callback<T> callback, String failureInformation,
            String startInfo, String finishInfo) {

        authenticateAsAdmin();

        for (T module : modules) {

            try {

                if (!callback.executionRequired(module)) {
                    continue;
                }

                if (null != startInfo && LOG.isDebugEnabled()) {
                    LOG.debug(String.format(startInfo, module));
                }

                callback.doWith(module);

                if (null != finishInfo && LOG.isDebugEnabled()) {
                    LOG.debug(String.format(finishInfo, module));
                }

            } catch (ModuleLifecycleException e) {
                LOG.warn(String.format(failureInformation, module), e);
            }
        }

        revokeAuthentication();
    }

    /**
     * Simpl callback interface to implement specific behaviour to applied to the handed over module.
     * 
     * @author Oliver Gierke - gierke@synyx.de
     */
    private static abstract class Callback<T> {

        public boolean executionRequired(T module) {

            return true;
        }


        public abstract void doWith(T module);
    }
}
