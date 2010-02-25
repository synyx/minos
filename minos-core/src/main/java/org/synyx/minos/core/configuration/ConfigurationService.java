package org.synyx.minos.core.configuration;

import org.synyx.minos.core.domain.User;


/**
 * Simple interface for a service providing access to configuration data. It
 * allows storing and retrieving user specific configuration values.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ConfigurationService {

    /**
     * Returns the config value for the given config id of the given
     * {@link User}.
     * 
     * @param <T>
     * @param config
     * @param user
     * @return
     */
    <T> T getConfigValue(String config, User user);


    /**
     * Sets a config value for the configuration option defined by the given key
     * for the given {@link User}.
     * 
     * @param <T>
     * @param config
     * @param user
     * @param value
     */
    <T> void setConfigValue(String config, User user, T value);

}