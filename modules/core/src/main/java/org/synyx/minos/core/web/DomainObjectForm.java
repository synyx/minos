package org.synyx.minos.core.web;

/**
 * Interface to expect a form for a domain object to be able to return a domain
 * object instance.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface DomainObjectForm<T> {

    /**
     * Returns a domain object from the form content.
     * 
     * @return
     */
    public T getDomainObject();


    /**
     * Maps the current form data to the given domain object.
     * 
     * @param user
     * @return
     */
    public T mapProperties(T domainObject);
}
