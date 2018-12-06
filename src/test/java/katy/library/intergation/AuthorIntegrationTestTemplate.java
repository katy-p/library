package katy.library.intergation;

import com.fasterxml.jackson.databind.ObjectMapper;
import katy.library.dao.AuthorDao;
import katy.library.model.Author;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

abstract class AuthorIntegrationTestTemplate {

    private Author author1 = Author.builder()
            .id(1)
            .firstName("Terry")
            .lastName("Pratchett")
            .dateOfBirth(LocalDate.of(1948, Month.APRIL, 28))
            .build();

    protected abstract String getAddress();

    protected abstract CloseableHttpClient getHttpClient();

    protected abstract AuthorDao getAuthorDao();

    protected abstract ObjectMapper getObjectMapper();

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

        final Author author = getAuthorDao().create(author1);

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
                    "Author(id=" + getAuthorDao().fullList().get(0).getId() + ", firstName=Terry, lastName=Pratchett, dateOfBirth=1948-04-28)\n"));
        }
    }

    @Test
    void checkPut() throws IOException {
        final Author author = getAuthorDao().create(author1);

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
        final Author author = getAuthorDao().create(author1);

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
