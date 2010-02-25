package org.synyx.minos.cmt.service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.synyx.minos.cmt.dao.AddressDao;
import org.synyx.minos.cmt.dao.ContactDao;
import org.synyx.minos.cmt.domain.Address;
import org.synyx.minos.util.Assert;


/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
@Transactional
public class AddressManagementImpl implements AddressManagement {

	ContactDao contactDao;
	AddressDao addressDao;

	/**
	 * @see org.synyx.minos.cmt.service.AddressManagement#delete(org.synyx.minos.cmt.domain.Address)
	 */
	public void delete(Address address) {
		Assert.notNull(address);

		addressDao.delete(address);
	}

	/**
	 * @see org.synyx.minos.cmt.service.AddressManagement#save(org.synyx.minos.cmt.domain.Address)
	 */
	public Address save(Address address) {
		Assert.notNull(address);

		return addressDao.save(address);
	}

	/**
	 * @see org.synyx.minos.cmt.service.AddressManagement#getAddress(java.lang.Long)
	 */
	public Address getAddress(Long id) {
		Assert.notNull(id);

		return addressDao.readByPrimaryKey(id);
	}

	/**
	 * Get the active instance of AddressDao.
	 * 
	 * @return addressDao The active instance.
	 */
	public AddressDao getAddressDao() {
		return addressDao;
	}

	/**
	 * Set the instance to work of AddressDao.
	 * 
	 * @param addressDao
	 *            AddressDao instance to work with.
	 */
	@Required
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

}
