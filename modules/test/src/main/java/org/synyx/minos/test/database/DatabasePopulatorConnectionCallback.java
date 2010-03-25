package org.synyx.minos.test.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;


/**
 * {@link ConnectionCallback} that populates a {@link Connection} with the given
 * {@link Resource}s using a {@link ResourceDatabasePopulator}.
 * 
 * @see JdbcTemplate
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public class DatabasePopulatorConnectionCallback implements
        ConnectionCallback<Void> {

    private ResourceDatabasePopulator polulator;


    /**
     * Creates a new {@link DatabasePopulatorConnectionCallback} to populate a
     * database with the given SQL files.
     * 
     * @param files
     */
    public DatabasePopulatorConnectionCallback(List<? extends Resource> files) {

        polulator = new ResourceDatabasePopulator();
        for (Resource file : files) {
            polulator.addScript(file);
        }
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.jdbc.core.ConnectionCallback#doInConnection(java.
     * sql.Connection)
     */
    @Override
    public Void doInConnection(Connection connection) throws SQLException,
            DataAccessException {

        polulator.populate(connection);
        return null;
    }
}
