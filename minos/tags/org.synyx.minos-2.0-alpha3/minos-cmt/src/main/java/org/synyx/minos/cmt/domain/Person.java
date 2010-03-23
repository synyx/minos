package org.synyx.minos.cmt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Oliver Gierke
 */
@Entity
public class Person extends Contact {

	private static final long serialVersionUID = 4849050914625079007L;

	@Column(nullable = false)
	private String firstname;
	private String lastname;

	public Person() {
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {

		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {

		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {

		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {

		this.lastname = lastname;
	}

	/**
	 * @see com.synyx.minos.amt.domain.Contact#getName()
	 */
	@Override
	public String getName() {

		return getFirstname() + " " + getLastname();
	}

}
