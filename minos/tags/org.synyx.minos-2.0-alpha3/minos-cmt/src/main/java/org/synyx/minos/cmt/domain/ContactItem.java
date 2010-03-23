package org.synyx.minos.cmt.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.apache.commons.lang.StringUtils;
import org.synyx.hades.domain.AbstractPersistable;

/**
 * ContactItem. Use it to manage the contacts of a contact.
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
@Entity
public class ContactItem extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -5404508408668147378L;

	private String value;
	private String description;
	private boolean visibility;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Contact contact;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/**
	 * Return the display string of the contactItem.
	 * 
	 * @param pattern
	 *            The pattern to display the current ContactItem.
	 * @return The formatted contactItem
	 */
	public String getDisplayString(String pattern) {

		if (StringUtils.isBlank(pattern)) {
			return "";
		}

		pattern = pattern.replaceAll("value", value);
		pattern = pattern.replaceAll("description", description);
		pattern = pattern.replaceAll("visibility",
				(visibility == true ? "public" : "private"));

		return pattern;

	}
}
