package katy.library.service;

import katy.library.dao.AuthorDao;
import katy.library.model.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


class AuthorServiceTest {

    private Author author1 = Author.builder()
            .id(-1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    private Author author2 = Author.builder()
            .id(-2)
            .firstName("Jane")
            .lastName("Austen")
            .dateOfBirth(LocalDate.of(1775, Month.DECEMBER, 16))
            .build();

    @Test
    void testgetByIdAuthor() {

        AuthorDao authorDao = mock(AuthorDao.class);
        AuthorService servise = new AuthorService(authorDao);

        when(servise.getByIdAuthor(1)).thenReturn(author1);

        assertEquals(servise.getByIdAuthor(1), author1);
    }

    @Test
    void testcreateAuthor() {

        AuthorDao authorDao = mock(AuthorDao.class);
        AuthorService servise = new AuthorService(authorDao);

        when(servise.createAuthor(author1)).thenReturn(author1);

        assertEquals(servise.createAuthor(author1), author1);
    }

    @Test
    void testupdateAuthor() {

        AuthorDao authorDao = mock(AuthorDao.class);
        AuthorService servise = new AuthorService(authorDao);

        when(servise.updateAuthor(author2)).thenReturn(author2);

        assertEquals(servise.createAuthor(author2), author2);
    }

    @Test
    void testdeleteAuthor() {

        AuthorDao authorDao = mock(AuthorDao.class);
        AuthorService servise = new AuthorService(authorDao);

        when(servise.deleteAuthor(1)).thenReturn(author1);

        assertEquals(servise.deleteAuthor(1), author1);
    }

    @Test
    void testfindByNameAuthor() {

        AuthorDao authorDao = mock(AuthorDao.class);
        AuthorService servise = new AuthorService(authorDao);


        List <Author> authorList = new ArrayList<>();
        authorList.add(author1);

        when(servise.findByNameAuthor("Pratchett")).thenReturn(authorList);
        assertEquals(servise.findByNameAuthor("Pratchett"), authorList);
        assertEquals(servise.findByNameAuthor("Pratchett").get(0), author1);
    }
}