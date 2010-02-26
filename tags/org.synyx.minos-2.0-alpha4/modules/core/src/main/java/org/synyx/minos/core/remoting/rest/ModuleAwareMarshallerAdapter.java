package org.synyx.minos.core.remoting.rest;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.synyx.minos.core.module.Module;
import org.synyx.minos.util.Assert;


/**
 * Simple adapter class to use {@link Marshaller} and {@link Unmarshaller}
 * instance and tie them onto a {@link Module}. The {@link Module} is used to
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class ModuleAwareMarshallerAdapter implements ModuleAwareMarshaller {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    private final Module module;


    /**
     * Creates a new {@link ModuleAwareMarshallerAdapter}.
     * 
     * @param <T>
     * @param unMarshaller
     * @param module
     */
    public <T extends Marshaller & Unmarshaller> ModuleAwareMarshallerAdapter(
            T unMarshaller, Module module) {

        Assert.notNull(unMarshaller);
        Assert.notNull(module);

        this.marshaller = unMarshaller;
        this.unmarshaller = unMarshaller;

        this.module = module;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.hera.core.Plugin#supports(java.lang.Object)
     */
    @Override
    public boolean supports(Class<?> delimiter) {

        boolean marshallerSupported = marshaller.supports(delimiter);
        boolean unmarshallerSupported = unmarshaller.supports(delimiter);

        return marshallerSupported && unmarshallerSupported
                && module.isModuleType(delimiter);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.oxm.Marshaller#marshal(java.lang.Object,
     * javax.xml.transform.Result)
     */
    @Override
    public void marshal(Object graph, Result result) throws IOException,
            XmlMappingException {

        marshaller.marshal(graph, result);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.oxm.Unmarshaller#unmarshal(javax.xml.transform.Source
     * )
     */
    @Override
    public Object unmarshal(Source source) throws IOException,
            XmlMappingException {

        return unmarshaller.unmarshal(source);
    }
}
