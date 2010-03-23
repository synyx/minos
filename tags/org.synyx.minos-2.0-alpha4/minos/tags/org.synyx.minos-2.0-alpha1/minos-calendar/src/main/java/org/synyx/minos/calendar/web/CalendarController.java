package org.synyx.minos.calendar.web;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.calendar.service.CalendarService;
import org.synyx.minos.core.domain.TimePeriod;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.web.DateTimeEditor;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.ValidationSupport;
import org.synyx.minos.umt.service.UserManagement;
import org.synyx.minos.umt.web.UserEditor;
import org.synyx.minos.util.EnumUtils;


/**
 * Controller for web access to the calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Controller
@SessionAttributes(CalendarController.APPOINTMENT_KEY)
public class CalendarController extends ValidationSupport<Appointment> {

    private static final CalendarView DEFAULT_VIEW = CalendarView.WEEK;
    public static final String APPOINTMENT_KEY = "appointment";

    private CalendarService calendarService;
    private UserManagement userManagement;


    /**
     * @param calendarService the calendarService to set
     */
    @Required
    public void setCalendarService(CalendarService calendarService) {

        this.calendarService = calendarService;
    }


    /**
     * @param userManagement the userManagement to set
     */
    @Required
    public void setUserManagement(UserManagement userManagement) {

        this.userManagement = userManagement;
    }


    /**
     * Initializes binders to bind HTTP request parameters into domain objects.
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(DataBinder binder, HttpServletRequest request) {

        // Bind simple ISO dates (2008-12-31) from start property as well as
        // locale based ones for any other DateTime instance
        binder.registerCustomEditor(DateTime.class, "start",
                new DateTimeEditor(ISODateTimeFormat.date()));
        binder.registerCustomEditor(DateTime.class, new DateTimeEditor(request
                .getLocale()));

        binder.registerCustomEditor(User.class, new UserEditor(userManagement));
    }


    /**
     * Simple redirect to the list appointments view. Convenience mapping to
     * allow simple reconfiguration of calendar start page. This one could
     * regard e.g. user settings to decide where to redirect to.
     * 
     * @return
     */
    @RequestMapping(CalendarUrls.MODULE_BASE)
    public String index() {

        return UrlUtils.redirect(CalendarUrls.LIST_APPOINTMENTS);
    }


    /**
     * Lists all {@link Appointment}s.
     * 
     * @param model
     * @return
     */
    @RequestMapping(CalendarUrls.LIST_APPOINTMENTS)
    public String listAppointments(
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "start", required = false) DateTime start,
            ModelMap model) {

        TimePeriod period =
                EnumUtils.valueOf(mode, DEFAULT_VIEW).getPeriod(start);

        model.addAttribute("appointments", calendarService.getAppointments(
                period.getStart(), period.getEnd()).iterator());

        return CalendarUrls.LIST_APPOINTMENTS;
    }


    /**
     * Shows the form to edit or create an {@link Appointment}.
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = CalendarUrls.EDIT_APPOINTMENT, method = RequestMethod.GET)
    public String showAppointmentForm(
            @RequestParam(value = "id", required = false) Long id,
            ModelMap model) {

        model.addAttribute("users", userManagement.getUsers());
        model.addAttribute("calendars", calendarService.getCalendars());

        model.addAttribute(APPOINTMENT_KEY, null != id ? calendarService
                .getAppointment(id) : new Appointment());

        return CalendarUrls.EDIT_APPOINTMENT;
    }


    /**
     * Saves a given appointment.
     * 
     * @param appointment
     * @param errors
     * @param model
     * @return
     */
    @RequestMapping(value = CalendarUrls.EDIT_APPOINTMENT, method = RequestMethod.POST)
    public String saveAppointment(
            @ModelAttribute(CalendarController.APPOINTMENT_KEY) Appointment appointment,
            Errors errors, ModelMap model) {

        if (!isValid(appointment, errors)) {
            return showAppointmentForm(appointment.getId(), model);
        }

        calendarService.save(appointment);

        return UrlUtils.redirect("appointments");
    }
}
