package org.synyx.minos.core.notification;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import org.synyx.minos.core.domain.User;


/**
 * Implementation of {@link NotificationProvider} that sends the notification via email.
 *
 * @author Oliver Gierke - gierke@synyx.de
 */
public class EmailNotificationProvider extends AbstractNotificationProvider {

    private MailSender mailSender;

    /**
     * Creates a new {@link EmailNotificationProvider}.
     */
    public EmailNotificationProvider(MailSender mailSender) {

        super("minos.notification.provider.email", "1.0");
        this.mailSender = mailSender;
    }

    @Override
    public void notifyInExecutor(Notification notification, User recipient) {

        SimpleMailMessage mail = new SimpleMailMessage();

        if (notification.hasSender()) {
            User sender = notification.getSender();
            mail.setFrom(sender.getEmailAddress());
        }

        mail.setTo(recipient.getEmailAddress());
        mail.setText(notification.getMessage());

        mailSender.send(mail);
    }
}
