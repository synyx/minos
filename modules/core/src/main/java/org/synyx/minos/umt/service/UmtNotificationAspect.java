package org.synyx.minos.umt.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Required;
import org.synyx.minos.core.domain.Password;
import org.synyx.minos.core.domain.User;
import org.synyx.minos.core.notification.ConfigBasedNotificationContext;
import org.synyx.minos.core.notification.Notification;
import org.synyx.minos.core.notification.NotificationContext;
import org.synyx.minos.core.notification.NotificationFactory;
import org.synyx.minos.core.notification.NotificationService;


/**
 * Aspect triggering the following notifications on {@link UserManagement} invocations:
 * <ul>
 * <li>Creation of new users results in a notification of the new password.</li>
 * </ul>
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
@Aspect
public class UmtNotificationAspect {

    private static final String USER_NOTIFICATION_CONFIG_KEY = "minos.user.notification.provider";

    private NotificationService notificationService;
    private NotificationFactory notificationFactory;
    private ThreadLocal<Password> newPassword = new ThreadLocal<Password>();


    /**
     * Setter to inject a {@link NotificationService}.
     * 
     * @param notificationService
     */
    @Required
    public void setNotificationService(NotificationService notificationService) {

        this.notificationService = notificationService;
    }


    /**
     * Setter injecting factory to create {@link Notification} instance
     * 
     * @param argNotificationFactory {@link org.synyx.minos.core.notification.NotificationFactory}
     */
    @Required
    public void setNotificationFactory(NotificationFactory argNotificationFactory) {

        notificationFactory = argNotificationFactory;
    }


    /**
     * Intercepts password creations and stores it in a {@code ThreadLocal}.
     * 
     * @param newPassword
     */
    @AfterReturning(pointcut = "execution(* org.synyx.minos.umt.service.PasswordCreator.generatePassword())", returning = "newPassword")
    public void createNewPassword(Password newPassword) {

        this.newPassword.set(newPassword);
    }


    /**
     * Sends a notification to the user who is about to be created.
     */
    @Around("execution(* org.synyx.minos.umt.service.UserManagement.save(..)) && args(user)")
    public Object sendNewPassword(ProceedingJoinPoint joinPoint, User user) throws Throwable {

        boolean passwordNotificationRequired = user.isNew();

        Object result = joinPoint.proceed();

        Password passwordToSet = newPassword.get();

        if (passwordNotificationRequired && null != passwordToSet) {
            NotificationContext context = createContext();

            // Execute notification
            Notification notification = notificationFactory.create(user, user.getUsername(), passwordToSet);
            notificationService.notify(notification, context);

            // Clear password
            newPassword.set(null);
        }

        return result;
    }


    /**
     * Create a notification context with the notification provider the user selected
     * 
     * @return {@link ConfigBasedNotificationContext} instance
     */
    protected ConfigBasedNotificationContext createContext() {

        return new ConfigBasedNotificationContext(USER_NOTIFICATION_CONFIG_KEY);
    }
}
