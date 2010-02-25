package org.synyx.minos.cmt.web;

import org.synyx.minos.cmt.domain.Organisation;
import org.synyx.minos.core.web.DomainObjectForm;


/**
 * Abstraction of a web form for {@code Person} domain objects. Adds additional
 * properties mainly for the sake of validation.
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
public class OrganisationForm implements DomainObjectForm<Organisation> {

	private Long id;
	private String name;
	private String type;

	public OrganisationForm(Organisation organisation) {
		this.id = organisation.getId();
		this.name = organisation.getName();
		this.type = organisation.getType();
	}

	public OrganisationForm() {
	}

	public Organisation getDomainObject() {
		Organisation organisation = new Organisation();

		organisation.setId(id);
		organisation.setType(type);
		organisation.setName(name);

		return organisation;
	}

	public Organisation mapProperties(Organisation organisation) {
		organisation.setId(id);
		organisation.setType(type);
		organisation.setName(name);

		return organisation;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isNew() {
		return null == id;
	}
}