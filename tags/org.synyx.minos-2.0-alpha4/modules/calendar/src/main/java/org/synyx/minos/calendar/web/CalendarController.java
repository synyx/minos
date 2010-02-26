package org.synyx.minos.calendar.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.synyx.minos.calendar.web.CalendarUrls.*;
import static org.synyx.minos.core.web.UrlUtils.*;

import java.util.Locale;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.calendar.domain.Appointments;
import org.synyx.minos.calendar.service.CalendarService;
import org.synyx.minos.core.domain.TimePeriod;
import org.synyx.minos.core.web.DateTimeEditor;
import org.synyx.minos.core.web.UrlUtils;
import org.synyx.minos.core.web.ValidationSupport;
import org.synyx.minos.umt.service.UserManagement;


/**
 * Controller for web access to the calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Controller
@SessionAttributes(CalendarController.APPOINTMENT_KEY)
public class CalendarController extends ValidationSupport<Appointment> {

    public static final String APPOINTMENT_KEY = "appointment";

    private final CalendarService calendarService;
    private final UserManagement userManagement;


    /**
     * Creates a new {@link CalendarController}.
     * 
     * @param calendarService
     * @param userManagement
     */
    @Autowired
    public CalendarController(CalendarService calendarService,
            UserManagement userManagement) {

        this.calendarService = calendarService;
        this.userManagement = userManagement;
    }


    /**
     * Initializes binders to bind HTTP request parameters into domain objects.
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(DataBinder binder, Locale locale) {

        // Bind simple ISO dates (2008-12-31) from start property as well as
        // locale based ones for any other DateTime instance
        binder.registerCustomEditor(DateTime.class, "periodStart",
                new DateTimeEditor(ISODateTimeFormat.date()));
        binder.registerCustomEditor(DateTime.class, new DateTimeEditor(locale));
    }


    /**
     * Simple redirect to the list appointments view. Convenience mapping to
     * allow simple reconfiguration of calendar start page. This one could
     * regard e.g. user settings to decide where to redirect to.
     * 
     * @return
     */
    @RequestMapping(CalendarUrls.MODULE)
    public String index() {

        return UrlUtils.redirect(CalendarUrls.APPOINTMENTS);
    }


    /**
     * Lists all {@link Appointment}s.
     * 
     * @param model
     * @return
     */
    @RequestMapping(CalendarUrls.APPOINTMENTS)
    public String listAppointments(Model model) {

        DateMidnight date = new DateMidnight();

        return redirect(APPOINTMENTS_MONTH, date.getYear(), date
                .getMonthOfYear());
    }


    /**
     * Shows all appointments for a given day, e.g. /appointments/2010/02/17.
     * 
     * @param year
     * @param month
     * @param day
     * @param model
     * @return
     */
    @RequestMapping(value = APPOINTMENTS_DAY, method = GET)
    public String showAppointmentsForDay(@PathVariable(YEAR_PATHVAR) int year,
            @PathVariable(MONTH_PATHVAR) int month,
            @PathVariable(DAY_PATHVAR) int day, Model model) {

        DateMidnight date = new DateMidnight(year, month, day);
        Appointments appointments =
                calendarService.getAppointments(TimePeriod.allDay(date));

        return prepareAppointments(appointments, CalendarView.DAY, date, model);
    }


    public String showAppointmentsForWeek(Model model) {

        return prepareAppointments(null, CalendarView.WEEK, null, model);
    }


    /**
     * Shows appointmens for a given month, e.g. /appointments/2010/02.
     * 
     * @param year
     * @param month
     * @param model
     * @return
     */
    @RequestMapping(value = APPOINTMENTS_MONTH, method = GET)
    public String showAppointmentsForMonth(
            @PathVariable(YEAR_PATHVAR) int year,
            @PathVariable(MONTH_PATHVAR) int month, Model model) {

        DateMidnight date = new DateMidnight(year, month, 1);
        Appointments appointments =
                calendarService.getAppointments(TimePeriod.allMonth(date));

        return prepareAppointments(appointments, CalendarView.MONTH, date,
                model);
    }


    public String prepareAppointments(Appointments appointments,
            CalendarView view, DateMidnight referenceDate, Model model) {

        model.addAttribute("appointments", appointments.iterator());
        model.addAttribute("view", view);
        model.addAttribute("referenceDate", referenceDate);
        model.addAttribute("modes", CalendarView.values());

        return String.format("%s_%s", APPOINTMENTS, view.name().toLowerCase(
                Locale.ENGLISH));
    }


    /**
     * Shows the form to edit or create an {@link Appointment}.
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = APPOINTMENT, method = GET)
    public String showAppointment(
            @PathVariable(APPOINTMENT_PATHVAR) Appointment appointment,
            Model model) {

        return prepareAppointmentForm(appointment, model);
    }


    @RequestMapping(value = APPOINTMENT_FORM, method = GET)
    public String showAppointmentForm(Model model) {

        return prepareAppointmentForm(new Appointment(), model);
    }


    private String prepareAppointmentForm(Appointment appointment, Model model) {

        model.addAttribute("users", userManagement.getUsers());
        model.addAttribute("calendars", calendarService.getCalendars());
        model.addAttribute(APPOINTMENT_KEY, appointment);

        return "calendar/appointment";
    }


    @RequestMapping(value = APPOINTMENTS, method = POST)
    public String createAppointment(
            @ModelAttribute(APPOINTMENT_KEY) Appointment appointment,
            Errors errors, Model model) {

        return saveAppointment(appointment, model, errors);
    }


    @RequestMapping(value = APPOINTMENT, method = PUT)
    public String updateAppointment(
            @ModelAttribute(APPOINTMENT_KEY) Appointment appointment,
            Errors errors, Model model) {

        return saveAppointment(appointment, model, errors);
    }


    private String saveAppointment(Appointment appointment, Model model,
            Errors errors) {

        if (!isValid(appointment, errors)) {
            return prepareAppointmentForm(appointment, model);
        }

        calendarService.save(appointment);

        return redirect(APPOINTMENTS);
    }
}
