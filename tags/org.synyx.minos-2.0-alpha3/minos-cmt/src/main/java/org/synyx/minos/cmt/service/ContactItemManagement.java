package org.synyx.minos.cmt.service;

import org.synyx.minos.cmt.domain.ContactItem;


/**
 * @author David Gran - gran@synyx.de
 */
public interface ContactItemManagement {

    /**
     * Saves an given contactItem.
     * 
     * @param contactItem The contactItem to save
     * @return The saved contactItem
     */
    public ContactItem save(ContactItem contactItem);


    /**
     * Deletes a given contactItem.
     * 
     * @param contactItem
     */
    public void delete(ContactItem contactItem);


    /**
     * Get the matching contactItem by the given id.
     * 
     * @param id The id of the contactItem.
     * @return The retrieved contactItem.
     */
    public ContactItem getContactItem(Long id);
}
