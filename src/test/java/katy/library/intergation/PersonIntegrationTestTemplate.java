package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.PersonHttpHandler;
import katy.library.dao.AuthorDao;
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

abstract class PersonIntegrationTestTemplate {


    private Person person1 = Person.builder()
            .id(1)
            .firstName("Anna")
            .lastName("Smith")
            .dateOfBirth(LocalDate.of(1980, Month.APRIL,28))
            .build();

    protected abstract String getAddress();

    protected abstract CloseableHttpClient getHttpClient();

    protected abstract PersonDao getPersonDao();

    @Test
    void checkEmptyResponse() throws IOException {

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpGet(getAddress() + "/persons/"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("person list:\n"));
        }
    }

    @Test
    public void checkGet() throws IOException {

        final Person person = getPersonDao().create(person1);

        try(final CloseableHttpResponse response = getHttpClient().execute(new HttpGet(getAddress() +
                "/persons/"+person.getId()))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("person list:\n" +
                    "Person(id="+person.getId()+", firstName=Anna, lastName=Smith, dateOfBirth=1980-04-28)\n"));
        }
    }

    @Test
    void checkPost() throws IOException {

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpPost(getAddress() +
                "/persons/?id=222&firstName=Anna&lastName=Smith&dateOfBirth=1980-04-28"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("created person:\n" +
                    "Person(id=" + getPersonDao().fullList().get(0).getId() + ", firstName=Anna, lastName=Smith, dateOfBirth=1980-04-28)\n"));
        }
    }

    @Test
    void checkPut() throws IOException {

        final Person person = getPersonDao().create(person1);

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpPut(getAddress() +
                "/persons/?id=" + person.getId() + "&firstName=Liza&lastName=Brown&dateOfBirth=1975-10-06"))) {

            assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

            String body = bodyAsString(response.getEntity());

            assertThat(body, equalTo("updated person:\n" +
                    "Person(id=" + person.getId() + ", firstName=Liza, lastName=Brown, dateOfBirth=1975-10-06)\n"));
        }
    }

    @Test
    void checkDelete() throws IOException {

        final Person person = getPersonDao().create(person1);

        try (final CloseableHttpResponse response = getHttpClient().execute(new HttpDelete(getAddress() +
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

