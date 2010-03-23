package org.synyx.minos.cmt.web;

import org.synyx.minos.cmt.domain.DomainType;
import org.synyx.minos.core.web.DomainObjectForm;


/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
public class DomainTypeForm implements DomainObjectForm<DomainType> {

	private Long id;
	private String languageKey;
	private String decsription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLanguageKey() {
		return languageKey;
	}

	public void setLanguageKey(String languageKey) {
		this.languageKey = languageKey;
	}

	public String getDecsription() {
		return decsription;
	}

	public void setDecsription(String decsription) {
		this.decsription = decsription;
	}

	public DomainType getDomainObject() {
		DomainType type = new DomainType();

		type.setDecsription(decsription);
		type.setId(id);
		type.setLanguageKey(languageKey);

		return type;
	}

	public DomainType mapProperties(DomainType domainType) {
		domainType.setDecsription(decsription);
		domainType.setId(id);
		domainType.setLanguageKey(languageKey);

		return domainType;
	}

}
