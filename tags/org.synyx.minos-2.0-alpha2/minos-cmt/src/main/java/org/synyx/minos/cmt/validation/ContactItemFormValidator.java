package org.synyx.minos.cmt.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.cmt.web.ContactItemForm;


public class ContactItemFormValidator implements Validator {

	public static final String VALUE_EMPTY = "contactItem.value.empty";

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return ContactItemForm.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		ContactItemForm contactItem = (ContactItemForm) target;

		// is the value empty given?
		if (StringUtils.isBlank(contactItem.getValue())) {
			errors.rejectValue("value", VALUE_EMPTY);
		}
	}
}
