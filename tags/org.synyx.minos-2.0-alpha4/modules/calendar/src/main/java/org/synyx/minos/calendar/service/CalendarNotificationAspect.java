package org.synyx.minos.calendar.service;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;
import org.synyx.minos.calendar.configuration.ConfigKeys;
import org.synyx.minos.calendar.domain.Appointment;
import org.synyx.minos.core.notification.ConfigBasedNotificationContext;
import org.synyx.minos.core.notification.NotificationService;
import org.synyx.minos.core.notification.SimpleMessageNotification;


/**
 * Notifications for calendar module.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Aspect
public class CalendarNotificationAspect {

    private static final String APPOINTMENT_CHANGE_MESSAGE =
            "Appointment '%s' has changes!";

    private NotificationService notificationService;


    /**
     * @param notificationService the notificationService to set
     */
    @Required
    public void setNotificationService(NotificationService notificationService) {

        this.notificationService = notificationService;
    }


    /**
     * Notifies all participants of an {@link Appointment} about the changes
     * that have happend.
     * 
     * @param joinPoint
     * @param appointment
     */
    @AfterReturning("execution(* org.synyx.minos.calendar.service.CalendarService.save(..)) && args(appointment)")
    public void notifyAppointmentParticipants(Appointment appointment) {

        String message =
                String.format(APPOINTMENT_CHANGE_MESSAGE, appointment
                        .getDescription());

        notificationService.notify(new SimpleMessageNotification(appointment
                .getParticipants(), message),
                new ConfigBasedNotificationContext(
                        ConfigKeys.INVITATION_NOTIFICATION));
    }
}
