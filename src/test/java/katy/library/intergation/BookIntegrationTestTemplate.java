package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.BooksHttpHandler;
import katy.library.dao.BookDao;
import katy.library.dao.LibraryCardDao;
import katy.library.dao.map.BookMapDao;
import katy.library.dao.map.LibraryCardMapDao;
import katy.library.model.Author;
import katy.library.model.Book;
import katy.library.model.LibraryCard;
import katy.library.model.Person;
import katy.library.service.BookService;
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

abstract class BookIntegrationTestTemplate {

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

    protected abstract CloseableHttpClient getHttpServer();

    protected abstract String getAddress();

    public abstract BookDao setBookDao();

    public abstract LibraryCardDao getLibraryCardDao();


    @Test
    void checkEmptyResponse() throws IOException {

        try (final CloseableHttpResponse response = getHttpServer().execute(new HttpGet(getAddress() + "/books/"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("book list:\n"));
        }
    }

    @Test
    public void checkGet() throws IOException {

        final Book book = setBookDao().create(book1);

        try (final CloseableHttpResponse response = getHttpServer().execute(new HttpGet(getAddress() +
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

        try (final CloseableHttpResponse response = getHttpServer().execute(new HttpPost(getAddress() +
                "/books/?id=222&title=DiscWorld&authorId="+book1.getAuthor().getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            Book book = setBookDao().fullList().get(0);
            /*assertThat(body, equalTo("created book:\n" +
                 "Book(id=" + book.getId() + ", title=DiscWorld, " +
                 "author=Author(id="+book.getAuthor().getId()+", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28)\n"));*/
        }
    }

    @Test
    void checkPut() throws IOException {

        final Book book = setBookDao().create(book1);

        try (final CloseableHttpResponse response = getHttpServer().execute(new HttpPut(getAddress() +
                "/books/?id=" + book.getId() + "&title=Strata&authorId="+book1.getAuthor().getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            /*assertThat(body, equalTo("updated book:\n" +
                 "Book(id=" + book.getId() + ", title=Strata, " +
                 "author=Author(id="+book.getAuthor().getId()+", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28))\n"));*/
        }
    }

    @Test
    void checkDelete() throws IOException {

        final Book book = setBookDao().create(book1);

        try (final CloseableHttpResponse response = getHttpServer().execute(new HttpDelete(getAddress() +
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
