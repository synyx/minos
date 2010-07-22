package org.synyx.minos.test;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.synyx.minos.test.database.DatabasePopulatorConnectionCallback;


/**
 * Abstract test class that takes care of inserting data from SQL-Scripts into the database before each test is
 * executed. Override {@link #getSqlFilesToPolulate()} to return the script-resources to be executed. For now this needs
 * exactly one {@link DataSource} configured in the {@link ApplicationContext}.
 * 
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 * @author Oliver Gierke - gierke@synyx.de
 */
public abstract class DatabasePopulationAwareTest {

    @Autowired(required = false)
    protected DataSource dataSource;


    /**
     * Populates the Database before a test is executed
     */
    @Before
    public void populateDatabase() {

        List<? extends Resource> resources = getSqlFilesToPolulate();

        boolean dataSourceAvailable = dataSource != null;
        boolean populationRequired = !resources.isEmpty();

        if (populationRequired && !dataSourceAvailable) {
            throw new IllegalStateException(
                    "No DataSource could be autowired. This is needed to populate the database with sql-scripts given: "
                            + resources.toString());
        }

        if (populationRequired) {

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute(new DatabasePopulatorConnectionCallback(resources));
        }
    }


    /**
     * Returns an array of resources that will be handled as sql-scripts and inserted into the database before any tests
     * are executed.
     * 
     * @return resources to be inserted into the database
     */
    public List<? extends Resource> getSqlFilesToPolulate() {

        return Collections.emptyList();
    }
}
