package org.synyx.minos.support.remoting;

import org.springframework.beans.factory.InitializingBean;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;


/**
 * JAXB {@link NamespacePrefixMapper} to create nice namespace prefixes on XML
 * marshalling. The class allows you to configure an unprefixed namespace by
 * specifying either a module name or the entire namespace URI. The module name
 * will be resolved using the configured {@code namespaceTemplate} which
 * defaults to {@value #DEFAULT_NAMESPACE_TEMPLATE}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosNamespacePrefixMapper extends NamespacePrefixMapper implements
        InitializingBean {

    private static final String DEFAULT_NAMESPACE_TEMPLATE =
            "http://schemas.synyx.de/minos/%s/rest";

    private static final String PLACEHOLDER = "%s";
    private static final String DELIMITER = "/";

    private String unprefixedNamespace;

    private String namespaceTemplate = DEFAULT_NAMESPACE_TEMPLATE;
    private String unprefixedModuleName;

    private int modulePosition = 0;


    /**
     * Creates a new {@link MinosNamespacePrefixMapper}.
     */
    public MinosNamespacePrefixMapper() {

        initModulePosition();
    }


    /**
     * Returns the namespace to be unprefixed, independent from the way he was
     * configured.
     * 
     * @return
     */
    private String getUnprefixedNamespace() {

        return null == unprefixedNamespace ? String.format(namespaceTemplate,
                unprefixedModuleName) : unprefixedNamespace;
    }


    /**
     * Sets the namespace that shall not be prefixed. This will reset any
     * confiurations made to {@link #setUnprefixedModuleName(String)} and
     * {@link #setNamespaceTemplate(String)}.
     * 
     * @param unprefixedNamespace the unprefixedNamespace to set
     */
    public void setUnprefixedNamespace(String unprefixedNamespace) {

        this.unprefixedNamespace = unprefixedNamespace;
    }


    /**
     * Setter to configure the namespace template to be used when
     * {@link #setUnprefixedModuleName(String)} is to be configured. Defaults to
     * {@value #DEFAULT_NAMESPACE_TEMPLATE}.
     * <p>
     * The configured template will be used to construct the unprefixed
     * namespace by using the module name and inject it into the template
     * string. Setting the template to {@code null} will reset the default
     * template
     * 
     * @param namespaceTemplate the namespaceTemplate to set
     */
    public void setNamespaceTemplate(String namespaceTemplate) {

        this.namespaceTemplate =
                null == namespaceTemplate ? DEFAULT_NAMESPACE_TEMPLATE
                        : namespaceTemplate;

        initModulePosition();
    }


    /**
     * Sets the module that should not be prefixed. Will be used in conjunction
     * with {@code namespaceTemplate}.
     * 
     * @param unprefixedModuleName the unprefixedModuleName to set
     */
    public void setUnprefixedModuleName(String unprefixedModuleName) {

        this.unprefixedNamespace = null;
        this.unprefixedModuleName = unprefixedModuleName;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sun.xml.bind.marshaller.NamespacePrefixMapper#getPreferredPrefix(
     * java.lang.String, java.lang.String, boolean)
     */
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion,
            boolean requirePrefix) {

        if (namespaceUri.equals(getUnprefixedNamespace())) {
            return "";
        }

        String[] parts = namespaceUri.split(DELIMITER);
        return parts[modulePosition];
    }


    /**
     * Detects the module placeholder position from the template. This will be
     * used to extract the prefix for all namespace URIs that are not
     * unprefixed.
     */
    private void initModulePosition() {

        String[] templateParts = namespaceTemplate.split(DELIMITER);

        for (int i = 0; i < templateParts.length; i++) {
            if (PLACEHOLDER.equals(templateParts[i])) {
                this.modulePosition = i;
                return;
            }
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {

        if (null == unprefixedModuleName && null == unprefixedNamespace) {
            throw new IllegalStateException(
                    "Either module name or namespace have to be configured!");
        }
    }
}
