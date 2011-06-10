package org.synyx.minos.test;

import org.springframework.aop.framework.Advised;


/**
 * Utility methods to work with mock objects.
 *
 * @author Oliver Gierke
 */
public abstract class MockUtils {

    private MockUtils() {
    }

    /**
     * Unwraps a possibly advised mock to add expectations on it.
     *
     * @param advisedMock
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T unwrap(T advisedMock) throws Exception {

        T mock = advisedMock;

        while (mock instanceof Advised) {
            Advised advised = (Advised) mock;
            mock = (T) advised.getTargetSource().getTarget();
        }

        return mock;
    }
}
