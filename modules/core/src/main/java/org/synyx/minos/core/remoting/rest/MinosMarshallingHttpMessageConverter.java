package org.synyx.minos.core.remoting.rest;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.MarshallingFailureException;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.UnmarshallingFailureException;
import org.synyx.hera.core.PluginRegistry;
import org.synyx.minos.util.Assert;


/**
 * Custom {@link org.springframework.http.converter.HttpMessageConverter} based
 * on a {@link PluginRegistry} of {@link ModuleAwareMarshaller}s. This one will
 * select an appropriate {@link Marshaller} and {@link Unmarshaller} based on
 * the target class.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class MinosMarshallingHttpMessageConverter extends
        AbstractXmlHttpMessageConverter<Object> {

    private static final String NO_MARSHALLER_FOUND_TEMPLATE =
            "Could not find a (un)marshaller for class %s!";

    private final PluginRegistry<ModuleAwareMarshaller, Class<?>> marshallers;


    /**
     * Creates a new {@link MinosMarshallingHttpMessageConverter}.
     * 
     * @param marshallers
     */
    public MinosMarshallingHttpMessageConverter(
            PluginRegistry<ModuleAwareMarshaller, Class<?>> marshallers) {

        Assert.notNull(marshallers);
        this.marshallers = marshallers;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter
     * #readFromSource(java.lang.Class, org.springframework.http.HttpHeaders,
     * javax.xml.transform.Source)
     */

    @Override
    protected Object readFromSource(Class<Object> clazz, HttpHeaders headers,
            Source source) throws IOException {

        Unmarshaller marshaller =
                marshallers.getPluginFor(clazz,
                        new UnmarshallingFailureException(String.format(
                                NO_MARSHALLER_FOUND_TEMPLATE, clazz)));
        return marshaller.unmarshal(source);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter
     * #writeToResult(java.lang.Object, org.springframework.http.HttpHeaders,
     * javax.xml.transform.Result)
     */
    @Override
    protected void writeToResult(Object t, HttpHeaders headers, Result result)
            throws IOException {

        Class<?> type = t.getClass();

        Marshaller marshaller =
                marshallers.getPluginFor(type, new MarshallingFailureException(
                        String.format(NO_MARSHALLER_FOUND_TEMPLATE, type)));
        marshaller.marshal(t, result);
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.http.converter.AbstractHttpMessageConverter#supports
     * (java.lang.Class)
     */
    @Override
    protected boolean supports(Class<? extends Object> clazz) {

        return marshallers.hasPluginFor(clazz);
    }
}
