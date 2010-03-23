package org.synyx.minos.cmt.service;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.cmt.domain.Person;


public interface PersonManagement {

	/**
	 * Saves an given person.
	 * 
	 * @param person
	 *            The person to save
	 * @return The saved person
	 */
	public Person save(Person person);

	/**
	 * Deletes a given person.
	 * 
	 * @param person
	 */
	public void delete(Person person);

	/**
	 * Get the matching person by the given id.
	 * 
	 * @param id
	 *            The id of the person.
	 * @return The retrieved person.
	 */
	public Person getPerson(Long id);

	/**
	 * Get all available persons.
	 * 
	 * @return All retrieved persons.
	 */
	public Page<Person> getAll(Pageable pageable);
}
