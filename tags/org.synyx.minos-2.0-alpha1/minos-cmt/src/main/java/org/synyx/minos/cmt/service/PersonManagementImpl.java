/**
 * 
 */
package org.synyx.minos.cmt.service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.cmt.dao.PersonDao;
import org.synyx.minos.cmt.domain.Person;


/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
@Transactional
public class PersonManagementImpl implements PersonManagement {

	private PersonDao personDao;

	/**
	 * @see org.synyx.minos.cmt.service.PersonManagement#delete(Person)
	 */
	public void delete(Person person) {
		Assert.notNull(person);
		personDao.delete(person);
	}

	/**
	 * @see org.synyx.minos.cmt.service.PersonManagement#getPerson(Long)
	 */
	public Person getPerson(Long id) {

		return personDao.readByPrimaryKey(id);
	}

	/**
	 * @see org.synyx.minos.cmt.service.PersonManagement#save(Person)
	 */
	public Person save(Person person) {
		Assert.notNull(person);

		return personDao.save(person);

	}

	/**
	 * @see org.synyx.minos.cmt.service.PersonManagement#getAll()
	 */
	@Transactional(readOnly = true)
	public Page<Person> getAll(Pageable pageable) {
		return personDao.readAll(pageable);
	}

	/**
	 * Set the instance to work of PersonDao.
	 * 
	 * @param personDao
	 *            PersonDao instance to work with.
	 */
	@Required
	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
}
