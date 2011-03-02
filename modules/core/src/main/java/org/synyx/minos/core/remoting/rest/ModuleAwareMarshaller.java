package org.synyx.minos.core.remoting.rest;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import org.synyx.hera.core.Plugin;


/**
 * Simple collecting interface to create a {@link Marshaller} and {@link Unmarshaller} that can be used as
 * {@link Plugin}.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface ModuleAwareMarshaller extends Plugin<Class<?>>, Marshaller, Unmarshaller {
}
