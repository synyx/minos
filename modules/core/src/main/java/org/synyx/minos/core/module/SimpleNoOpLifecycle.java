package org.synyx.minos.core.module;

/**
 * Simple empty {@link Lifecycle} implementations to free subclasses from the
 * need to implement all lifecycle methods. This implementation will simply do
 * nothing for every invocation.
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class SimpleNoOpLifecycle implements Lifecycle {

    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.module.Lifecycle#onStart()
     */
    @Override
    public void onStart() throws ModuleLifecycleException {

    }


    /*
     * (non-Javadoc)
     * 
     * @see com.synyx.minos.core.module.Lifecycle#onStop()
     */
    @Override
    public void onStop() throws ModuleLifecycleException {

    }


    /*
     * (non-Javadoc)
     * 
     * @see org.synyx.minos.core.module.Lifecycle#install()
     */
    @Override
    public void install() throws ModuleLifecycleException {

    }
}
