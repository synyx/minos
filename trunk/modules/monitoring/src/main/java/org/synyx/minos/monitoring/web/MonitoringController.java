package org.synyx.minos.monitoring.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

import org.synyx.minos.monitoring.service.MonitoringService;
import org.synyx.minos.monitoring.service.MonitoringTest;
import org.synyx.minos.monitoring.service.MonitoringTestResult;

import java.util.List;


/**
 * Controller-Class that handles monitoring-requests
 *
 * @author  Marc Kannegiesser - kannegiesser@synyx.de
 */
@Controller
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @RequestMapping(value = "/public/monitoring", method = GET)
    @ResponseBody
    public List<MonitoringTest> getMonitoringTests() {

        return monitoringService.getMonitoringTests();
    }


    @RequestMapping(value = "/public/monitoring/{name}", method = { GET, POST })
    @ResponseBody
    public MonitoringTestResult executeTest(@PathVariable("name") String name) {

        return monitoringService.executeTest(name);
    }
}
