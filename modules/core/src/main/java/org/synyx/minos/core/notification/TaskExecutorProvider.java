package org.synyx.minos.core.notification;

import org.springframework.core.task.TaskExecutor;


/**
 * Interface for components that provide their own {@link TaskExecutor} and expect to be invoked with this executor.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public interface TaskExecutorProvider {

    /**
     * Returns the {@link TaskExecutor}. Must not return {@code null}.
     * 
     * @return
     */
    TaskExecutor getTaskExecutor();
}
