package org.synyx.minos.cmt.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * @author Oliver Gierke
 */
@Entity
public class Organisation extends Contact {

	private static final long serialVersionUID = 1434099998351811696L;

	private String name;

	// TODO: Rename type to kind...
	private String type;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	private final List<Contact> members;

	/**
	 * Constructor of <code>Company</code>.
	 */
	public Organisation() {

		this.members = new ArrayList<Contact>();
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the members
	 */
	public List<Contact> getMembers() {

		return members;
	}

	/**
	 * Adds an person.
	 * 
	 * @param person
	 */
	public void addMember(Contact member) {

		this.members.add(member);
	}

}
