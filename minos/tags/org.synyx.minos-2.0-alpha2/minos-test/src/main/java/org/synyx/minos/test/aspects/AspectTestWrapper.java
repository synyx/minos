package org.synyx.minos.test.aspects;

import static org.mockito.Mockito.*;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;


/**
 * Helper class to ease testing of {@code @Aspect} pointcut matches. The wrapper
 * will create {@link EasyMock} proxies for the classes handed into the factory
 * method.
 * <p>
 * The typical use case looks as follows;
 * <ol>
 * <li>Create an instance via {@link #create(Class, Class)}</li>
 * <li>Access the aspect instance and the target</li>
 * <li>Programatically expect an aspect method to be triggered</li>
 * <li>Call a method on the target</li>
 * <li>Verify expected behaviour on the aspect</li>
 * </ol>
 * 
 * @author Oliver Gierke - gierke@synyx.de
 */
public class AspectTestWrapper<Aspect, Target> {

    private final Aspect aspect;
    private final Target target;

    private final Target proxy;


    /**
     * Creates a new {@link AspectTestWrapper} for the given aspect type and
     * target type.
     * 
     * @param aspectType
     * @param targetType
     */
    @SuppressWarnings("unchecked")
    private AspectTestWrapper(Class<Aspect> aspectType, Class<Target> targetType) {

        this.aspect = mock(aspectType);
        this.target = mock(targetType);

        AspectJProxyFactory factory = new AspectJProxyFactory(this.target);
        factory.addAspect(this.aspect);
        factory.addInterface(targetType);

        if (!targetType.isInterface()) {
            factory.setProxyTargetClass(true);
        }

        proxy = (Target) factory.getProxy();
    }


    /**
     * Create a new {@link AspectTestWrapper}.
     * 
     * @param <Aspect>
     * @param <Service>
     * @param aspectType
     * @param serviceType
     * @return
     */
    public static <Aspect, Service> AspectTestWrapper<Aspect, Service> create(
            Class<Aspect> aspectType, Class<Service> serviceType) {

        return new AspectTestWrapper<Aspect, Service>(aspectType, serviceType);
    }


    /**
     * Returns the aspect mock to record expected invocations on.
     * 
     * @return
     */
    public Aspect getAspect() {

        return aspect;
    }


    /**
     * Returns the target to invoke actual method invocations on.
     * 
     * @return
     */
    public Target getTarget() {

        return proxy;
    }
}
