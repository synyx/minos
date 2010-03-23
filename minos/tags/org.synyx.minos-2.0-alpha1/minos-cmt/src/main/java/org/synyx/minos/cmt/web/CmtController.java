package org.synyx.minos.cmt.web;

import static org.synyx.minos.cmt.web.CmtUrls.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.synyx.hades.domain.Pageable;
import org.synyx.minos.cmt.domain.Address;
import org.synyx.minos.cmt.domain.Contact;
import org.synyx.minos.cmt.domain.ContactItem;
import org.synyx.minos.cmt.domain.Organisation;
import org.synyx.minos.cmt.domain.Person;
import org.synyx.minos.cmt.service.AddressManagement;
import org.synyx.minos.cmt.service.ContactItemManagement;
import org.synyx.minos.cmt.service.ContactManagement;
import org.synyx.minos.cmt.service.OrganisationManagement;
import org.synyx.minos.cmt.service.PersonManagement;
import org.synyx.minos.cmt.validation.AddressFormValidator;
import org.synyx.minos.cmt.validation.ContactItemFormValidator;
import org.synyx.minos.cmt.validation.OrganisationFormValidator;
import org.synyx.minos.core.web.DomainObjectForm;
import org.synyx.minos.core.web.Message;
import org.synyx.minos.core.web.Minos;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.ValidationSupport;
import org.synyx.minos.util.Assert;



/**
 * Web controller for the contact management providing access to most of the
 * contact management functionality.
 * 
 * @author David Gran - gran@synyx.de
 */
@Controller
@SessionAttributes("contactId")
public class CmtController extends ValidationSupport<PersonForm> {

    private OrganisationManagement organisationManagement;
    private PersonManagement personManagement;
    private AddressManagement addressManagement;
    private ContactManagement contactManagement;
    private ContactItemManagement contactItemManagement;

    private AddressFormValidator addressValidator;
    private OrganisationFormValidator organisationValidator;
    private ContactItemFormValidator contactItemValidator;

    // Session attribute keys for the masks.
    public static final String CONTACT_ID_KEY = "contactId";
    public static final String PERSON_KEY = "personForm";
    public static final String ORGANISATION_KEY = "organisationForm";
    public static final String ADDRESS_KEY = "addressForm";
    public static final String CONTACTITEM_KEY = "contactItemForm";
    public static final String ORGANISATIONS_KEY = "organisations";
    public static final String PERSONS_KEY = "persons";
    public static final String ADDRESSES_FOR_CONTACT_KEY =
            "addressesForContact";
    public static final String CONTACTITEMS_FOR_CONTACT_KEY =
            "contactItemsForContact";

    public static final String CONTACT_VIEW = "cmt/contact";
    public static final String CONTACTS_VIEW = "cmt/contacts";
    public static final String CONTACTITEM_FORM_VIEW = "cmt/contactItem";
    public static final String ADDRESS_FORM_VIEW = "cmt/address";


    /**
     * Setter to inject person management.
     * 
     * @param personManagement The person management to inject.
     */
    public void setPersonManagement(PersonManagement personManagement) {

        this.personManagement = personManagement;
    }


    /**
     * Setter to inject organisation management.
     * 
     * @param organisationManagement The organisation management to inject.
     */
    public void setOrganisationManagement(
            OrganisationManagement organisationManamagent) {

        this.organisationManagement = organisationManamagent;
    }


    /**
     * Setter to inject address management.
     * 
     * @param addressManagement The address management to inject.
     */
    public void setAddressManagement(AddressManagement addressManagement) {

        this.addressManagement = addressManagement;
    }


    /**
     * Setter to inject contact management.
     * 
     * @param contactManagement The contact management to inject.
     */
    public void setContactManagement(ContactManagement contactManagement) {

        this.contactManagement = contactManagement;
    }


    /**
     * Setter to inject contactItem management.
     * 
     * @param contactItemManagement The contactItem management to inject.
     */
    public void setContactItemManagement(
            ContactItemManagement contactItemManagement) {

        this.contactItemManagement = contactItemManagement;
    }


