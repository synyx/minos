package org.synyx.minos.test.database;

import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * {@link ConnectionCallback} that populates a {@link Connection} with the given {@link Resource}s using a
 * {@link ResourceDatabasePopulator}.
 *
 * @see JdbcTemplate
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class DatabasePopulatorConnectionCallback implements ConnectionCallback<Void> {

    private ResourceDatabasePopulator polulator;

    /**
     * Creates a new {@link DatabasePopulatorConnectionCallback} to populate a database with the given SQL files.
     *
     * @param files
     */
    public DatabasePopulatorConnectionCallback(List<? extends Resource> files) {

        polulator = new ResourceDatabasePopulator();

        for (Resource file : files) {
            polulator.addScript(file);
        }
    }

    @Override
    public Void doInConnection(Connection connection) throws SQLException {

        polulator.populate(connection);

        return null;
    }
}
