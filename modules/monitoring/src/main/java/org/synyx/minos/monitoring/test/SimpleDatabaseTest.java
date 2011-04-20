package org.synyx.minos.monitoring.test;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.PageRequest;
import org.synyx.hades.domain.Pageable;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.monitoring.service.MonitoringTest.MonitoringTestCommand;
import org.synyx.minos.monitoring.service.MonitoringTestResult;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Tests a simple read from the database ({@link User}).
 *
 * @author  Marc Kannegießer - kannegiesser@synyx.de
 */
public class SimpleDatabaseTest implements MonitoringTestCommand {

    private static int expectedPageSize = 1;

    private UserDao userDao;

    public SimpleDatabaseTest(UserDao userDao) {

        super();
        this.userDao = userDao;
    }

    @Override
    public MonitoringTestResult getResult() {

        Pageable page = new PageRequest(0, expectedPageSize);

        Page<User> users = userDao.readAll(page);

        int pageSize = users.getSize();

        if (pageSize == expectedPageSize) {
            return MonitoringTestResult.success("Successfull read from the database.");
        } else {
            return MonitoringTestResult.error("Could not read user from the database.");
        }
    }
}
