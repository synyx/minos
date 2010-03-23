package org.synyx.minos.cmt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.synyx.hades.domain.AbstractPersistable;

/**
 * Represents an address book.
 * 
 * @author Oliver Gierke
 */
@Entity
public class AddressBook extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -6752421387835581822L;

	private String name;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	private List<Contact> contacts;

	public AddressBook() {

		this.contacts = new ArrayList<Contact>();
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return the contacts
	 */
	public List<Contact> getContacts() {

		return contacts;
	}

	/**
	 * @param contacts
	 *            the contacts to set
	 */
	public void setContacts(List<Contact> contacts) {

		this.contacts = contacts;
	}

	/**
	 * Adds a person as contact.
	 * 
	 * @param person
	 */
	public void addContact(Person person) {

		this.contacts.add(person);
		person.getAddressBooks().add(this);
	}

	/**
	 * Adds a organisation to the contacts. Automatically adds all registered
	 * members to the contacts to. Does not add an member, if the contact is
	 * already in the address book avoiding duplicates.
	 * 
	 * @param organisation
	 */
	public void addContact(Organisation organisation) {

		// Add company itself
		this.contacts.add(organisation);

		// Add members if not already available
		List<Contact> members = organisation.getMembers();

		for (Contact member : members) {
			if (!contacts.contains(member)) {
				this.contacts.add(member);
			}
		}
	}

	/**
	 * Removes a contact from the contacts list.
	 * 
	 * @param contact
	 */
	public void removeContact(Contact contact) {

		if (this.contacts.contains(contact)) {

			contact.getAddressBooks().remove(this);
			this.contacts.remove(contact);
		}
	}
}
