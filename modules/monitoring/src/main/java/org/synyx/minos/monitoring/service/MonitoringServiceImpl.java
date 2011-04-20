package org.synyx.minos.monitoring.service;

import org.synyx.minos.util.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MonitoringServiceImpl implements MonitoringService {

    private List<MonitoringTest> tests;

    @Override
    public List<MonitoringTest> getMonitoringTests() {

        return Collections.unmodifiableList(tests);
    }


    @Override
    public MonitoringTestResult executeTest(MonitoringTest test) {

        try {
            return test.execute();
        } catch (Exception e) {
            return MonitoringTestResult.error("Unexpected error:  " + e.getMessage() + " ["
                    + e.getClass().getSimpleName() + "]");
        }
    }


    @Override
    public MonitoringTestResult executeTest(String name) {

        MonitoringTest test = findByName(name);

        return executeTest(test);
    }


    private MonitoringTest findByName(String name) {

        Assert.notNull(name);

        for (MonitoringTest test : tests) {
            if (name.equals(test.getName())) {
                return test;
            }
        }

        // TODO: Think about throwing an exception here
        return null;
    }


    public void setTests(List<MonitoringTest> tests) {

        checkNameUniqueness(tests);

        this.tests = tests;
    }


    private void checkNameUniqueness(List<MonitoringTest> tests) {

        Set<String> names = new HashSet<String>();

        for (MonitoringTest test : tests) {
            if (names.contains(test.getName())) {
                throw new IllegalArgumentException("Names of tests must be unique. More than one test with name "
                    + test.getName() + " found.");
            }
        }
    }
}
