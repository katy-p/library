package katy.library.dao.sql;

import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;
import katy.library.dao.PersonDao;
import katy.library.dao.map.PersonMapDao;
import katy.library.model.Person;
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

import static org.junit.jupiter.api.Assertions.*;


class PersonSqlDaoTest {

    private static EmbeddedPostgreSQL embeddedPostgreSQL;
    private static DataSource source;

    private Person person1 = Person.builder()
            .id(1)
            .firstName("Anna")
            .lastName("Smith")
            .dateOfBirth(LocalDate.of(1980, Month.APRIL, 28))
            .build();

    private Person person2 = Person.builder()
            .id(2)
            .firstName("John")
            .lastName("Gray")
            .dateOfBirth(LocalDate.of(1983, Month.MAY, 14))
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
    void getById() {

        PersonDao dao = new PersonSqlDao(source);

        final Person created = dao.create(person1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testcreate() {

        PersonDao dao = new PersonSqlDao(source);

        final Person created = dao.create(person1);

        assertEquals(person1.withId(created.getId()), created);
    }

    @Test
    void testupdate() {

        PersonDao dao = new PersonSqlDao(source);

        final Person created = dao.create(person1);
        assertEquals(created, person1);

        final Person updated = dao.update(person2.withId(created.getId()));

        assertEquals(updated, person2.withId(created.getId()));

        assertNotEquals(updated, person1);
    }

    @Test
    void testdelete() {

        PersonDao dao = new PersonSqlDao(source);

        final Person created = dao.create(person1);

        final Optional<Person> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByName() {

        PersonDao dao = new PersonSqlDao(source);

        final Person created1 = dao.create(person1);
        final Person created2 = dao.create(person2);

        final List<Person> daoByName = dao.findByName("Smith");

        assertEquals(daoByName.get(0), created1);
    }

    @Test
    void testfullList() {

        PersonDao dao = new PersonSqlDao(source);

        final Person created1 = dao.create(person1);
        final Person created2 = dao.create(person2);

        final List<Person> daoByName = dao.fullList();

        assertEquals(daoByName.get(0), created1);
        assertEquals(daoByName.get(1), created2);
    }
}