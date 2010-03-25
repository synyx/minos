package org.synyx.minos.core.notification;

import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.synyx.hera.metadata.AbstractMetadataBasedPlugin;
import org.synyx.minos.core.domain.User;


/**
 * Base implementation of {@link NotificationProvider} leveraging a
 * {@link TaskExecutor} for noification execution. Implementations can get a
 * custom executor configured and {@link #notifyInExecutor(Notification, User)}
 * will be executed with this executor. Default executor is
 * {@value #DEFAULT_TASK_EXECUTOR}. As {@link NotificationProvider}s are
 * supposed to be plugins we extend {@link AbstractMetadataBasedPlugin}.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class AbstractNotificationProvider extends
        AbstractMetadataBasedPlugin implements NotificationProvider {

    private static final TaskExecutor DEFAULT_TASK_EXECUTOR =
            new SyncTaskExecutor();

    private TaskExecutor taskExecutor = DEFAULT_TASK_EXECUTOR;


    /**
     * Creates a new {@link AbstractNotificationProvider}
     * 
     * @param name
     * @param version
     */
    public AbstractNotificationProvider(String name, String version) {

        super(name, version);
    }


    /**
     * Sets an implementation of {@link TaskExecutor}. If {@code null} is
     * provided {@value #DEFAULT_TASK_EXECUTOR} is used again.
     * 
     * @param taskExecuter
     */
    public void setTaskExecuter(TaskExecutor taskExecuter) {

        this.taskExecutor =
                (null == taskExecuter) ? DEFAULT_TASK_EXECUTOR : taskExecuter;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.synyx.minos.core.notification.NotificationProvider#notify(com.synyx
     * .minos.core.notification.Notification)
     */
    public final void notify(final Notification notification,
            final User recipient) {

        taskExecutor.execute(new Runnable() {

            public void run() {

                notifyInExecutor(notification, recipient);
            }
        });
    }


    /**
     * Callback method for actual notification logic to be executed with the
     * configured {@link TaskExecutor}.
     * 
     * @param notification
     * @param recipient
     */
    public abstract void notifyInExecutor(Notification notification,
            User recipient);
}
