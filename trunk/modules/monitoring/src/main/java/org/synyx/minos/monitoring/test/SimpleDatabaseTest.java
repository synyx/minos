package org.synyx.minos.monitoring.test;

import org.synyx.hades.domain.Page;
import org.synyx.hades.domain.PageRequest;
import org.synyx.hades.domain.Pageable;

import org.synyx.minos.core.domain.User;
import org.synyx.minos.monitoring.service.MonitoringTest;
import org.synyx.minos.monitoring.service.MonitoringTestResult;
import org.synyx.minos.umt.dao.UserDao;


/**
 * Tests a simple read from the database ({@link User}).
 *
 * @author  Marc Kannegießer - kannegiesser@synyx.de
 */
public class SimpleDatabaseTest extends MonitoringTest {

    private static final String DEFAULT_DESCRIPTION = "Simple Database Test";
    private static final String DEFAULT_NAME = "DB-READ";

    private static int expectedPageSize = 1;

    private UserDao userDao;

    public SimpleDatabaseTest(UserDao userDao) {

        super(DEFAULT_NAME, DEFAULT_DESCRIPTION);
        this.userDao = userDao;
    }

    @Override
    public MonitoringTestResult execute() {

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
