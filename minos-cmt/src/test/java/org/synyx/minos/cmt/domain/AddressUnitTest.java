package org.synyx.minos.cmt.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.synyx.minos.cmt.domain.Address;

/**
 * Test the domain class address if it works fine.
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
public class AddressUnitTest {

	@Test
	public void testTheDisplayString() {
		Address address = new Address();
		address.setCity("Karlsruhe");
		address.setStreet("Mathystraße 86");
		address.setZipCode("76137");

		assertEquals("Mathystraße 86, Karlsruhe (76137)", address
				.getDisplayString("street, city (zipcode)"));
		assertEquals("Mathystraße 86, 76137 Karlsruhe", address
				.getDisplayString("street, zipcode city"));
	}
}
