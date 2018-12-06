package katy.library.dao;

import katy.library.model.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


abstract public class AuthorDaoTestTemplate {

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

    protected abstract AuthorDao getAuthorDao();

    @Test
    void testgetById() {

        AuthorDao dao = getAuthorDao();

        final Author created = dao.create(author1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testcreate() {

        AuthorDao dao = getAuthorDao();

        final Author created = dao.create(author1);

        assertEquals(author1.withId(created.getId()), created);
    }

    @Test
    void testupdate() {

        AuthorDao dao = getAuthorDao();

        final Author created = dao.create(author1);
        assertEquals(created, author1);

        final Author updated = dao.update(author2.withId(created.getId()));

        assertEquals(updated, author2.withId(created.getId()));

        assertNotEquals(updated, author1);
    }

    @Test
    void testdelete() {

        AuthorDao dao = getAuthorDao();

        final Author created = dao.create(author1);

        final Optional<Author> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByName() {

        AuthorDao dao = getAuthorDao();

        final Author created1 = dao.create(author1);
        final Author created2 = dao.create(author2);

        final List<Author> daoByName = dao.findByName("Pratchett");

        assertEquals(daoByName.get(0), created1);
    }

    @Test
    void testfullList() {

        AuthorDao dao = getAuthorDao();

        final Author created1 = dao.create(author1);
        final Author created2 = dao.create(author2);

        final List<Author> daoByName = dao.fullList();

        assertEquals(daoByName.get(0), created1);
        assertEquals(daoByName.get(1), created2);
    }
}