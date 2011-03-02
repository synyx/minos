package org.synyx.minos.core.remoting.rest;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.XmlMappingException;

import org.synyx.hera.core.PluginRegistry;

import java.io.IOException;

import javax.xml.transform.Result;


/**
 * {@link Marshaller} implementation that delegates to a {@link ModuleAwareMarshaller} depending on the type to be
 * marshalled.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class DispatchingMarshaller implements Marshaller {

    private final PluginRegistry<ModuleAwareMarshaller, Class<?>> marshallers;

    /**
     * Creates a new {@link DispatchingMarshaller}.
     */
    public DispatchingMarshaller(PluginRegistry<ModuleAwareMarshaller, Class<?>> marshallers) {

        this.marshallers = marshallers;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.oxm.Marshaller#marshal(java.lang.Object, javax.xml.transform.Result)
     */
    @Override
    public void marshal(Object graph, Result result) throws IOException, XmlMappingException {

        marshallers.getPluginFor(graph.getClass()).marshal(graph, result);
    }


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.oxm.Marshaller#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return marshallers.hasPluginFor(clazz);
    }
}
