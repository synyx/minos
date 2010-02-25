package org.synyx.minos.cmt.service;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.cmt.domain.Organisation;


public interface OrganisationManagement {

	/**
	 * Saves an given organisation.
	 * 
	 * @param organisation
	 *            The organisation to save
	 * @return The saved organisation
	 */
	public Organisation save(Organisation organisation);

	/**
	 * Deletes a given organisation.
	 * 
	 * @param organisation
	 */
	public void delete(Organisation organisation);

	/**
	 * Get the matching organisation by the given id.
	 * 
	 * @param id
	 *            The id of the organisation.
	 * @return The retrieved organisation.
	 */
	public Organisation getOrganisation(Long id);

	/**
	 * Get all available organisations.
	 * 
	 * @return All retrieved organisations.
	 */
	public Page<Organisation> getAll(Pageable pageable);
}
