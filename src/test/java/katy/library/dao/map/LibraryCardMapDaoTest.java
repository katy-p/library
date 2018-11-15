package katy.library.dao.map;

import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibraryCardMapDaoTest {

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

    private Book book1 = Book.builder()
            .id(1)
            .title("DiscWorld")
            .author(author1)
            .build();

    private Book book2 = Book.builder()
            .id(1)
            .title("Emma")
            .author(author2)
            .build();

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

    private LibraryCard libraryCard1 = LibraryCard.builder()
            .id(1)
            .person(person1)
            .book(book1)
            .build();

    private LibraryCard libraryCard2 = LibraryCard.builder()
            .id(2)
            .person(person2)
            .book(book2)
            .build();

    @Test
    void testgetById() {

        LibraryCardMapDao dao = new LibraryCardMapDao();

        final LibraryCard created = dao.create(libraryCard1);

        assertEquals(Optional.of(created), dao.getById(created.getId()));
    }

    @Test
    void testcreate() {

        LibraryCardMapDao dao = new LibraryCardMapDao();

        final LibraryCard created = dao.create(libraryCard1);

        assertEquals(libraryCard1.withId(created.getId()), created);
    }

    @Test
    void testupdate() {

        LibraryCardMapDao dao = new LibraryCardMapDao();

        final LibraryCard created1 = dao.create(libraryCard1);

        final LibraryCard created2 = dao.create(libraryCard2);

        LibraryCard libraryCard3 = created1.withId(created2.getId());

        final LibraryCard updated = dao.update(libraryCard3);

        assertEquals(updated, libraryCard3);

        assertNotEquals(updated, libraryCard2);
    }

    @Test
    void testdelete() {

        LibraryCardMapDao dao = new LibraryCardMapDao();

        final LibraryCard created = dao.create(libraryCard1);

        final Optional<LibraryCard> deleted = dao.delete(created.getId());

        assertEquals(deleted, Optional.of(created));
    }

    @Test
    void testfindByPerson() {

        LibraryCardMapDao dao = new LibraryCardMapDao();

        final LibraryCard created1 = dao.create(libraryCard1);

        final LibraryCard created2 = dao.create(libraryCard2);

        final List<LibraryCard> ListByPerson = dao.findByPerson(person1);

        assertEquals(ListByPerson.get(0), created1);
        assertNotEquals(ListByPerson.get(0), created2);
    }

    @Test
    void testfindByBook() {

        LibraryCardMapDao dao = new LibraryCardMapDao();

        final LibraryCard created1 = dao.create(libraryCard1);

        final LibraryCard created2 = dao.create(libraryCard2);

        final List<LibraryCard> ListByBook = dao.findByBook(book1);

        assertEquals(ListByBook.get(0), created1);
        assertNotEquals(ListByBook.get(0), created2);
    }
}