package fturizo.utils.dbmigration.controllers;

import fturizo.utils.dbmigration.entities.Migration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.omnifaces.util.Messages;

/**
 *
 * @author Fabio Andres
 */
@Named
@RequestScoped
public class MigrationQuerier {

    private final static String MIGRATION_QUERY = "select * from \"schema_version\" order by \"version_rank\"";

    @Inject
    private DataSource dataSource;
    private final List<Migration> migrationList = new ArrayList<>();
    
    public List<Migration> getMigrations() {
        return migrationList;
    }

    public void queryMigrations() {
        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(MIGRATION_QUERY)) {
                while (rs.next()) {
                    migrationList.add(buildMigration(rs));
                }
            } catch (SQLException ex) {
                Messages.addGlobalError("Error querying migration data: {0} - {1}", ex.getErrorCode(), ex.getMessage());
            }
        }
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
                rs.getInt("\"success\"") == 0);
    }
}
