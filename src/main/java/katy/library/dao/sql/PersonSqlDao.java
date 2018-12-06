package katy.library.dao.sql;


import katy.library.dao.PersonDao;
import katy.library.model.Person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonSqlDao implements PersonDao {

    private DataSource ds;

    public PersonSqlDao(DataSource ds) {

        this.ds = ds;
    }

    @Override
    public Optional<Person> getById(long id) {

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select p.id, p.first_name, p.last_name, p.date_of_burth " +
                          "from person as p " +
                          "where p.id = ?"
             )) {
            statement.setLong(1, id);
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                if (resultSet.next()) {
                    Person person = Person.builder()
                                    .id(resultSet.getLong("id"))
                                    .firstName(resultSet.getString("first_name"))
                                    .lastName(resultSet.getString("last_name"))
                                    .dateOfBirth(resultSet.getTimestamp("date_of_burth").toLocalDateTime().toLocalDate())
                                    .build();
                    return Optional.of(person);

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
    public Person create(Person person) {

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "insert into person (first_name, last_name, date_of_burth) " +
                          "values (?, ?, ?)" +
                             "returning id"
             )) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setTimestamp(3, Timestamp.valueOf(person.getDateOfBirth().atStartOfDay()));
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("Can't get inserted id.");
                }
                return person.withId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }
    }

    @Override
    public Person update(Person person) {

        Optional<Person> personOptional = getById(person.getId());

        if (!personOptional.isPresent()) {
            return create(person);

        } else {
            try (Connection conn = ds.getConnection();
                 PreparedStatement statement = conn.prepareStatement(
                         "update person " +
                              "set first_name = ?, " +
                                 "last_name = ?, " +
                                 "date_of_burth = ?" +
                              "WHERE id = ?"
                 )) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setTimestamp(3, Timestamp.valueOf(person.getDateOfBirth().atStartOfDay()));
                statement.setLong(4, person.getId());
                statement.execute();

                return getById(person.getId()).orElseThrow(() -> new RuntimeException("Should never get here."));

            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Person> delete(long id) {

        Optional<Person> person = getById(id);

        if (person.isPresent()) {
            try (Connection conn = ds.getConnection();
                 PreparedStatement statement = conn.prepareStatement(
                         "delete from person " +
                              "where id = ?"
                 )) {
                statement.setLong(1, id);
                statement.execute();

                return person;

            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        }

        return person;
    }

    @Override
    public List<Person> findByName(String lastName) {

        List <Person> res = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select p.id, p.first_name, p.last_name, p.date_of_burth " +
                          "from person as p " +
                          "where p.last_name = ?"
             )) {
            statement.setString(1, lastName);
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                while (resultSet.next()) {
                    res.add(Person.builder()
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
    public List<Person> fullList() {

        List <Person> res = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select p.id, p.first_name, p.last_name, p.date_of_burth " +
                             "from person as p "
             )) {
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {

                while (resultSet.next()) {
                    res.add(Person.builder()
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
