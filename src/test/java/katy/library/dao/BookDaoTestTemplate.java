package katy.library.dao;

import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;
import katy.library.dao.sql.AuthorSqlDao;
import katy.library.dao.sql.BookSqlDao;
import katy.library.dao.sql.SqlDaoTestUtils;
import katy.library.model.Author;
import katy.library.model.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

abstract public class BookDaoTestTemplate {

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    private Author author2 = Author.builder()
            .id(2)
            .firstName("Jane")
            .lastName("Austen")
            .dateOfBirth(LocalDate.of(1775, Month.DECEMBER, 16))
            .build();

    private Book book1 = Book.builder()
            .id(1)
            .title("DiscWorld")
            .author(author1)
            .build();

    private Book book2 = Book.builder()
            .id(2)
            .title("Emma")
            .author(author2)
            .build();

    protected abstract BookDao getBookDao();

    protected abstract AuthorDao getAuthorDao();

    @Test
    void testgetById() {

        BookDao dao = getBookDao();
        AuthorDao authorDao = getAuthorDao();

        final Author author = authorDao.create(book1.getAuthor());
        final Book created = dao.create(book1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
        assertEquals(author, created.getAuthor());
    }

    @Test
    void testcreate() {

        BookDao dao = getBookDao();
        AuthorDao authorDao = getAuthorDao();

        final Author author = authorDao.create(book1.getAuthor());
        final Book created = dao.create(book1.withAuthor(author));

        assertEquals(book1.withId(created.getId()), created);
        assertEquals(author, created.getAuthor());
    }

    @Test
    void testupdate() {

        BookDao dao = getBookDao();
        AuthorDao authorDao = getAuthorDao();

        final Author author1 = authorDao.create(book1.getAuthor());
        final Author author2 = authorDao.create(book2.getAuthor());

        final Book created = dao.create(book1);

        assertEquals(created, book1);
        assertEquals(created.getAuthor(), author1);

        final Book updated = dao.update(book2.withId(created.getId()));

        assertEquals(updated, book2.withId(created.getId()));
        assertEquals(updated.getAuthor(), author2);

        assertNotEquals(updated, book1);
        assertNotEquals(updated.getAuthor(), author1);
    }

    @Test
    void testdelete() {

        BookDao dao = getBookDao();
        AuthorDao authorDao = getAuthorDao();

        final Author author = authorDao.create(book1.getAuthor());

        final Book created = dao.create(book1);

        final Optional<Book> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByAuthor() {

        BookDao dao = getBookDao();

        final Book created1 = dao.create(book1);

        final Book created2 = dao.create(book2);

        final List<Book> daoByName = dao.findByAuthor(created1.getAuthor());

        assertEquals(daoByName.get(0), created1);
    }

    @Test
    void testfindByTitle() {

        BookDao dao = getBookDao();

        final Book created1 = dao.create(book1);

        final Book created2 = dao.create(book2);

        final List<Book> daoByName = dao.findByTitle(created2.getTitle());

        assertEquals(daoByName.get(0), created2);
    }

    @Test
    void testfullList() {

        BookDao dao = getBookDao();

        final Book created1 = dao.create(book1);
        final Book created2 = dao.create(book2);

        final List<Book> daoByName = dao.fullList();

        assertEquals(daoByName.get(0), created1);
        assertEquals(daoByName.get(1), created2);
    }
}