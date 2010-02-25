package org.synyx.minos.cmt.web;

/**
 * URLs to be used for the CMT web and REST controllers.
 * 
 * @author David Gran - gran@synyx.de
 * 
 */
public final class CmtUrls {

	public static final String MODULE = "/cmt";

	public static final String LIST_CONTACTS = MODULE + "/contacts";
	public static final String DELETE_ADDRESS = MODULE + "/deleteAddress";
	public static final String DELETE_CONTACT = MODULE + "/deleteContact";
	public static final String DELETE_CONTACTITEM = MODULE
			+ "/deleteContactItem";
	public static final String LIST_ADDRESSES_FOR_CONTACT = MODULE
			+ "/addresses";
	public static final String EDIT_ADDRESS = MODULE + "/editAddress";
	public static final String EDIT_CONTACTITEM = MODULE + "/editContactItem";

	public static final String SAVE_PERSON = MODULE + "/savePerson";
	public static final String SAVE_ORGANISATION = MODULE + "/saveOrganisation";
	// public static final String EDIT_PERSON = MODULE + "/editPerson";
	// public static final String EDIT_ORGANISATION = MODULE +
	// "/editOrganisation";
	public static final String CREATE_PERSON = MODULE + "/createPerson";
	public static final String CREATE_ORGANISATION = MODULE
			+ "/createOrganisation";
	public static final String EDIT_CONTACT = MODULE + "/editContact";

	/**
	 * Private constructors to prevent the instantiation.
	 */
	private CmtUrls() {

	}
}
