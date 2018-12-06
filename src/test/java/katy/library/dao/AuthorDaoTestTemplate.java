package katy.library.dao.sql;

import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;
import katy.library.model.Author;
import katy.library.model.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class AuthorSqlDaoTest {

    private static EmbeddedPostgreSQL embeddedPostgreSQL;
    private static DataSource source;

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

    @BeforeAll
    static void init() throws IOException, SQLException {

        embeddedPostgreSQL = EmbeddedPostgreSQL.start();

        source = embeddedPostgreSQL.getPostgresDatabase();

        SqlDaoTestUtils.createTables(source);
    }

    @AfterAll
    static void teardown() throws IOException {

        embeddedPostgreSQL.close();
    }

    @BeforeEach
    void cleanData() {

        SqlDaoTestUtils.clearTables(source);
    }


    @Test
    void testgetById() {

        AuthorSqlDao dao = new AuthorSqlDao(source);

        final Author created = dao.create(author1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testcreate() {
        AuthorSqlDao dao = new AuthorSqlDao(source);

        final Author created = dao.create(author1);

        assertEquals(author1.withId(created.getId()), created);
    }

    @Test
    void testupdate() {

        AuthorSqlDao dao = new AuthorSqlDao(source);

        final Author created = dao.create(author1);
        assertEquals(created, author1);

        final Author updated = dao.update(author2.withId(created.getId()));

        assertEquals(updated, author2.withId(created.getId()));

        assertNotEquals(updated, author1);
    }

    @Test
    void testdelete() {

        AuthorSqlDao dao = new AuthorSqlDao(source);

        final Author created = dao.create(author1);

        final Optional<Author> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByName() {
        AuthorSqlDao dao = new AuthorSqlDao(source);

        final Author created1 = dao.create(author1);
        final Author created2 = dao.create(author2);

        final List<Author> daoByName = dao.findByName("Pratchett");

        assertEquals(daoByName.get(0), created1);
    }

    @Test
    void testfullList() {
        AuthorSqlDao dao = new AuthorSqlDao(source);

        final Author created1 = dao.create(author1);
        final Author created2 = dao.create(author2);

        final List<Author> daoByName = dao.fullList();

        assertEquals(daoByName.get(0), created1);
        assertEquals(daoByName.get(1), created2);
    }
}