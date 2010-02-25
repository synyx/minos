/**
 * 
 */
package org.synyx.minos.cmt.service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.cmt.dao.OrganisationDao;
import org.synyx.minos.cmt.domain.Organisation;


/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
@Transactional
public class OrganisationManagementImpl implements OrganisationManagement {

	private OrganisationDao organisationDao;

	/**
	 * @see org.synyx.minos.cmt.service.OrganisationManagement#delete(Organisation)
	 */
	public void delete(Organisation organisation) {
		Assert.notNull(organisation);
		organisationDao.delete(organisation);
	}

	/**
	 * @see org.synyx.minos.cmt.service.OrganisationManagement#getOrganisation(Long)
	 */
	public Organisation getOrganisation(Long id) {

		return organisationDao.readByPrimaryKey(id);
	}

	/**
	 * @see org.synyx.minos.cmt.service.OrganisationManagement#save(Organisation)
	 */
	public Organisation save(Organisation organisation) {
		Assert.notNull(organisation);

		return organisationDao.save(organisation);

	}

	/**
	 * @see org.synyx.minos.cmt.service.OrganisationManagement#getAll()
	 */
	@Transactional(readOnly = true)
	public Page<Organisation> getAll(Pageable pageable) {
		return organisationDao.readAll(pageable);
	}

	/**
	 * Set the instance to work of OrganisationDao.
	 * 
	 * @param organisationDao
	 *            OrganisationDao instance to work with.
	 */
	@Required
	public void setOrganisationDao(OrganisationDao organisationDao) {
		this.organisationDao = organisationDao;
	}
}
