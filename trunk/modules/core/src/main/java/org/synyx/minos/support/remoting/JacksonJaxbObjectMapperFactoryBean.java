package org.synyx.minos.support.remoting;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.springframework.beans.factory.FactoryBean;


/**
 * {@link FactoryBean} to create a JAXB annotation aware Jackson {@link ObjectMapper}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class JacksonJaxbObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public ObjectMapper getObject() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();

        mapper.getSerializationConfig().setAnnotationIntrospector(introspector);
        mapper.getDeserializationConfig().setAnnotationIntrospector(introspector);

        return mapper;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<? extends ObjectMapper> getObjectType() {

        return ObjectMapper.class;
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton() {

        return true;
    }
}
