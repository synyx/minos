package org.synyx.minos.cmt.service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.cmt.dao.ContactDao;
import org.synyx.minos.cmt.dao.ContactItemDao;
import org.synyx.minos.cmt.domain.ContactItem;
import org.synyx.minos.util.Assert;


/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
@Transactional
public class ContactItemManagementImpl implements ContactItemManagement {

	ContactDao contactDao;
	ContactItemDao contactItemDao;

	public void delete(ContactItem contactItem) {
		Assert.notNull(contactItem);

		contactItemDao.delete(contactItem);
	}

	public ContactItem save(ContactItem contactItem) {
		Assert.notNull(contactItem);

		return contactItemDao.save(contactItem);
	}

	public ContactItem getContactItem(Long id) {
		Assert.notNull(id);

		return contactItemDao.readByPrimaryKey(id);
	}

	/**
	 * Get the active instance of ContactItemDao.
	 * 
	 * @return contactItemDao The active instance.
	 */
	public ContactItemDao getContactItemDao() {
		return contactItemDao;
	}

	/**
	 * Set the instance to work of ContactItemDao.
	 * 
	 * @param contactItemDao
	 *            ContactItemDao instance to work with.
	 */
	@Required
	public void setContactItemDao(ContactItemDao contactItemDao) {
		this.contactItemDao = contactItemDao;
	}

}
