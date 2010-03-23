/**
 * 
 */
package org.synyx.minos.cmt.domain;

import java.util.Arrays;
import java.util.List;

import org.synyx.minos.cmt.domain.AddressBook;
import org.synyx.minos.cmt.domain.Contact;
import org.synyx.minos.cmt.domain.Organisation;
import org.synyx.minos.cmt.domain.Person;

import junit.framework.TestCase;

/**
 * @author Oliver Gierke
 */
public class AddressBookUnitTest extends TestCase {

	private Person ollie;
	private Person achim;

	private Organisation synyx;
	private AddressBook addressBook;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {

		ollie = new Person();
		ollie.setFirstname("Oliver");
		ollie.setLastname("Gierke");

		achim = new Person();
		achim.setFirstname("Achim");
		achim.setLastname("Arrasz");

		synyx = new Organisation();
		synyx.addMember(ollie);
		synyx.addMember(achim);

		addressBook = new AddressBook();
	}

	public void testAddingCompanyCorrectlyAddsEmployees() {

		addressBook.addContact(ollie);
		addressBook.addContact(synyx);

		List<Contact> contacts = addressBook.getContacts();

		assertEquals(3, contacts.size());
		assertTrue(contacts.containsAll(Arrays.asList(ollie, achim, synyx)));
	}
}
