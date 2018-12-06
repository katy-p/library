package katy.library.intergation;

import com.sun.net.httpserver.HttpServer;
import katy.library.controllers.sun.PersonHttpHandler;
import katy.library.dao.PersonDao;
import katy.library.dao.map.PersonMapDao;
import katy.library.service.PersonServise;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PersonIntegrationMapTest extends PersonIntegrationTestTemplate{

    private static HttpServer httpServer;
    private static String address;
    private static CloseableHttpClient httpClient;
    private static PersonDao personDao;

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    protected PersonDao getPersonDao() { return personDao; }

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
}

