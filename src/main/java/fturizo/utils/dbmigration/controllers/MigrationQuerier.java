package fturizo.utils.dbmigration.controllers;

import fturizo.utils.dbmigration.entities.Migration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.omnifaces.util.Messages;

/**
 *
 * @author Fabio Andres
 */
@Named
public class MigrationQuerier {

    private final static String MIGRATION_QUERY = "select * from \"schema_version\" order by version_rank";

    @Inject
    public DataSource dataSource;

    public List<Migration> getMigrations() {
        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(MIGRATION_QUERY)) {
                List<Migration> migrationList = new ArrayList<>();
                while (rs.next()) {
                    migrationList.add(buildMigration(rs));
                }
                return migrationList;
            } catch (SQLException ex) {
                Messages.addGlobalError("Error querying migration data: {0} - {1}", ex.getErrorCode(), ex.getMessage());
                ex.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    private Migration buildMigration(ResultSet rs) throws SQLException {
        return new Migration(rs.getLong("\"version_rank\""),
                rs.getLong("\"installed_rank\""),
                rs.getString("\"version\""),
                rs.getString("\"description\""),
                rs.getString("\"type\""),
                rs.getString("\"script\""),
                rs.getString("\"checksum\""),
                rs.getString("\"installed_by\""),
                rs.getDate("\"installed_on\""),
                rs.getLong("\"execution_time\""),
                rs.getInt("\"sucess\"") == 0);
    }
}
