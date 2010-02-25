package org.synyx.minos.cmt.service;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.cmt.domain.Contact;


/**
 * @author Oliver Gierke
 */
public interface ContactManagement {

    /**
     * Deletes a given address.
     * 
     * @param contact
     */
    public void delete(Contact contact);


    /**
     * Saves an given contact.
     * 
     * @param contact The contact to save
     * @return The saved contact
     */
    public Contact save(Contact contact);


    /**
     * Find the contact matching the given id.
     * 
     * @param id The id to find the matching contact.
     * @return A contact if found one, or null, if not.
     */
    public Contact getContactById(Long id);


    /**
     * Get all available contacts.
     * 
     * @return All retrieved contacts.
     */
    public Page<Contact> getAll(Pageable pageable);
}
