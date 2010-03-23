package org.synyx.minos.cmt.web;

import org.synyx.minos.cmt.domain.Contact;
import org.synyx.minos.cmt.domain.ContactItem;
import org.synyx.minos.core.web.DomainObjectForm;


/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
public class ContactItemForm implements DomainObjectForm<ContactItem> {

	private String description;
	private String value;
	private boolean isVisible;
	private Long id;
	private Long contactId;
	private Contact contact;

	public ContactItemForm() {

	}

	public ContactItemForm(ContactItem contactItem) {
		this.description = contactItem.getDescription();
		this.value = contactItem.getValue();
		this.id = contactItem.getId();
		this.isVisible = contactItem.isVisibility();
		this.contact = contactItem.getContact();
		this.contactId = contact != null ? contact.getId() : null;
	}

	public String getDisplayString() {
		return description + ", " + value + ", "
				+ (isVisible == true ? "public" : "private");
	}

	public boolean isNew() {
		return null == id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean getIsVisible() {
		return isVisible;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public ContactItem getDomainObject() {
		ContactItem contactItem = new ContactItem();
		contactItem.setContact(contact);
		contactItem.setDescription(description);
		contactItem.setId(id);
		contactItem.setValue(value);
		contactItem.setVisibility(isVisible);

		return contactItem;
	}

	public ContactItem mapProperties(ContactItem contactItem) {
		contactItem.setContact(contact);
		contactItem.setDescription(description);
		contactItem.setId(id);
		contactItem.setValue(value);
		contactItem.setVisibility(isVisible);

		return contactItem;
	}

}
