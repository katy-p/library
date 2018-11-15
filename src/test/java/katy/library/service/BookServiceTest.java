package katy.library.service;

import katy.library.dao.BookDao;
import katy.library.dao.LibraryCardDao;
import katy.library.dao.map.BookMapDao;
import katy.library.dao.map.LibraryCardMapDao;
import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class BookServiceTest {

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    private Book book1 = Book.builder()
            .id(1)
            .title("DiscWorld")
            .author(author1)
            .build();

    private Person person1 = Person.builder()
            .id(-1)
            .firstName("Anna")
            .lastName("Smith")
            .dateOfBirth(LocalDate.of(1980, Month.APRIL, 28))
            .build();

    private LibraryCard libraryCard = LibraryCard.builder()
            .id(-1)
            .person(person1)
            .book(book1)
            .build();

    @Test
    void testgetByIdBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);

        when(bookDao.getById(1)).thenReturn(Optional.of(book1));

        assertEquals(service.getByIdBook(1), book1);
    }

    @Test
    void testcreateBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);


        when(service.createBook(book1)).thenReturn(book1);

        assertEquals(service.createBook(book1), book1);
    }

    @Test
    void testupdateBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);


        when(service.updateBook(book1)).thenReturn(book1);

        assertEquals(service.updateBook(book1), book1);
    }

    @Test
    void testdeleteBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);


        when(service.deleteBook(1)).thenReturn(book1);

        assertEquals(service.deleteBook(1), book1);
    }

    @Test
    void testfindByNameBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);


        List <Book> bookList = new ArrayList<>();
        bookList.add(book1);

        when(service.findByNameBook("DiscWorld")).thenReturn(bookList);

        assertEquals(service.findByNameBook("DiscWorld"), bookList);
        assertEquals(service.findByNameBook("DiscWorld").get(0), book1);
    }

    @Test
    void testfindByAuthorBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);


        List <Book> bookList = new ArrayList<>();
        bookList.add(book1);

        when(service.findByAuthorBook(author1)).thenReturn(bookList);

        assertEquals(service.findByAuthorBook(author1), bookList);
        assertEquals(service.findByAuthorBook(author1).get(0), book1);
    }

    @Test
    void testborrowBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);


        when(service.borrowBook(libraryCard)).thenReturn(libraryCard);

        assertEquals(service.borrowBook(libraryCard), libraryCard);
    }

    @Test
    void testreturnBook() {

        BookDao bookDao = mock(BookDao.class);
        LibraryCardDao libraryCardDao = mock(LibraryCardDao.class);

        BookService service = new BookService(bookDao, libraryCardDao);


        when(service.returnBook(1)).thenReturn(libraryCard);

        assertEquals(service.returnBook(1), libraryCard);
    }
}