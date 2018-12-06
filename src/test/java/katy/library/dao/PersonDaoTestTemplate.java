package katy.library.dao;

import katy.library.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


abstract public class PersonDaoTestTemplate {

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

    protected abstract PersonDao getPersonDao();

    @Test
    void getById() {

        PersonDao dao = getPersonDao();

        final Person created = dao.create(person1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testcreate() {

        PersonDao dao = getPersonDao();

        final Person created = dao.create(person1);

        assertEquals(person1.withId(created.getId()), created);
    }

    @Test
    void testupdate() {

        PersonDao dao = getPersonDao();

        final Person created = dao.create(person1);
        assertEquals(created, person1);

        final Person updated = dao.update(person2.withId(created.getId()));

        assertEquals(updated, person2.withId(created.getId()));

        assertNotEquals(updated, person1);
    }

    @Test
    void testdelete() {

        PersonDao dao = getPersonDao();

        final Person created = dao.create(person1);

        final Optional<Person> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByName() {

        PersonDao dao = getPersonDao();

        final Person created1 = dao.create(person1);
        final Person created2 = dao.create(person2);

        final List<Person> daoByName = dao.findByName("Smith");

        assertEquals(daoByName.get(0), created1);
    }

    @Test
    void testfullList() {

        PersonDao dao = getPersonDao();

        final Person created1 = dao.create(person1);
        final Person created2 = dao.create(person2);

        final List<Person> daoByName = dao.fullList();

        assertEquals(daoByName.get(0), created1);
        assertEquals(daoByName.get(1), created2);
    }
}