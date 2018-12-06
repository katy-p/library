package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.AuthorsHttpHandler;
import katy.library.dao.AuthorDao;
import katy.library.dao.map.AuthorMapDao;
import katy.library.service.AuthorService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.InetSocketAddress;

class AuthorIntegrationTest extends AuthorIntegrationTestTemplate {

    private static HttpServer httpServer;
    private static String address;
    private static CloseableHttpClient httpClient;
    private static AuthorDao authorDao;

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

    @Override
    public AuthorDao getAuthorDao() {
        return authorDao;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    @BeforeEach
    void cleanData() {

        authorDao.fullList().forEach(a -> authorDao.delete(a.getId()));
    }
}
