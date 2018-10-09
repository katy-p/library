package katy.library.dao.map;

import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class BookMapDaoTest {

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
            .id(1)
            .title("Emma")
            .author(author2)
            .build();

    @Test
    void testgetById() {

        BookMapDao dao = new BookMapDao();

        final Book created = dao.create(book1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testcreate() {

        BookMapDao dao = new BookMapDao();

        final Book created = dao.create(book1);

        assertEquals(book1.withId(created.getId()), created);
    }

    @Test
    void testupdate() {

        BookMapDao dao = new BookMapDao();

        final Book created1 = dao.create(book1);

        final Book created2 = dao.create(book2);

        Book book3 = created1.withId(created2.getId());

        final Book updated = dao.update(book3);

        assertEquals(updated, book3);

        assertNotEquals(updated, created2);
    }

    @Test
    void testdelete() {

        BookMapDao dao = new BookMapDao();

        final Book created = dao.create(book1);

        final Optional<Book> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByAuthor() {

        BookMapDao dao = new BookMapDao();

        final Book created1 = dao.create(book1);

        final Book created2 = dao.create(book2);

        final List<Book> daoByName = dao.findByAuthor(author1);

        assertEquals(daoByName.get(0), created1);
    }

    @Test
    void testfindByTitle() {

        BookMapDao dao = new BookMapDao();

        final Book created1 = dao.create(book1);

        final Book created2 = dao.create(book2);

        final List<Book> daoByName = dao.findByTitle("Emma");

        assertEquals(daoByName.get(0), created2);
    }
}