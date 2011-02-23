package org.synyx.minos.core.module;

/**
 * Simple empty {@link Lifecycle} implementations to free subclasses from the need to implement all lifecycle methods.
 * This implementation will simply do nothing for every invocation.
 *
 * @author  Oliver Gierke - gierke@synyx.de
 */
public class SimpleNoOpLifecycle implements Lifecycle {

    @Override
    public void onStart() throws ModuleLifecycleException {
    }


    @Override
    public void onStop() throws ModuleLifecycleException {
    }


    @Override
    public void install() throws ModuleLifecycleException {
    }
}
