package katy.library.service;

import katy.library.dao.PersonDao;
import katy.library.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class PersonServiseTest {

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
    void testgetByIdPerson() {

        PersonDao personDao = mock(PersonDao.class);
        PersonServise service = new PersonServise(personDao);

        when(service.getByIdPerson(1)).thenReturn(person1);

        assertEquals(service.getByIdPerson(1), person1);
    }

    @Test
    void testcreatePerson() {

        PersonDao personDao = mock(PersonDao.class);
        PersonServise service = new PersonServise(personDao);

        when(service.createPerson(person1)).thenReturn(person1);

        assertEquals(service.createPerson(person1), person1);
    }

    @Test
    void testupdatePerson() {

        PersonDao personDao = mock(PersonDao.class);
        PersonServise service = new PersonServise(personDao);

        when(service.updatePerson(person2)).thenReturn(person2);

        assertEquals(service.updatePerson(person2), person2);
    }

    @Test
    void testdeletePerson() {

        PersonDao personDao = mock(PersonDao.class);
        PersonServise service = new PersonServise(personDao);

        when(service.deletePerson(1)).thenReturn(person1);

        assertEquals(service.deletePerson(1), person1);
    }

    @Test
    void testfindByNamePerson() {

        PersonDao personDao = mock(PersonDao.class);
        PersonServise service = new PersonServise(personDao);

        List<Person> personList = new ArrayList<>();
        personList.add(person1);

        when(service.findByNamePerson("Smith")).thenReturn(personList);
        assertEquals(service.findByNamePerson("Smith"), personList);
        assertEquals(service.findByNamePerson("Smith").get(0), person1);
    }
}