package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.AuthorsHttpHandler;
import katy.library.dao.dummy.AuthorDaoImpl;
import katy.library.service.AuthorService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorIntegrationTest {

    private static HttpServer httpServer;
    private static String address;
    private static CloseableHttpClient httpClient;

    @BeforeAll
    public static void init() throws IOException {
        httpClient = HttpClientBuilder.create().build();
        httpServer = HttpServer.create(new InetSocketAddress("localhost", 0), 0);

        final AuthorsHttpHandler handler = new AuthorsHttpHandler(new AuthorService(new AuthorDaoImpl()));

        httpServer.createContext(handler.path(), handler);

        httpServer.start();

        address = "http:/" + httpServer.getAddress();
    }

    @AfterAll
    public static void teardown() throws IOException {
        httpClient.close();
        httpServer.stop(0);
    }

    @Test
    public void checkEmptyResponse() throws IOException {
        final CloseableHttpResponse response = httpClient.execute(new HttpGet(address + "/authors/"));
        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

        String body = bodyAsString(response.getEntity());

        assertEquals(body, "author list:\n");
    }

    private String bodyAsString(HttpEntity entity) throws IOException {
        return IOUtils.toString(entity.getContent(), "UTF-8");
    }
}
