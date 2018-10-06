package katy.library.dao.map;

import katy.library.model.Author;
import katy.library.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Simon Popugaev
 */
class BookMapDaoTest {

    private Author author1 = Author.builder()
            .id(-1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    private Author author2 = Author.builder()
            .id(-2)
            .firstName("John")
            .lastName("Gray")
            .dateOfBirth(LocalDate.of(1983, Month.MAY, 14))
            .build();

    @Test
    void getById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByAuthor() {
    }
}