package org.synyx.minos.cmt.web;

import org.synyx.minos.cmt.domain.Person;
import org.synyx.minos.core.web.DomainObjectForm;


/**
 * Abstraction of a web form for {@code Person} domain objects. Adds additional
 * properties mainly for the sake of validation.
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
public class PersonForm implements DomainObjectForm<Person> {

	private Long id;
	private String lastname;
	private String firstname;

	public PersonForm(Person person) {
		this.id = person.getId();
		this.lastname = person.getLastname();
		this.firstname = person.getFirstname();
	}

	public PersonForm() {
	}

	public Person getDomainObject() {
		Person person = new Person();

		person.setId(id);
		person.setFirstname(firstname);
		person.setLastname(lastname);

		return person;
	}

	public Person mapProperties(Person person) {
		person.setId(id);
		person.setFirstname(firstname);
		person.setLastname(lastname);

		return person;
	}

	public String getName() {
		return firstname + " " + lastname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public boolean isNew() {
		return null == id;
	}
}