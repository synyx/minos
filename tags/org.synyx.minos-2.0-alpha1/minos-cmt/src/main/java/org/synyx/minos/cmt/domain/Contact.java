package org.synyx.minos.cmt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.synyx.hades.domain.AbstractPersistable;

/**
 * Abstract base class for contacts owning adresses. Contacts can be assigned to
 * address books.
 * 
 * @author Oliver Gierke
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contact extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -2021490740953394494L;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<AddressBook> addressBooks;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private final List<Address> addresses;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private final List<ContactItem> contactItems;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<Organisation> organisations;

	/**
	 * Constructor of <code>Contact</code>.
	 */
	public Contact() {

		this.organisations = new ArrayList<Organisation>();
		this.addresses = new ArrayList<Address>();
		this.addressBooks = new ArrayList<AddressBook>();
		this.contactItems = new ArrayList<ContactItem>();
	}

	/**
	 * @return the addressBooks
	 */
	public List<AddressBook> getAddressBooks() {

		return addressBooks;
	}

	/**
	 * @param addressBooks
	 *            the addressBooks to set
	 */
	public void setAddressBooks(List<AddressBook> addressBooks) {

		this.addressBooks = addressBooks;
	}

	public boolean isAssignedToAddressBook() {

		return 0 != this.addressBooks.size();
	}

	/**
	 * Returns the name of the contact.
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * @return the addresses
	 */
	public List<Address> getAddresses() {

		return addresses;
	}

	/**
	 * @return the contactItems
	 */
	public List<ContactItem> getContactItems() {

		return contactItems;
	}

	/**
	 * Adds an adress to the contact.
	 * 
	 * @param address
	 */
	public void addAddress(Address address) {

		this.addresses.add(address);
	}

	/**
	 * Adds an contactItem to the contact.
	 * 
	 * @param contactItem
	 */
	public void addContactItem(ContactItem contactItem) {

		this.contactItems.add(contactItem);
	}

	/**
	 * Get all available organisations.
	 * 
	 * @return organsations
	 */
	public List<Organisation> getOrganisations() {
		return organisations;
	}

	/**
	 * Add new organisation to this person.
	 * 
	 * @param orga
	 *            The orga to add the person for.
	 */
	public void addOrganisation(Organisation orga) {
		organisations.add(orga);
	}

	/**
	 * Set the organisations of this person.
	 * 
	 * @param organisations
	 */
	public void setOrganisations(List<Organisation> organisations) {
		this.organisations = organisations;
	}
}
