package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.AuthorsHttpHandler;
import katy.library.dao.AuthorDao;
import katy.library.dao.map.AuthorMapDao;
import katy.library.model.Author;
import katy.library.service.AuthorService;
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
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class AuthorIntegrationTest {

    private static HttpServer httpServer;

    public String getAddress() {
        return address;
    }

    private static String address;

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    private static CloseableHttpClient httpClient;
    private static AuthorDao authorDao;

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL,28))
            .build();

    @BeforeAll
    static void init() throws IOException {

        httpClient = HttpClientBuilder.create().build();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        authorDao = new AuthorMapDao();

        final AuthorsHttpHandler handler = new AuthorsHttpHandler(new AuthorService(authorDao));

        httpServer.createContext(handler.path(), handler);

        httpServer.start();

        address = "http:/" + httpServer.getAddress();
    }

    @AfterAll
    static void teardown() throws IOException {
        httpClient.close();
        httpServer.stop(0);
    }

    @BeforeEach
    void cleanData() {

        authorDao.fullList().forEach(a -> authorDao.delete(a.getId()));
    }

    @Test
    void checkEmptyResponse() throws IOException {

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpGet(getAddress() + "/authors/"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("author list:\n"));
        }
    }

    @Test
    void checkGet() throws IOException {

        final Author author = authorDao.create(author1);

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpGet(getAddress() +
                "/authors/" + author.getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("author list:\n" +
                    "Author(id=" + author.getId() + ", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28)\n"));
        }
    }

    @Test
    void checkPost() throws IOException {
        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpPost(getAddress() +
                "/authors/?id=222&firstName=Terry&lastName=Pratchett&dateOfBirth=1948-04-28"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("created author:\n" +
                    "Author(id=" + authorDao.fullList().get(0).getId() + ", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28)\n"));
        }
    }

    @Test
    void checkPut() throws IOException {
        final Author author = authorDao.create(author1);

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpPut(getAddress() +
                "/authors/?id=" + author.getId() + "&firstName=Jane&lastName=Austen&dateOfBirth=1775-12-16"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("updated author:\n" +
                    "Author(id=" + author.getId() + ", firstName=Jane, lastName=Austen, dateOfBirth=1775-12-16)\n"));
        }
    }

    @Test
    void checkDelete() throws IOException {
        final Author author = authorDao.create(author1);

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpDelete(getAddress() +
                "/authors/" + author.getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("deleted author:\n" +
                    "Author(id=" + author.getId() + ", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28)\n"));
        }
    }

    private String bodyAsString(HttpEntity entity) throws IOException {
        try (final InputStream content = entity.getContent()) {
            return IOUtils.toString(content, "UTF-8");
        }
    }
}
