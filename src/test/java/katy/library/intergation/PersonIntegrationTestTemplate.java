package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.PersonHttpHandler;
import katy.library.dao.PersonDao;
import katy.library.dao.map.PersonMapDao;
import katy.library.model.Person;
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

public class PersonIntegrationTest {

    private static HttpServer httpServer;
    private static String address;
    private static CloseableHttpClient httpClient;
    private static PersonDao personDao;

    private Person person1 = Person.builder()
            .id(1)
            .firstName("Anna")
            .lastName("Smith")
            .dateOfBirth(LocalDate.of(1980, Month.APRIL,28))
            .build();

    @BeforeAll
    public static void init() throws IOException {

        httpClient = HttpClientBuilder.create().build();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        personDao = new PersonMapDao();

        final PersonHttpHandler handler = new PersonHttpHandler(new PersonServise(personDao));

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

        personDao.fullList().forEach(a -> personDao.delete(a.getId()));
    }

    @Test
    void checkEmptyResponse() throws IOException {

        try (final CloseableHttpResponse response = httpClient.execute(new HttpGet(address + "/persons/"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("person list:\n"));
        }
    }

    @Test
    public void checkGet() throws IOException {

        final Person person = personDao.create(person1);

        try(final CloseableHttpResponse response = httpClient.execute(new HttpGet(address +
                "/persons/"+person.getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("person list:\n" +
                    "Person(id="+person.getId()+", firstName=Anna, lastName=Smith, dateOfBirth=1980-04-28)\n"));
        }
    }

    @Test
    void checkPost() throws IOException {
        try (final CloseableHttpResponse response = httpClient.execute(new HttpPost(address +
                "/persons/?id=222&firstName=Anna&lastName=Smith&dateOfBirth=1980-04-28"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("created person:\n" +
                    "Person(id=" + personDao.fullList().get(0).getId() + ", firstName=Anna, lastName=Smith, dateOfBirth=1980-04-28)\n"));
        }
    }

    @Test
    void checkPut() throws IOException {
        final Person person = personDao.create(person1);

        try (final CloseableHttpResponse response = httpClient.execute(new HttpPut(address +
                "/persons/?id=" + person.getId() + "&firstName=Liza&lastName=Brown&dateOfBirth=1975-10-06"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("updated person:\n" +
                    "Person(id=" + person.getId() + ", firstName=Liza, lastName=Brown, dateOfBirth=1975-10-06)\n"));
        }
    }

    @Test
    void checkDelete() throws IOException {
        final Person person = personDao.create(person1);

        try (final CloseableHttpResponse response = httpClient.execute(new HttpDelete(address +
                "/persons/"+person.getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("deleted person:\n" +
                    "Person(id=" + person.getId() + ", firstName=Anna, lastName=Smith, dateOfBirth=1980-04-28)\n"));
        }
    }

    private String bodyAsString(HttpEntity entity) throws IOException {
        return IOUtils.toString(entity.getContent(), "UTF-8");
    }


}