    /**
     * Setter to inject the validator for the addressForm.
     * 
     * @param addressValidator The validator to use for the addressForm.
     */
    public void setAddressValidator(AddressFormValidator addressValidator) {

        this.addressValidator = addressValidator;
    }


    /**
     * Setter to inject the validator for the contactItemForm.
     * 
     * @param contactItemValidator The validator to use for the contactItemForm.
     */
    public void setContactItemValidator(
            ContactItemFormValidator contactItemValidator) {

        this.contactItemValidator = contactItemValidator;
    }


    /**
     * Setter to inject the validator for the organisationItemForm.
     * 
     * @param organisationItemValidator The validator to use for the
     *            organisationItemForm.
     */
    public void setOrganisationValidator(
            OrganisationFormValidator organisationValidator) {

        this.organisationValidator = organisationValidator;
    }


    /**
     * Redirect to entry page.
     * 
     * @return The view name.
     */
    @RequestMapping(MODULE)
    public String index() {

        return UrlUtils.redirect("cmt/contacts");
    }


    /**
     * Saving a organisation contact. Used for editing an existing one or
     * creating a new one.
     * 
     * @param organisationForm The submitted form.
     * @param sessionStatus The current sessionStatus
     * @param errors The occured errors.
     * @param model The current model.
     * @return String The name of the target view.
     */
    @RequestMapping(value = SAVE_ORGANISATION, method = RequestMethod.POST)
    public String doSubmit(OrganisationForm organisationForm, Errors errors,
            SessionStatus sessionStatus, Model model) {

        if (!isFormValid(organisationValidator, errors, organisationForm)) {
            return "contact";
        }

        Organisation organisation = new Organisation();

        if (organisationForm.getId() != null) {
            organisation =
                    organisationManagement.getOrganisation(organisationForm
                            .getId());
        }

        organisation =
                organisationManagement.save(organisationForm
                        .mapProperties(organisation));
        sessionStatus.setComplete();

        model.addAttribute(Minos.MESSAGE, Message.success(
                "organisation.save.success", organisationForm.getName()));

        return UrlUtils.redirect("contacts");
    }


    /**
     * Saving a person contact. Used for editing an existing one or creating a
     * new one.
     * 
     * @param personForm The submitted form.
     * @param sessionStatus The current sessionStatus
     * @param errors The occured errors.
     * @param model The current model.
     * @return String The name of the target view.
     */
    @RequestMapping(value = SAVE_PERSON, method = RequestMethod.POST)
    public String doSubmit(PersonForm personForm, Errors errors,
            SessionStatus sessionStatus, Model model) {

        if (!isValid(personForm, errors)) {
            return "contact";
        }

        Person person = new Person();

        if (personForm.getId() != null) {
            person = personManagement.getPerson(personForm.getId());
        }

        person = personManagement.save(personForm.mapProperties(person));
        sessionStatus.setComplete();

        model.addAttribute(Minos.MESSAGE, Message.success(
                "person.save.success", personForm.getName()));

        return UrlUtils.redirect("contacts");
    }


