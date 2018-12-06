package katy.library.dao.sql;


import org.apache.commons.io.IOUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDaoTestUtils {

    public static void createTables(DataSource source) throws SQLException, IOException {
        final String sql =
                IOUtils.resourceToString("/db/create_tables.sql", Charset.forName("UTF-8"));

        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    public static void clearTables(DataSource source) {
        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate book, author, person cascade");
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }

    }
}
