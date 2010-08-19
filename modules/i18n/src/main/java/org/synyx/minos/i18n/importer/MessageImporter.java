/**
 * 
 */
package org.synyx.minos.i18n.importer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.Resource;


/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class MessageImporter {

    private MessageMetaAcceptor target;

    private Map<String, Resource> resources;


    public MessageImporter(MessageMetaAcceptor target) {

        super();
        this.target = target;
    }


    public void importMessages(String basename, Resource resource) {

        Map<String, String> messages = loadProperties(resource);

        target.setMessages(basename, messages);

    }


    /**
     * Loads {@link Map} of Key=Value pairs from the given {@link Resource} while handling errors.
     */
    private Map<String, String> loadProperties(Resource resource) {

        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(resource.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException("Could not load messages from " + resource.toString() + ": " + e.getMessage(), e);
        }

        Map<String, String> messages = new HashMap<String, String>(properties.size());

        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) properties.getProperty(key);
            messages.put(key, value);
        }

        return messages;

    }


    public void importMessages() {

        for (String resourceName : resources.keySet()) {
            importMessages(resourceName, resources.get(resourceName));
        }

    }


    public Map<String, Resource> getResources() {

        return resources;
    }


    public void setResources(Map<String, Resource> resources) {

        this.resources = resources;
    }

}
