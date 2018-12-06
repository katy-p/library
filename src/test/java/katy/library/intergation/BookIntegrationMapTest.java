package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.BooksHttpHandler;
import katy.library.controllers.sun.PersonHttpHandler;
import katy.library.dao.BookDao;
import katy.library.dao.LibraryCardDao;
import katy.library.dao.dummy.BookDaoImpl;
import katy.library.dao.dummy.LibraryCardDaoImpl;
import katy.library.dao.dummy.PersonDaoImpl;
import katy.library.dao.map.BookMapDao;
import katy.library.dao.map.LibraryCardMapDao;
import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;
import katy.library.service.BookService;
import katy.library.service.PersonServise;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookIntegrationTest {

    private static HttpServer httpServer;
    private static String address;
    private static CloseableHttpClient httpClient;
    private static BookDao bookDao;
    private static LibraryCardDao libraryCardDao;

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL,28))
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

    private LibraryCard libraryCard1 = LibraryCard.builder()
            .id(1)
            .person(person1)
            .book(book1)
            .build();


    @BeforeAll
    public static void init() throws IOException {

        httpClient = HttpClientBuilder.create().build();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        bookDao = new BookMapDao();
        libraryCardDao = new LibraryCardMapDao();

        final BooksHttpHandler handler = new BooksHttpHandler(new BookService(bookDao, libraryCardDao));

        httpServer.createContext(handler.path(), handler);

        httpServer.start();

        address = "http:/" + httpServer.getAddress();
    }

    @AfterAll
    public static void teardown() throws IOException {
        httpClient.close();
        httpServer.stop(0);
    }

    @BeforeEach
    void cleanData() {

        bookDao.fullList().forEach(b -> bookDao.delete(b.getId()));
        libraryCardDao.fullList().forEach(lc -> libraryCardDao.delete(lc.getId()));
    }

    @Test
    void checkEmptyResponse() throws IOException {

        try (final CloseableHttpResponse response = httpClient.execute(new HttpGet(address + "/books/"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("book list:\n"));
        }
    }

    @Test
    public void checkGet() throws IOException {

        final Book book = bookDao.create(book1);

        try (final CloseableHttpResponse response = httpClient.execute(new HttpGet(address +
                "/books/" + book.getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("book list:\n" +
                "Book(id="+book.getId()+", title=DiscWorld, " +
                "author=Author(id="+book.getAuthor().getId()+", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28))\n"));
        }
    }

    @Test
    void checkPost() throws IOException {

        try (final CloseableHttpResponse response = httpClient.execute(new HttpPost(address +
                "/books/?id=222&title=DiscWorld&authorId="+book1.getAuthor().getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            Book book = bookDao.fullList().get(0);
            //assertThat(body, equalTo("created book:\n" +
            //     "Book(id=" + book.getId() + ", title=DiscWorld, " +
            //     "author=Author(id="+book.getAuthor().getId()+", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28)\n"));
        }
    }

    @Test
    void checkPut() throws IOException {
        final Book book = bookDao.create(book1);

        try (final CloseableHttpResponse response = httpClient.execute(new HttpPut(address +
                "/books/?id=" + book.getId() + "&title=Strata&authorId="+book1.getAuthor().getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            //assertThat(body, equalTo("updated book:\n" +
            //     "Book(id=" + book.getId() + ", title=Strata, " +
            //     "author=Author(id="+book.getAuthor().getId()+", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28))\n"));
        }
    }

    @Test
    void checkDelete() throws IOException {
        final Book book = bookDao.create(book1);

        try (final CloseableHttpResponse response = httpClient.execute(new HttpDelete(address +
                "/books/" + book.getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("deleted book:\n" +
                    "Book(id=" + book.getId() + ", title=DiscWorld, " +
                    "author=Author(id="+book.getAuthor().getId()+", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28))\n"));
        }
    }

    private String bodyAsString(HttpEntity entity) throws IOException {
        return IOUtils.toString(entity.getContent(), "UTF-8");
    }

}
