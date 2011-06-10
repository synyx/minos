package org.synyx.minos.monitoring.service;

import java.util.List;


public interface MonitoringService {

    List<MonitoringTest> getMonitoringTests();


    MonitoringTestResult executeTest(MonitoringTest test);


    MonitoringTestResult executeTest(String name);
}
