package katy.library.dao.sql;

import katy.library.dao.AuthorDao;
import katy.library.model.Author;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorSqlDao implements AuthorDao {

    private DataSource ds;

    public AuthorSqlDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Optional<Author> getById(long id) {

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select a.id, a.first_name, a.last_name, a.date_of_burth " +
                          "from author as a " +
                          "where a.id = ?"
             )) {
            statement.setLong(1, id);
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                if (resultSet.next()) {
                    Author author =
                        Author.builder()
                            .id(resultSet.getLong("id"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .dateOfBirth(resultSet.getTimestamp("date_of_burth").toLocalDateTime().toLocalDate())
                            .build();
                    return Optional.of(author);

                }else {
                    return Optional.empty();

                }
            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }
    }

    @Override
    public Author create(Author author) {

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "insert into author(first_name, last_name, date_of_burth) " +
                             "values (?, ?, ?)" +
                                 "returning id"
             )) {
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setTimestamp(3, Timestamp.valueOf(author.getDateOfBirth().atStartOfDay()));
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("Can't get inserted id.");
                }
                return author.withId(resultSet.getLong("id"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }
    }

    @Override
    public Author update(Author author) {

        Optional<Author> authorOptional = getById(author.getId());

        if (!authorOptional.isPresent()) {
            return create(author);

        } else {
            try (Connection conn = ds.getConnection();
                 PreparedStatement statement = conn.prepareStatement(
                         "update author " +
                              "set first_name = ?, " +
                                 "last_name = ?, " +
                                 "date_of_burth = ?" +
                              "WHERE id = ?"
                 )) {
                statement.setString(1, author.getFirstName());
                statement.setString(2, author.getLastName());
                statement.setTimestamp(3, Timestamp.valueOf(author.getDateOfBirth().atStartOfDay()));
                statement.setLong(4, author.getId());
                statement.execute();

                return getById(author.getId()).orElseThrow(() -> new RuntimeException("Should never get here."));

            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Author> delete(long id) {

        Optional<Author> author = getById(id);

        if (author.isPresent()) {
            try (Connection conn = ds.getConnection();
                 PreparedStatement statement = conn.prepareStatement(
                         "delete from author " +
                              "where id = ?"
                 )) {
                statement.setLong(1, id);
                statement.execute();

                return author;
            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        }

        return author;
    }

    @Override
    public List<Author> findByName(String lastName) {

        List <Author> res = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select a.id, a.first_name, a.last_name, a.date_of_burth " +
                          "from author as a " +
                          "where a.last_name = ?"
             )) {
            statement.setString(1, lastName);
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                while (resultSet.next()) {
                    res.add(Author.builder()
                               .id(resultSet.getLong("id"))
                               .firstName(resultSet.getString("first_name"))
                               .lastName(resultSet.getString("last_name"))
                               .dateOfBirth(resultSet.getTimestamp("date_of_burth").toLocalDateTime().toLocalDate())
                               .build());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<Author> fullList() {

        List <Author> res = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select a.id, a.first_name, a.last_name, a.date_of_burth " +
                          "from author as a "
             )) {
             statement.execute();
             try (ResultSet resultSet = statement.getResultSet()) {

                while (resultSet.next()) {
                    res.add(Author.builder()
                            .id(resultSet.getLong("id"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .dateOfBirth(resultSet.getTimestamp("date_of_burth").toLocalDateTime().toLocalDate())
                            .build());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }

        return res;
    }

}
