package org.synyx.minos.cmt.web;

import org.synyx.minos.cmt.domain.Address;
import org.synyx.minos.cmt.domain.Contact;
import org.synyx.minos.core.web.DomainObjectForm;


/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
public class AddressForm implements DomainObjectForm<Address> {

	private String street;
	private String zipCode;
	private String city;
	private Long id;
	private Long contactId;
	private Contact contact;

	public AddressForm() {

	}

	public AddressForm(Address address) {
		this.street = address.getStreet();
		this.zipCode = address.getZipCode();
		this.city = address.getCity();
		this.id = address.getId();
		this.contact = address.getContact();
		this.contactId = contact != null ? contact.getId() : null;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {

		return street;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {

		this.street = street;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {

		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {

		this.zipCode = zipCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {

		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {

		this.city = city;
	}

	/**
	 * @return the city
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/**
	 * @return the contactId
	 */
	public Long getContactId() {
		return contactId;
	}

	/**
	 * @param contactId
	 *            the contactId to set
	 */
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getDisplayString() {
		return street + ", " + zipCode + ", " + city;
	}

	public boolean isNew() {
		return null == id;
	}

	/**
	 * 
	 */
	public Address getDomainObject() {
		Address address = new Address();

		address.setCity(city);
		address.setStreet(street);
		address.setZipCode(zipCode);
		address.setId(id);
		address.setContact(contact);

		return address;
	}

	public Address mapProperties(Address address) {
		address.setCity(city);
		address.setStreet(street);
		address.setZipCode(zipCode);
		address.setId(id);
		address.setContact(contact);

		return address;
	}

}
