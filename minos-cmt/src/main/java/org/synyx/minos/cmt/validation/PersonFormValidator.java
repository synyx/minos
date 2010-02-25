package org.synyx.minos.cmt.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.cmt.web.PersonForm;


public class PersonFormValidator implements Validator {

	public static final String LASTNAME_EMPTY = "contact.lastname.empty";

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return PersonForm.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		PersonForm user = (PersonForm) target;

		// Reject empty lastname
		if (StringUtils.isBlank(user.getLastname())) {
			errors.rejectValue("lastname", LASTNAME_EMPTY);
		}

	}

}
