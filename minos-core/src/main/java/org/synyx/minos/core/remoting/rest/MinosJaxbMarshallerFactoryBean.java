package org.synyx.minos.core.remoting.rest;

import static javax.xml.bind.Marshaller.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.synyx.hera.core.Plugin;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.support.remoting.MinosNamespacePrefixMapper;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;


/**
 * {@link FactoryBean} to create JAXB marshallers aligned to Minos conventions.
 * This class primarily acts as configuration helper to avoid tedious
 * repeatition.
 * <p>
 * This {@link FactoryBean} will register a {@link Jaxb2Marshaller} with the
 * following configuration:
 * <ul>
 * <li>contextPath - <code>${module.basePackage}.remoting.rest.dto</code></li>
 * <li>properties - formatted UTF-8 output, custom {@link NamespacePrefixMapper}
 * </li>
 * </ul>
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosJaxbMarshallerFactoryBean extends
        AbstractFactoryBean<ModuleAwareMarshaller> implements Plugin<String> {

    private static final String PREFIX_MAPPER_PROPERTY =
            "com.sun.xml.bind.namespacePrefixMapper";
    private static final String DEFAULT_DTO_SUB_PACKAGE = "remoting.rest.dto";
    private static final String DEFAULT_SCHEMA_TEMPLATE =
            "remoting/rest/%s-remoting.xsd";
    private static final Map<String, Object> PROPERTIES =
            new HashMap<String, Object>();

    static {
        PROPERTIES.put(JAXB_FORMATTED_OUTPUT, true);
        PROPERTIES.put(JAXB_ENCODING, "UTF-8");
    }

    private Module module;
    private String dtoSubPackage = DEFAULT_DTO_SUB_PACKAGE;
    private String schemaTemplate = DEFAULT_SCHEMA_TEMPLATE;
    private NamespacePrefixMapper prefixMapper;


    /**
     * Set the module to derive defaults from.
     * 
     * @param module the moduleInformation to set
     */
    public void setModule(Module module) {

        this.module = module;
        this.prefixMapper =
                new MinosNamespacePrefixMapper(module.getIdentifier());
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<? extends ModuleAwareMarshaller> getObjectType() {

        return ModuleAwareMarshaller.class;
    }


    /**
     * Returns the context path for the {@link Marshaller}. This is the package
     * where the JAXB classes are located.
     * 
     * @return
     */
    private String getContextPath() {

        return String.format("%s.%s", module.getBasePackage(), dtoSubPackage);
    }


    /**
     * Returns all the schemas to configure the {@link Marshaller} with.
     * 
     * @return
     */
    private Resource[] getSchemas() {

        List<Resource> schemas = new ArrayList<Resource>();

        // Use reverted dependencies as we need to start with the very core
        // module first
        List<Module> dependencies = module.getDependants();
        Collections.reverse(dependencies);

        for (Module dependant : dependencies) {

            addSchemaFor(dependant, schemas);
        }

        addSchemaFor(module, schemas);

        return schemas.toArray(new Resource[schemas.size()]);
    }


    private void addSchemaFor(Module module, Collection<Resource> resources) {

        String schemaPath =
                String.format(schemaTemplate, module.getIdentifier());
        resources.add(module.getModuleResource(schemaPath));
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.beans.factory.config.AbstractFactoryBean#createInstance
     * ()
     */
    @Override
    protected ModuleAwareMarshaller createInstance() throws Exception {

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(getContextPath());
        marshaller.setSchemas(getSchemas());

        Map<String, Object> properties =
                new HashMap<String, Object>(PROPERTIES);
        properties.put(PREFIX_MAPPER_PROPERTY, prefixMapper);

        marshaller.setMarshallerProperties(properties);

        return new ModuleAwareMarshallerAdapter(marshaller, module);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hera.core.Plugin#supports(java.lang.Object)
     */
    @Override
    public boolean supports(String delimiter) {

        return this.module.getIdentifier().equals(delimiter);
    }
}
