/**
 * 
 */
package org.synyx.minos.cmt.domain;

import org.synyx.minos.cmt.domain.AddressBook;
import org.synyx.minos.cmt.domain.Person;

import junit.framework.TestCase;


/**
 * @author Oliver Gierke
 */
public class ContactUnitTest extends TestCase {

    private Person person;
    private AddressBook addressBook;


    public void testAssignmentToAddressBook() {

        person = new Person();
        person.setFirstname("Oliver");
        person.setLastname("Gierke");

        addressBook = new AddressBook();
        addressBook.addContact(person);

        assertTrue(person.isAssignedToAddressBook());

        addressBook.removeContact(person);
        assertFalse(person.isAssignedToAddressBook());
    }
}
