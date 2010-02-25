package org.synyx.minos.cmt.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.apache.commons.lang.StringUtils;
import org.synyx.hades.domain.AbstractPersistable;

/**
 * Address.
 * 
 * @author Oliver Gierke
 */
@Entity
public class Address extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 3449389309171077843L;

	private String street;
	private String zipCode;
	private String city;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Contact contact;

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
	 * 
	 * @return The address to display it.
	 */
	public String getDisplayString() {
		return street + ", " + zipCode + " " + city;
	}

	/**
	 * 
	 * @return The address to display it.
	 */
	public String getDisplayString(String formatPattern) {
		if (StringUtils.isBlank(formatPattern)) {
			return getDisplayString();
		}

		formatPattern = formatPattern.replaceAll("street", street);
		formatPattern = formatPattern.replace("zipcode", zipCode);
		formatPattern = formatPattern.replace("city", city);

		return formatPattern;
	}

}
