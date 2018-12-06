package katy.library.dao.sql;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneralSqlDaoTest {

    public static void createTables(DataSource source) throws SQLException {
        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table author (" +
                    "id serial primary key not null," +
                    "first_name text, " +
                    "last_name text, " +
                    "date_of_burth timestamp )");
            statement.executeUpdate("create table book (" +
                    "id serial primary key  not null, " +
                    "title text, " +
                    "author_id int references author (id))");
        }
    }

    public static void clearTables(DataSource source) {
        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from book");
            statement.executeUpdate("delete from author");
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }

    }
}
