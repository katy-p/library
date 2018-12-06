package katy.library.dao.sql;


import katy.library.dao.BookDao;
import katy.library.model.Author;
import katy.library.model.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookSqlDao implements BookDao {

    private DataSource ds;

    public BookSqlDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Optional<Book> getById(long id) {

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select b.id, b.title, b.author_id, a.first_name, a.last_name, a.date_of_burth " +
                             "from book as b " +
                             "left join author as a on b.author_id = a.id " +
                             "where b.id = ?"
             )) {
            statement.setLong(1, id);
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                if (resultSet.next()) {
                    Book book =
                            Book.builder()
                                    .id(resultSet.getLong("id"))
                                    .title(resultSet.getString("title"))
                                    .author(
                                            Author.builder()
                                                    .id(resultSet.getLong("author_id"))
                                                    .firstName(resultSet.getString("first_name"))
                                                    .lastName(resultSet.getString("last_name"))
                                                    .dateOfBirth(resultSet.getTimestamp("date_of_burth").toLocalDateTime().toLocalDate())
                                                    .build()
                                    )
                                    .build();
                    return Optional.of(book);

                } else {
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
    public Book create(Book entry) {

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "insert into book (title, author_id) " +
                          "values (?, ?) " +
                          "returning id"
             )) {
            statement.setString(1, entry.getTitle());
            statement.setLong(2, entry.getAuthor().getId());
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("Can't get inserted id.");
                }
                return entry.withId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }
    }

    @Override
    public Book update(Book book) {

        Optional<Book> bookOptional = getById(book.getId());

        if (!bookOptional.isPresent()) {
            return create(book);

        } else {
            try (Connection conn = ds.getConnection();
                 PreparedStatement statement = conn.prepareStatement(
                         "update book " +
                                 "set title = ?, " +
                                    "author_id = ? " +
                                 "WHERE id = ?"
                 )) {
                statement.setString(1, book.getTitle());
                statement.setLong(2, book.getAuthor().getId());
                statement.setLong(3, book.getId());
                statement.execute();

                return getById(book.getId()).orElseThrow(() -> new RuntimeException("Should never get here."));

            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Book> delete(long id) {

        Optional<Book> book = getById(id);

        if (book.isPresent()) {
            try (Connection conn = ds.getConnection();
                 PreparedStatement statement = conn.prepareStatement(
                         "delete from book " +
                                 "where id = ?"
                 )) {
                statement.setLong(1, id);
                statement.execute();

                return book;

            } catch (SQLException e) {
                throw new RuntimeException(e); //e.printStackTrace();
            }
        }

        return book;
    }

    @Override
    public List<Book> findByAuthor(Author author) {

        List<Book> res = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select b.id, b.title, b.author_id " +
                             "from book as b " +
                             "where b.author_id = ?"
             )) {
            statement.setLong(1, author.getId());
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                while (resultSet.next()) {
                    res.add(Book.builder()
                            .id(resultSet.getLong("id"))
                            .title(resultSet.getString("title"))
                            .author(author)
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
    public List<Book> findByTitle(String title) {

        List<Book> res = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select b.id, b.title, b.author_id, a.first_name, a.last_name, a.date_of_burth " +
                             "from book as b " +
                             "left join author as a on b.author_id = a.id " +
                             "where b.title = ?"
             )) {
            statement.setString(1, title);
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                while (resultSet.next()) {
                    res.add(Book.builder()
                            .id(resultSet.getLong("id"))
                            .title(resultSet.getString("title"))
                            .author(
                                    Author.builder()
                                            .id(resultSet.getLong("author_id"))
                                            .firstName(resultSet.getString("first_name"))
                                            .lastName(resultSet.getString("last_name"))
                                            .dateOfBirth(resultSet.getTimestamp("date_of_burth").toLocalDateTime().toLocalDate())
                                            .build()
                            )
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
    public List<Book> fullList() {

        List<Book> res = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "select b.id, b.title, b.author_id, a.first_name, a.last_name, a.date_of_burth " +
                             "from book as b " +
                             "left join author as a on b.author_id = a.id "
             )) {
            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {

                while (resultSet.next()) {
                    res.add(Book.builder()
                            .id(resultSet.getLong("id"))
                            .title(resultSet.getString("title"))
                            .author(
                                    Author.builder()
                                            .id(resultSet.getLong("author_id"))
                                            .firstName(resultSet.getString("first_name"))
                                            .lastName(resultSet.getString("last_name"))
                                            .dateOfBirth(resultSet.getTimestamp("date_of_burth").toLocalDateTime().toLocalDate())
                                            .build()
                            )
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
