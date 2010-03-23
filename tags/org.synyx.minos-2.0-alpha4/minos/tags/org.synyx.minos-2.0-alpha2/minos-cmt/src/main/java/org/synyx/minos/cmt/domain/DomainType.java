package org.synyx.minos.cmt.domain;

import javax.persistence.Entity;

import org.synyx.hades.domain.AbstractPersistable;

/**
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
@Entity
public class DomainType extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 7972734136735242508L;
	private String languageKey;
	private String decsription;

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
}
