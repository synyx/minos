/**
 * 
 */
package org.synyx.minos.cmt.service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.cmt.dao.ContactDao;
import org.synyx.minos.cmt.domain.Contact;


/**
 * @author Oliver Gierke
 */
@Transactional
public class ContactManagementImpl implements ContactManagement {

	private ContactDao contactDao;

	/**
	 * @see org.synyx.minos.cmt.service.ContactManagement#delete(Contact)
	 */
	public void delete(Contact contact) {
		Assert.notNull(contact);
		contactDao.delete(contact);
	}

	/**
	 * @see org.synyx.minos.cmt.service.ContactManagement#getContactById(Long)
	 */
	public Contact getContactById(Long id) {

		return contactDao.readByPrimaryKey(id);
	}

	/**
	 * @see org.synyx.minos.cmt.service.ContactManagement#save(Contact)
	 */
	public Contact save(Contact contact) {
		Assert.notNull(contact);

		return contactDao.save(contact);

	}

	/**
	 * @see org.synyx.minos.cmt.service.ContactManagement#getAll()
	 */
	@Transactional(readOnly = true)
	public Page<Contact> getAll(Pageable pageable) {
		return contactDao.readAll(pageable);
	}

	/**
	 * Set the instance to work of ContactDao.
	 * 
	 * @param contactDao
	 *            ContactDao instance to work with.
	 */
	@Required
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
}
