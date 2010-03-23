package org.synyx.minos.cmt.validation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.synyx.minos.cmt.web.AddressForm;


public class AddressFormValidator implements Validator {

	public static final String ZIPCODE_LENGTH = "address.zip.length";
	public static final String ZIPCODE_NUMBER = "address.zip.number";
	public static final String STREET_EMPTY = "address.street.empty";
	public static final String CITY_EMPTY = "address.city.empty";

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return AddressForm.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		AddressForm address = (AddressForm) target;

		// is a street given?
		if (StringUtils.isBlank(address.getStreet())) {
			errors.rejectValue("street", STREET_EMPTY);
		}

		// is a city given?
		if (StringUtils.isBlank(address.getCity())) {
			errors.rejectValue("city", CITY_EMPTY);
		}

		// is the given zipcode a valid german zipcode?
		if (!StringUtils.isBlank(address.getZipCode())) {
			if (StringUtils.length(address.getZipCode()) < 5
					|| StringUtils.length(address.getZipCode()) > 5) {
				errors.rejectValue("zipCode", ZIPCODE_LENGTH);
				// is the given zipcode a valid german zipcode?
			}

			if (!NumberUtils.isNumber(address.getZipCode())) {
				errors.rejectValue("zipCode", ZIPCODE_NUMBER);
			}
		}
	}
}
