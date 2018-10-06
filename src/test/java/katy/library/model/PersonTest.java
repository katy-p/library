package katy.library.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;


class PersonTest {

    @Test
    public void testPerson(){

        Person person = Person.builder()
                .id(1)
                .firstName("Anna")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1988, Month.APRIL,28))
                .build();

        assertEquals(1,person.getId());
        assertEquals("Anna", person.getFirstName());
        assertEquals("Smith", person.getLastName());
    }
}