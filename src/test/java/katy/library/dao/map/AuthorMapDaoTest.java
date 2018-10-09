package katy.library.dao.map;

import katy.library.model.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapDaoTest {

    private Author author1 = Author.builder()
            .id(-1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    private Author author2 = Author.builder()
            .id(-2)
            .firstName("Jane")
            .lastName("Austen")
            .dateOfBirth(LocalDate.of(1775, Month.DECEMBER, 16))
            .build();
    @Test
    void testgetById() {

        AuthorMapDao dao = new AuthorMapDao();

        final Author created = dao.create(author1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testcreate() {

        AuthorMapDao dao = new AuthorMapDao();

        final Author created = dao.create(author1);

        assertEquals(author1.withId(created.getId()), created);
    }

    @Test
    void testupdate() {

        AuthorMapDao dao = new AuthorMapDao();

        final Author created1 = dao.create(author1);

        final Author created2 = dao.create(author2);

        Author author3 = created1.withId(created2.getId());

        final Author updated = dao.update(author3);

        assertEquals(updated, author3);

        assertNotEquals(updated, created2);
    }

    @Test
    void testdelete() {

        AuthorMapDao dao = new AuthorMapDao();

        final Author created = dao.create(author1);

        final Optional<Author> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByName() {

        AuthorMapDao dao = new AuthorMapDao();

        final Author created1 = dao.create(author1);

        final Author created2 = dao.create(author2);

        final List<Author> daoByName = dao.findByName("Pratchett");

        assertEquals(daoByName.get(0), created1);
    }
}