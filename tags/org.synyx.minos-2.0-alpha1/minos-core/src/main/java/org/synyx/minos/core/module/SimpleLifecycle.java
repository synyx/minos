package org.synyx.minos.core.module;

/**
 * Simple empty {@link Lifecycle} implementations to free subclasses from the
 * need to implement all lifecycle methods. This implementation will simply do
 * nothing for every invocation.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class SimpleLifecycle implements Lifecycle {

    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.module.Lifecycle#onStart()
     */
    public void onStart() {

    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.module.Lifecycle#onStop()
     */
    public void onStop() {

    }
}
