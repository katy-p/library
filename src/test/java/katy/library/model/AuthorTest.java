package katy.library.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;


public class AuthorTest {

    @Test
    public void testAuthor(){

        Author author = Author.builder()
                .id(1)
                .firstName("Terry")
                .lastName("Pratchett")
                .dateOfBirth(LocalDate.of(1948, Month.APRIL,28))
                .build();

        assertEquals(1,author.getId());
        assertEquals("Terry", author.getFirstName());
        assertEquals("Pratchett", author.getLastName());
    }

}