package org.synyx.minos.cmt.service;

import org.synyx.minos.cmt.domain.Address;


/**
 * @author David Gran - gran@synyx.de
 */
public interface AddressManagement {

    /**
     * Saves an given address.
     * 
     * @param address The address to save
     * @return The saved address
     */
    public Address save(Address address);


    /**
     * Deletes a given address.
     * 
     * @param address
     */
    public void delete(Address address);


    /**
     * Get the matching address by the given id.
     * 
     * @param id The id of the address.
     * @return The retrieved address.
     */
    public Address getAddress(Long id);
}