    /**
     * Delete a domainObject by the given id.
     * 
     * @return The name of the target view.
     */
    // TODO: can i use here the http delete method?
    @RequestMapping(value = DELETE_CONTACT, method = RequestMethod.GET)
    public String deleteEntry(
            @RequestParam(required = true, value = "id") Long id, Model model) {

        Assert.notNull(id);

        Contact contact = contactManagement.getContactById(id);

        contactManagement.delete(contact);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "object.delete.success", contact.getName()));

        return UrlUtils.redirect("contacts");
    }


    /**
     * Delete a domainObject by the given id.
     * 
     * @return The name of the target view.
     */
    // TODO: can i use here the http delete method?
    @RequestMapping(value = DELETE_ADDRESS, method = RequestMethod.GET)
    public String deleteAddress(
            @RequestParam(required = true, value = "id") Long id,
            @ModelAttribute(value = CONTACT_ID_KEY) Long contactId, Model model) {

        Assert.notNull(id);

        Address address = addressManagement.getAddress(id);

        // cut the address from the contact.
        Contact contact = address.getContact();
        contact.getAddresses().remove(address);
        contactManagement.save(contact);

        // Than delete the address
        address.setContact(null);
        addressManagement.delete(address);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "object.delete.success", address.getDisplayString()));

        return UrlUtils.redirect("editContact");
    }


    /**
     * Delete a domainObject by the given id.
     * 
     * @return The name of the target view.
     */
    // TODO: can i use here the http delete method?
    @RequestMapping(value = DELETE_CONTACTITEM, method = RequestMethod.GET)
    public String deleteContactItem(
            @RequestParam(required = true, value = "id") Long id,
            @ModelAttribute(value = CONTACT_ID_KEY) Long contactId, Model model) {

        Assert.notNull(id);

        ContactItem contactItem = contactItemManagement.getContactItem(id);

        // cut the address from the contact.
        Contact contact = contactItem.getContact();
        contact.getContactItems().remove(contactItem);
        contactManagement.save(contact);

        // Than delete the address
        contactItem.setContact(null);
        contactItemManagement.save(contactItem);

        model.addAttribute(Minos.MESSAGE, Message.success(
                "object.delete.success", contactItem
                        .getDisplayString("description: value")));

        return UrlUtils.redirect("editContact");
    }


    /**
     * View a contact.
     * 
     * @param id The contact id of the contact to show.
     * @param model The current model.
     * @return String The name of the target view.
     */
    @RequestMapping(value = CREATE_ORGANISATION, method = RequestMethod.GET)
    public String setupCreateOrganisationForm(Model model) {

        model.addAttribute(ORGANISATION_KEY, new OrganisationForm());
        return CONTACT_VIEW;
    }


    /**
     * View a contact.
     * 
     * @param id The contact id of the contact to show.
     * @param model The current model.
     * @return String The name of the target view.
     */
    @RequestMapping(value = CREATE_PERSON, method = RequestMethod.GET)
    public String setupCreatePersonForm(Model model) {

        model.addAttribute(PERSON_KEY, new PersonForm());
        return CONTACT_VIEW;
    }


    /**
     * Get all contacts.
     * 
     * @return All available contacts.
     */
    @RequestMapping(LIST_CONTACTS)
    public ModelAndView getAllContacts(Pageable pageable) {

        // TODO: Check the useage of pageable.

        ModelAndView model =
                new ModelAndView("cmt/contacts", ORGANISATIONS_KEY,
                        organisationManagement.getAll(pageable));

        model.addObject(PERSONS_KEY, personManagement.getAll(pageable));

        return model;
    }


    /**
     * Edit the person matching to the given id.
     * 
     * @param id The id of the person to edit.
     * @param model The current model.
     * @return The name of the target view, here {@link #CONTACT_VIEW} if a
     *         person was found or {@link #CONTACTS_VIEW} if no person was
     *         found.
     */
    public String setupEditOrganisationForm(Long id, Model model) {

        Assert.notNull(id);

        Organisation organisation = organisationManagement.getOrganisation(id);

        if (organisation == null) {
            model.addAttribute(Minos.MESSAGE, Message.error("cmt.id.invalid",
                    id));
            return CmtController.CONTACTS_VIEW;
        }

        model
                .addAttribute(ORGANISATION_KEY, new OrganisationForm(
                        organisation));
        model.addAttribute(CONTACTITEMS_FOR_CONTACT_KEY, organisation
                .getContactItems());
        model.addAttribute(ADDRESSES_FOR_CONTACT_KEY, organisation
                .getAddresses());
        model.addAttribute(CONTACT_ID_KEY, organisation.getId());

        return CONTACT_VIEW;
    }


    /**
     * Edit the person matching to the given id.
     * 
     * @param id The id of the person to edit.
     * @param model The current model.
     * @return The name of the target view, here {@link #CONTACT_VIEW} if a
     *         person was found or {@link #CONTACTS_VIEW} if no person was
     *         found.
     */
    public String setupEditPersonForm(Long id, Model model) {

        Assert.notNull(id);

        Person person = personManagement.getPerson(id);

        if (person == null) {
            model.addAttribute(Minos.MESSAGE, Message.error("cmt.id.invalid",
                    id));
            return CmtController.CONTACTS_VIEW;
        }

        model.addAttribute(CONTACTITEMS_FOR_CONTACT_KEY, person
                .getContactItems());
        model.addAttribute(ADDRESSES_FOR_CONTACT_KEY, person.getAddresses());
        model.addAttribute(PERSON_KEY, new PersonForm(person));
        model.addAttribute(CONTACT_ID_KEY, person.getId());

        return CONTACT_VIEW;
    }


    /**
     * Set up the form for creating or editing an address.
     * 
     * @param id The id of the address to update, can be null, but than the
     *            contactId must be given.
     * @param contactId The id of the contact to create an address for, can be
     *            null, but than the id must be given.
     * @param model The current model.
     * @return The name
     */
    @RequestMapping(value = EDIT_ADDRESS, method = RequestMethod.GET)
    public String setupAddressForm(
            @RequestParam(required = false, value = "id") Long id,
            @ModelAttribute(CONTACT_ID_KEY) Long contactId, Model model) {

        // if no id is available and a contactId is given, then a new address
        // will be created.
        if (id == null && contactId != null) {
            AddressForm form = new AddressForm();
            model.addAttribute(ADDRESS_KEY, form);
            return ADDRESS_FORM_VIEW;
        }

        if (id != null) {
            Address address = addressManagement.getAddress(id);

            if (address == null) {
                model.addAttribute(Minos.MESSAGE, Message.error(
                        "cmt.id.invalidId", id));
                return CmtController.CONTACTS_VIEW;
            }

            model.addAttribute(ADDRESS_KEY, new AddressForm(address));

            return ADDRESS_FORM_VIEW;
        }

        // TODO: This happens only when the id and the contactId is not set.
        return CmtController.CONTACTS_VIEW;
    }


    /**
     * Set up the form for creating or editing an address.
     * 
     * @param id The id of the address to update, can be null, but than the
     *            contactId must be given.
     * @param contactId The id of the contact to create an address for, can be
     *            null, but than the id must be given.
     * @param model The current model.
     * @return The name
     */
    @RequestMapping(value = EDIT_CONTACTITEM, method = RequestMethod.GET)
    public String setupContactItemForm(
            @RequestParam(required = false, value = "id") Long id,
            @ModelAttribute(CONTACT_ID_KEY) Long contactId, Model model) {

        // if no id is available and a contactId is given, then a new
        // contactItem will be created.
        if (id == null && contactId != null) {
            ContactItemForm form = new ContactItemForm();
            form.setContactId(contactId);
            model.addAttribute(CONTACTITEM_KEY, form);
            return CONTACTITEM_FORM_VIEW;
        }

        if (id != null) {
            ContactItem contactItem = contactItemManagement.getContactItem(id);

            if (contactItem == null) {
                model.addAttribute(Minos.MESSAGE, Message.error(
                        "cmt.id.invalidId", id));
                return CmtController.CONTACTS_VIEW;
            }

            model.addAttribute(CONTACTITEM_KEY,
                    new ContactItemForm(contactItem));

            return CONTACTITEM_FORM_VIEW;
        }

        // TODO: This happens only when the id and the contactId is not set. Is
        // it god to hold this way?
        return CmtController.CONTACTS_VIEW;
    }


    /**
     * Saving a address. Used for editing an existing one or creating a new one.
     * 
     * @param addressForm The submitted form.
     * @param sessionStatus The current sessionStatus
     * @param errors The occured errors.
     * @param model The current model.
     * @return String The name of the target view.
     */
    @RequestMapping(value = EDIT_ADDRESS, method = RequestMethod.POST)
    public String doSubmit(AddressForm addressForm, Errors errors,
            SessionStatus sessionStatus, Model model,
            @ModelAttribute(CONTACT_ID_KEY) Long contactId) {

        // TODO: Should i inject this validator by spring??
        // TODO: How can i use more than one validator in a single controller??
        if (!isFormValid(addressValidator, errors, addressForm)) {
            return ADDRESS_FORM_VIEW;
        }

        Address address = new Address();

        // TODO: Should the validator check before if the address is new and a
        // contactId already exists?
        if (!addressForm.isNew()) {
            address = addressManagement.getAddress(addressForm.getId());
        }

        Contact contact = contactManagement.getContactById(contactId);

        // TODO: Is it a good a idea to set the contact on the addressForm??
        // Should a remove the field from the addressFormClass and set it to the
        // outcoming domainObject?
        addressForm.setContact(contact);

        address = addressManagement.save(addressForm.mapProperties(address));

        if (addressForm.isNew()) {
            contact.addAddress(address);
            contactManagement.save(contact);
        }

        sessionStatus.setComplete();

        // TODO: Let the user decide anywhere how we should display some strings
        // of the domain objects
        model.addAttribute(Minos.MESSAGE, Message.success(
                "address.save.success", address
                        .getDisplayString("street, city (zipcode)")));

        // TODO: use the sessionAttribute Annotation to store the active
        // contactId.
        return "redirect:editContact";
    }


    /**
     * Choose the right method to prepare the view.
     * 
     * @param id The id of the contact, which should be displayed.
     * @param model The current model.
     * @return The name of the target view.
     */
    @RequestMapping(value = { EDIT_CONTACT }, method = RequestMethod.GET)
    public String setUpEditContact(
            @RequestParam(value = CONTACT_ID_KEY, required = true) Long id,
            Model model) {

        Assert.notNull(id);

        Contact contact = contactManagement.getContactById(id);

        if (contact instanceof Person) {
            return setupEditPersonForm(id, model);
        } else if (contact instanceof Organisation) {
            return setupEditOrganisationForm(id, model);
        }

        return UrlUtils.redirect("editContact");
    }


    /**
     * Saving a contactItem. Used for editing an existing one or creating a new
     * one.
     * 
     * @param contactItemForm The submitted form.
     * @param sessionStatus The current sessionStatus
     * @param errors The occured errors.
     * @param model The current model.
     * @return String The name of the target view.
     */
    @RequestMapping(value = EDIT_CONTACTITEM, method = RequestMethod.POST)
    public String doSubmit(ContactItemForm contactItemForm, Errors errors,
            SessionStatus sessionStatus, Model model,
            @ModelAttribute(CONTACT_ID_KEY) Long contactId) {

        // TODO: Should i inject this validator by spring??
        // TODO: How can i use more than one validator in a single controller??
        if (!isFormValid(contactItemValidator, errors, contactItemForm)) {
            return CONTACTITEM_FORM_VIEW;
        }

        ContactItem contactItem = new ContactItem();

        // TODO: Should the validator check before if the contactItem is new and
        // a contactId already exists?
        if (!contactItemForm.isNew()) {
            contactItem =
                    contactItemManagement.getContactItem(contactItemForm
                            .getId());
        }

        Contact contact = contactManagement.getContactById(contactId);

        // TODO: Is it a good a idea to set the contact on the contactItemForm??
        // Should a remove the field from the contactItemFormClass and set it to
        // the outcoming domainObject?
        contactItemForm.setContact(contact);

        contactItem =
                contactItemManagement.save(contactItemForm
                        .mapProperties(contactItem));

        if (contactItemForm.isNew()) {
            contact.addContactItem(contactItem);
            contactManagement.save(contact);
        }

        sessionStatus.setComplete();

        // TODO: Let the user decide anywhere how we should display some strings
        // of the domain objects
        model.addAttribute(Minos.MESSAGE, Message.success(
                "contactItem.save.success", contactItem
                        .getDisplayString("description: value")));

        return UrlUtils.redirect("editContact");
    }


    /**
     * Validate if a other form is validate, then the default supported form.
     * 
     * @param validator
     * @param errors
     * @param form
     * @return
     */
    // TODO: is it possible to use a list of Forms in the ValidationSupport
    // class or not?
    @SuppressWarnings("unchecked")
    private boolean isFormValid(final Validator validator, Errors errors,
            final DomainObjectForm form) {

        validator.validate(form, errors);

        return !errors.hasErrors();
    }

}
