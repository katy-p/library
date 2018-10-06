package katy.library.dao.map;

import katy.library.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class PersonMapDaoTest {

    private Person person1 = Person.builder()
                .id(-1)
                .firstName("Anna")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1980, Month.APRIL, 28))
                .build();

    private Person person2 = Person.builder()
                        .id(-2)
                        .firstName("John")
                        .lastName("Gray")
                        .dateOfBirth(LocalDate.of(1983, Month.MAY, 14))
                        .build();

    @Test
    void testCreate() {

        PersonMapDao dao = new PersonMapDao();

        final Person created = dao.create(person1);

        assertEquals(person1.withId(created.getId()), created);
    }

    @Test
    void testGetById() {

        PersonMapDao dao = new PersonMapDao();

        final Person created = dao.create(person1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testUpdate() {

        PersonMapDao dao = new PersonMapDao();

        final Person created1 = dao.create(person1);

        final Person created2 = dao.create(person2);

        Person person3 = created1.withId(created2.getId());

        final Person updated = dao.update(person3);

        assertEquals(updated, person3);

        assertNotEquals(updated, created2);
    }

    @Test
    void testDelete() {

        PersonMapDao dao = new PersonMapDao();

        final Person created = dao.create(person1);

        final Optional<Person> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testFind() {

        PersonMapDao dao = new PersonMapDao();

        final Person created1 = dao.create(person1);

        final Person created2 = dao.create(person2);

        final List<Person> daoByName = dao.findByName("Smith");

        assertEquals(daoByName.get(0), created1);


    }
}