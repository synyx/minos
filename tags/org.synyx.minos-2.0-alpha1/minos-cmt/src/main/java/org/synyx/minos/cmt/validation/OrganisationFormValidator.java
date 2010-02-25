package org.synyx.minos.cmt.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.cmt.web.OrganisationForm;


public class OrganisationFormValidator implements Validator {

	public static final String NAME_EMPTY = "organisation.name.empty";

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return OrganisationForm.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		OrganisationForm organisation = (OrganisationForm) target;

		// Reject empty name
		if (StringUtils.isBlank(organisation.getName())) {
			errors.rejectValue("name", NAME_EMPTY);
		}

	}

}
